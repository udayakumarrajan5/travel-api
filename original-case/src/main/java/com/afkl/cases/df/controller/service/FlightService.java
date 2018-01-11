/**
 * 
 */
package com.afkl.cases.df.controller.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.afkl.cases.df.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Udayakumar.Rajan
 *
 */
@Service
public class FlightService {
	
	String jsonPath = "data/flights.json";
	
	List<Flight> flights = new ArrayList<>();
	
	private static Logger logger = LoggerFactory.getLogger(FlightService.class);
	
	@PostConstruct
	public void init() throws IOException {
		ObjectMapper jsonMapper = new ObjectMapper();
		
		InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(jsonPath);
		
		flights = jsonMapper.readValue(in, new TypeReference<List<Flight>>() {
		});
		
		logger.info("-------------- Loaded Flight Information ---------------- ");
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Flight> getFlights () {
		return flights;
	}

}
