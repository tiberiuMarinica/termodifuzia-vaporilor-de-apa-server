package com.utcb.hidrotehnica.fizicaconstructiilor.configs;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.utcb.hidrotehnica.fizicaconstructiilor.endpoints.MainEndpoint;
import com.utcb.hidrotehnica.fizicaconstructiilor.endpoints.filters.CORSResponseFilter;

@Configuration
public class JerseyConfig extends ResourceConfig {
	
	public JerseyConfig() {
		register(MainEndpoint.class);
		register(CORSResponseFilter.class);
	}
	
}
