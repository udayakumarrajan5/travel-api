/**
 * 
 */
package com.afkl.cases.df.controller.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.afkl.cases.df.metrics.service.MetricService;
import com.afkl.cases.df.model.FareResult;
import com.afkl.cases.df.model.Flight;
import com.afkl.cases.df.model.RouteFare;
import com.afkl.cases.df.model.SearchModel;
import com.afkl.cases.df.model.ValidBetween;

/**
 * @author Udayakumar.Rajan
 *
 */
@Service
public class SearchFareService {

	@Autowired
	FlightService flightService;

	@Autowired
	FareService fareService;
	
	@Autowired
	MetricService metricServices;
	
	private static Logger logger = LoggerFactory.getLogger(SearchFareService.class);
	SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * To get fare information by user entered data.
	 * @param searchModel
	 * @return
	 */
	public List<FareResult> searchRouteFare(SearchModel searchModel) {
		List<FareResult> fareResults = new ArrayList<>();
		String origin = searchModel.getOrigin();
		String destination = searchModel.getDestination();
		String depart = searchModel.getDepart();
		String unquieId = UUID.randomUUID().toString();
		StringBuffer logBuffer = new StringBuffer();
		int adultCount = searchModel.getAdultCount() == null ? 1 : searchModel.getAdultCount();
		int childCount = searchModel.getChildCount() == null ? 0 : searchModel.getChildCount();
		int totalPax = adultCount + childCount;
		logBuffer.append("txn-id :").append(unquieId);
		logBuffer.append(" [Origin : ").append(origin).append(", Destination : ").append(destination).append(", Date : ")
				.append(depart).append("]");
		logger.info(" Request received to search fares for " + logBuffer.toString());
		long start = System.currentTimeMillis();
		List<Flight> matchedFlts = flightService
				.getFlights().stream().filter(flt -> flt.getOrigin().equals(origin)
						&& flt.getDestination().equals(destination) && isDateBetween(depart, flt.getValidBetween()))
				.collect(Collectors.toList());

		matchedFlts.stream().forEach(flt -> {
			fareService.getRouteFare().stream().filter(fare -> fare.getApplicableFlights().contains(flt.getId()))
					.collect(Collectors.toList()).forEach(fare -> {
						
						
						
						FareResult fareResult = new FareResult();
						fareResults.add(fareResult);
						fareResult.setOrigin(searchModel.getOriginDesc());
						fareResult.setDestination(searchModel.getDestDesc());
						fareResult.setFlightNumber(flt.getCarrierCode() + flt.getFlightNumber());
						fareResult.setDeparture(depart);
						fareResult.setTotalJouneryTime(flt.getFlightTime());
						fareResult.setDepartTime(flt.getDepartTime());
						fareResult.setArrivalTime(flt.getArrivalTime());
						fareResult.setEconomyFare(StringUtils.isEmpty(fare.getEconomyFare()) ? "No Seats"
								: fare.getCurrencyCode() + " " + Double.valueOf(fare.getEconomyFare())*totalPax);
						fareResult.setBusinessFare(StringUtils.isEmpty(fare.getBusinessFare()) ? "No Seats"
								: fare.getCurrencyCode() + " " + Double.valueOf(fare.getBusinessFare())*totalPax);

					});
		});
		long end = System.currentTimeMillis() - start;
		logger.info("Total Time taken for " +logBuffer.toString() + " " + end+ " ms");
		metricServices.addResponseTime(unquieId, Long.valueOf(end).intValue());
		return fareResults;
	}
	
	private boolean isDateBetween (String searchDate, ValidBetween between) {
		Date start = between.getStart(); 
		Date end = between.getEnd();
		try {
			Date departDate = dateformat.parse(searchDate);
			return departDate.compareTo(start) >= 0 && departDate.compareTo(end) <= 0;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return false;
	}

}
