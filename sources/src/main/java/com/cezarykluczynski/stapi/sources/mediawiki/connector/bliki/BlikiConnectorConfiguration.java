package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki;

import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@EnableConfigurationProperties({MediaWikiSourcesProperties.class})
public class BlikiConnectorConfiguration {

	public static final String MEMORY_ALPHA_EN_USER_DECORATOR = "MEMORY_ALPHA_EN_USER_DECORATOR";
	public static final String MEMORY_BETA_EN_USER_DECORATOR = "MEMORY_BETA_EN_USER_DECORATOR";

	@Inject
	private MediaWikiSourcesProperties mediaWikiSourcesProperties;

	@Bean(name = MEMORY_ALPHA_EN_USER_DECORATOR)
	public UserDecorator memoryAlphaEnUserDecorator() {
		UserDecorator userDecorator = new UserDecorator("", "", mediaWikiSourcesProperties.getMemoryAlphaEnApiUrl());
		userDecorator.login();
		return userDecorator;
	}

	@Bean(name = MEMORY_BETA_EN_USER_DECORATOR)
	public UserDecorator memoryBetaEnUserDecorator() {
		UserDecorator userDecorator = new UserDecorator("", "", mediaWikiSourcesProperties.getMemoryBetaEnApiUrl());
		userDecorator.login();
		return userDecorator;
	}

}
