/**
 * 
 */
package com.afkl.cases.df.metrics;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.afkl.cases.df.metrics.service.MetricService;

/**
 * @author Udayakumar.Rajan
 *
 */
@Component
@WebFilter(filterName="metricFilter", urlPatterns= "/*")
public class MetricFilter implements Filter {

	private MetricService metricService;

	@Override
	public void init(FilterConfig config) throws ServletException {
		metricService = (MetricService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext()).getBean("metricService");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		String uri = httpRequest.getRequestURI();

		chain.doFilter(request, response);

		int status = ((HttpServletResponse) response).getStatus();
		metricService.increaseCount(httpRequest.getMethod(), uri, status);
	}

	@Override
	public void destroy() {
		System.out.println("..................Destory....");

	}
}
