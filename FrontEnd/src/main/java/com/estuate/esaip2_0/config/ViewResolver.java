/**
 * 
 */
package com.estuate.esaip2_0.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author rgopalraj
 *
 */
public class ViewResolver implements WebMvcConfigurer {
	@Override 
	public void addViewControllers(ViewControllerRegistry registry) { 
		WebMvcConfigurer.super.addViewControllers(registry); 
		registry.addViewController("/").setViewName("index"); 
	} 
}
