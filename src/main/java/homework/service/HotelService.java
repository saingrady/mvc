package homework.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import homework.model.Hotel;
import homework.util.BasicCSVReader;

@Service
public class HotelService {
	
	private static final String SORT_ASC = "asc";
	private static final String SORT_DSC = "dsc";
	
	public List<Hotel> GetHotels() {
		return BasicCSVReader.hotels;
	}
	
	public List<Hotel> GetHotelsByCity(String city, String sort) {
		
        List<Hotel> result = new ArrayList<Hotel>();
        for (Hotel hotel : BasicCSVReader.hotels) {
            if (city.equals(hotel.getCity())) { 
                result.add(hotel);
            }
        }
        
        if (SORT_ASC.equals(sort)) {
        	return (sortHotelsPriceAsc(result));
        } else if (SORT_DSC.equals(sort)) {
        	return (sortHotelsPriceDsc(result));
        }
        
        return result;		
		
	}
	
	public List<Hotel> sortHotelsPriceAsc(List<Hotel> hotels) {
		Collections.sort(hotels, new Comparator<Hotel>() {
		    @Override
		    public int compare(Hotel hotel1, Hotel hotel2) {
		        return hotel1.getPrice().intValueExact() - hotel2.getPrice().intValueExact();
		    }
		});
		return hotels;
	}
	
	
	public List<Hotel> sortHotelsPriceDsc(List<Hotel> hotels) {
		Collections.sort(hotels, new Comparator<Hotel>() {
		    @Override
		    public int compare(Hotel hotel1, Hotel hotel2) {
		        return hotel2.getPrice().intValueExact() - hotel1.getPrice().intValueExact();
		    }
		});
		return hotels;
	}
	
}
