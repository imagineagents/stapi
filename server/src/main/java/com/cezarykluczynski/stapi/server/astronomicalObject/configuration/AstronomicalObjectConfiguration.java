package com.cezarykluczynski.stapi.server.astronomicalObject.configuration;

import com.cezarykluczynski.stapi.server.astronomicalObject.endpoint.AstronomicalObjectSoapEndpoint;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseRestMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseSoapMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullRestMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;

@Configuration
public class AstronomicalObjectConfiguration {

	@Inject
	private EndpointFactory endpointFactory;

	@Bean
	public Endpoint astronomicalObjectEndpoint() {
		return endpointFactory.createSoapEndpoint(AstronomicalObjectSoapEndpoint.class, AstronomicalObjectSoapEndpoint.ADDRESS);
	}

	@Bean
	public AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper() {
		return Mappers.getMapper(AstronomicalObjectBaseSoapMapper.class);
	}

	@Bean
	public AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper() {
		return Mappers.getMapper(AstronomicalObjectFullSoapMapper.class);
	}

	@Bean
	public AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper() {
		return Mappers.getMapper(AstronomicalObjectBaseRestMapper.class);
	}

	@Bean
	public AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper() {
		return Mappers.getMapper(AstronomicalObjectFullRestMapper.class);
	}

}
