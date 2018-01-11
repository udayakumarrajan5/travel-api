/**
 * 
 */
package com.afkl.cases.df.metrics;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.afkl.cases.df.metrics.service.MetricService;
import com.afkl.cases.df.model.Metrics;

/**
 * @author Udayakumar.Rajan
 *
 */
@RestController
public class MetricsController {
	@Autowired
	 private MetricService metricService;
	
	@RequestMapping(value = "/status-metric", method = RequestMethod.GET)
	@ResponseBody
	public List<Metrics> getStatusMetric() {
	    return metricService.getFullMetric();
	}
	
}
