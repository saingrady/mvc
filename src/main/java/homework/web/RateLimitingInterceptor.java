package homework.web;

import java.util.HashMap;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RateLimitingInterceptor extends HandlerInterceptorAdapter {
 
	@Autowired
	private Environment env;
	
	
	private static final int RATE_LIMIT_REQUESTS_GLOBAL = 3;
	private static final int RATE_LIMIT_SECONDS_GLOBAL = 10;
	
	private static HashMap<String, CallContext> timelogs = new HashMap<String, CallContext>();
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
	        HttpServletResponse response, Object handler) throws Exception {

		String apiKey = request.getParameter("apikey");
		String apiKeyRequests = apiKey + ".requests";
		String apiKeySeconds = apiKey + ".seconds";
		
		int requests = RATE_LIMIT_REQUESTS_GLOBAL;
		int extension = RATE_LIMIT_SECONDS_GLOBAL;
        
		if (null!=env.getProperty(apiKeyRequests)) {
			requests = Integer.parseInt(env.getProperty(apiKeyRequests));
			extension = Integer.parseInt(env.getProperty(apiKeySeconds));
		}

        // log time
		CallContext call = timelogs.get(apiKey);
		if ( null==call ) {
			// first time
	        timelogs.put(apiKey, new CallContext(apiKey, requests-1, System.currentTimeMillis() + extension*1000));
		} else {
			// second time
			if (System.currentTimeMillis() < call.getTimeLimited() ) {
				// limited				
				if (call.isSuspended()) {
					// cancel request
					System.out.println(call);
					return false;
				} else {
					if (call.getAttemptLeft() > 0) {
						call.setAttemptLeft( call.getAttemptLeft() - 1 );
					} else {
						// if over limit, suspended for next 5 minutes.
						call.setTimeLimited(System.currentTimeMillis() + 5*60*1000);
						//call.setTimeLimited(System.currentTimeMillis() + 1*60*1000);
						call.setSuspended(true);
						// cancel request
						System.out.println(call);
						return false;
					}
				}
			} else {
				// release
				call.setAttemptLeft(requests-1);
				call.setTimeLimited(System.currentTimeMillis() + extension*1000);
				call.setSuspended(false);
			} 
		}
		

        // normal process
		System.out.println(timelogs);
		return true;
	}
     
    
    @PreDestroy
    public void destroy() {
        // loop and finalize all limiters
    }
}