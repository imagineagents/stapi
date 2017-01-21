package com.cezarykluczynski.stapi.etl.template.episode.processor

import spock.lang.Specification

class EpisodeTitleFixedValueProviderTest extends Specification {

	private EpisodeTitleFixedValueProvider episodeTitleFixedValueProvider

	void setup() {
		episodeTitleFixedValueProvider = new EpisodeTitleFixedValueProvider()
	}

	void "provides correct title"() {
		expect:
		episodeTitleFixedValueProvider.getSearchedValue('E┬▓').found
		episodeTitleFixedValueProvider.getSearchedValue('E┬▓').value == 'E²'
	}

}