/**
 * 
 */
package com.afkl.cases.df.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.afkl.cases.df.controller.service.SearchFareService;
import com.afkl.cases.df.model.FareResult;
import com.afkl.cases.df.model.SearchModel;

/**
 * @author Udayakumar.rajan
 *
 */
@RestController
public class SearchFareController {
	
	String jsonPath = "data/routefare.json";
	
	@Autowired
	private SearchFareService service;
	
	private Logger logger = LoggerFactory.getLogger(SearchFareController.class);
	
	/**
	 * Based on the user search input, it will search and list the fares
	 * @param searchModel
	 * @return
	 */
	@RequestMapping(value = "/searchFares", method = RequestMethod.POST)
    public ResponseEntity<List<FareResult>> searchAirports(@RequestBody SearchModel searchModel) {
		return new ResponseEntity<>(service.searchRouteFare(searchModel), HttpStatus.CREATED);
    }

}
