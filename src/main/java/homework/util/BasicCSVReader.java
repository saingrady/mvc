package homework.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import homework.model.Hotel;

public class BasicCSVReader {
	
	private static Resource resource = new ClassPathResource("hoteldb.csv");
    public static List<Hotel> hotels = readCSV();  

    public static List<Hotel> readCSV() {
    	
    	//Create the CSVFormat object
		CSVFormat format = CSVFormat.RFC4180.withHeader().withDelimiter(',');
		
		//initialize the CSVParser object
		CSVParser parser;
		try {
			parser = new CSVParser(new FileReader(resource.getFile()), format);
			List<Hotel> hotels = new ArrayList<Hotel>();
			
			for(CSVRecord record : parser){
				Hotel hotel = new Hotel();
				
				// CITY,HOTELID,ROOM,PRICE
				hotel.setCity(record.get("CITY"));
				hotel.setHotelId(Integer.parseInt(record.get("HOTELID")));
				hotel.setRoom(record.get("ROOM"));
				hotel.setPrice(new BigDecimal(record.get("PRICE")));
				hotels.add(hotel);
			}
			//close the parser
			parser.close();
			return hotels;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
        
    }
}