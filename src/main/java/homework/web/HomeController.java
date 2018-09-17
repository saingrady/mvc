package homework.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import homework.model.Hotel;
import homework.service.HotelService;


@Controller
public class HomeController {

	@Autowired
	HotelService hotelService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "hi");
		return "hello";

	}

	@RequestMapping(value = "/api/search", method = RequestMethod.GET)
	public ResponseEntity<List<Hotel>>  find(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "apikey") String apiKey,
			@RequestParam(value = "city") String city,
			@RequestParam(value = "sort", required=false) String sort) {

		HotelService hotelService = new HotelService();

		return new ResponseEntity<List<Hotel>>(hotelService.GetHotelsByCity(city, sort), HttpStatus.OK);

	}

}