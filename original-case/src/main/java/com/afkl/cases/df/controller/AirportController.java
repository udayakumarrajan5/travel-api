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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afkl.cases.df.controller.service.AirportService;
import com.afkl.cases.df.model.Airport;

/**
 * @author Udayakumar.rajan
 *
 */
@RestController
public class AirportController {
	
	@Autowired
	private AirportService service;
	
	private static Logger logger = LoggerFactory.getLogger(AirportController.class);
	
	/**
	 * To get the Airport information from json file.
	 * @return
	 */
	@RequestMapping(value = "/getAirports", method = RequestMethod.GET)
    public ResponseEntity<List<Airport>> getAirports() {
		return new ResponseEntity<>(service.getAirports(), HttpStatus.OK);
    }
	
	/**
	 * To search and get the matched airport/ Airport names.
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "/searchAirports", method = RequestMethod.POST)
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam(value="filter") String filter) {
		return new ResponseEntity<>(service.searchAirports(filter), HttpStatus.CREATED);
    }
}
