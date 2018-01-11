/**
 * 
 */
package com.afkl.cases.df.metrics.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.afkl.cases.df.model.Metrics;

/**
 * @author Udayakumar.Rajan
 *
 */
@Service
public class MetricService {

	private ConcurrentMap<String, Integer> statusMap = new ConcurrentHashMap<>();
	private Map<String, Integer> timeStamp = new TreeMap <>();
	
	
	
	@PostConstruct
	public void init() throws IOException { 
		
	}
	
	

	/**
	 * To track the status and the hit count of any service from the server.
	 * @param httpMethod
	 * @param uri
	 * @param status
	 */
	public void increaseCount(String httpMethod, String uri, int status) {
	//	ConcurrentHashMap<Object, Integer> statusMap = metricMap.get(httpMethod+ ":"+uri);
		if (statusMap == null) {
			statusMap = new ConcurrentHashMap<>();
		}
		
		
		
		if ("/travel/status-metric".equals(uri) || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith(".css"))
			return;
		Integer count = statusMap.get(String.valueOf(status));
		if (count == null) {
			count = 1;
		} else {
			count++;
		}
		statusMap.put(String.valueOf(status), count);
		//metricMap.put(httpMethod+ ":"+uri, statusMap);
		
		Integer totalCount = statusMap.get("total");
		if (totalCount == null) {
			totalCount = 1;
		} else {
			totalCount++;
		}
		statusMap.put("total", totalCount);
	}

	public List<Metrics> getFullMetric() {
		long start = System.currentTimeMillis(); 
		List<Metrics> metricList = new ArrayList<>();
		String summaryText = "Total number of requests resulted in {0} Response";
		statusMap.entrySet().stream().forEachOrdered(entry-> {
				Metrics metrics = new Metrics();
				String key =  entry.getKey();
				metrics.setMethodType(key.equals("total") ? "Total number of requests processed" : MessageFormat.format(summaryText, entry.getKey()));
				metrics.setCount(entry.getValue());
				metricList.add(metrics);
		});
		
		long end = System.currentTimeMillis() - start;
		timeStamp.put(LocalDateTime.now().toString(), Long.valueOf(end).intValue());
		List<Integer> responseTimes = new ArrayList<>();
		responseTimes.addAll(timeStamp.values());
		Collections.sort(responseTimes);
		int min = responseTimes.get(0);
		int max =  responseTimes.get(responseTimes.size()-1);
		int avg = responseTimes.stream().mapToInt(val -> val.intValue()).sum() / responseTimes.size();
		Metrics metrics = new Metrics();
		metrics.setMethodType("Min response time of all requests");
		metrics.setCount(min);
		metricList.add(metrics);
		
		metrics = new Metrics();
		metrics.setMethodType("Max response time of all requests");
		metrics.setCount(max);
		metricList.add(metrics);
		
		metrics = new Metrics();
		metrics.setMethodType("Avg. response time of all requests");
		metrics.setCount(avg);
		metricList.add(metrics);
		
		return metricList;
	}
	
	public void addResponseTime (String uniqueId , int time) {
		timeStamp.put(uniqueId + ":" + LocalDateTime.now().toString(), time);
	}
	
}