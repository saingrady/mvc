package homework.web;

public class CallContext {

	private String attemptId;
	private int attemptLeft;
    private long timeLimited;
    private boolean isSuspended;
 
    public CallContext(String attemptId, int attemptLeft, long timeLimited) {
		this.attemptId = attemptId;
		this.attemptLeft = attemptLeft;
		this.timeLimited = timeLimited;
	}
    
	public String getAttemptId() {
		return attemptId;
	}
	public void setAttemptId(String attemptId) {
		this.attemptId = attemptId;
	}
	public int getAttemptLeft() {
		return attemptLeft;
	}
	public void setAttemptLeft(int attemptLeft) {
		this.attemptLeft = attemptLeft;
	}
	public long getTimeLimited() {
		return timeLimited;
	}
	public void setTimeLimited(long timeLimited) {
		this.timeLimited = timeLimited;
	}
	public boolean isSuspended() {
		return isSuspended;
	}
	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public String toString() {
        return "attemptId:" + attemptId + ", timeLimited:" + timeLimited + ", attemptLeft:" + attemptLeft + ", isSuspended:" + isSuspended;
    }
    
}
