/**
 * 
 */
package com.afkl.cases.df.controller.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.afkl.cases.df.model.Airport;
import com.afkl.cases.df.model.FareResult;
import com.afkl.cases.df.model.RouteFare;
import com.afkl.cases.df.model.SearchModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Udayakumar.rajan
 *
 */
@Service
public class FareService {
    
	String jsonPath = "data/fares.json";

    List<RouteFare> routeFare = new ArrayList<>();
    
    private static Logger logger = LoggerFactory.getLogger(FareService.class);

	@PostConstruct
	public void init() throws IOException {
		ObjectMapper jsonMapper = new ObjectMapper();
		
		InputStream in = this.getClass().getClassLoader()
                .getResourceAsStream(jsonPath);
		
		routeFare = jsonMapper.readValue(in, new TypeReference<List<RouteFare>>() {
		});
		
		logger.info("-------------- Loaded Fare Information ---------------- ");
	}
	
	/**
	 * 
	 * @return
	 */
	public List<RouteFare> getRouteFare() {
		return routeFare;
	}
	
	
}
