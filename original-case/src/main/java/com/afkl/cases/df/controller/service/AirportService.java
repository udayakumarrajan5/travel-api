/**
 * 
 */
package com.afkl.cases.df.controller.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.afkl.cases.df.model.Airport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Udayakumar.rajan
 *
 */
@Service
public class AirportService {
    
	String jsonPath = "data/airports.json";

    List<Airport> airports = new ArrayList<>();
    
    private static Logger logger = LoggerFactory.getLogger(AirportService.class);
    
    /**
     * 
     * @throws IOException
     */
	@PostConstruct
	public void init() throws IOException {
		ObjectMapper jsonMapper = new ObjectMapper();
		
		InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(jsonPath);
		
		airports = jsonMapper.readValue(in, new TypeReference<List<Airport>>() {
		});
		
		logger.info("-------------- Loaded Airport Information ---------------- ");
	}
	
	/**
	 * To return the list of airports.
	 * @return
	 */
    public List<Airport> getAirports() {
    		return airports;
    }
	
    /**
     * To search the airport details based the text entered by user.
     * @param filter
     * @return
     */
	public List<Airport> searchAirports(String filter) {
		return airports.stream().filter(airport -> airport.getCode().toLowerCase().contains(filter.toLowerCase())
				|| airport.getName().toLowerCase().contains(filter.toLowerCase())).collect(Collectors.toList());

	}
}
