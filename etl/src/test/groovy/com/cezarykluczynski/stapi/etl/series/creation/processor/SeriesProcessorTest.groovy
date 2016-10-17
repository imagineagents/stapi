package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import com.cezarykluczynski.stapi.etl.template.series.processor.SeriesTemplatePageProcessor
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import spock.lang.Specification

class SeriesProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private SeriesTemplatePageProcessor pageProcessorMock

	private SeriesTemplateProcessor seriesTemplateProcessorMock

	private SeriesProcessor seriesProcessor

	def setup() {
		pageHeaderProcessorMock = Mock(PageHeaderProcessor)
		pageProcessorMock = Mock(SeriesTemplatePageProcessor)
		seriesTemplateProcessorMock = Mock(SeriesTemplateProcessor)
		seriesProcessor = new SeriesProcessor(pageHeaderProcessorMock, pageProcessorMock, seriesTemplateProcessorMock)
	}

	def "converts PageHeader to Series"() {
		given:
		PageHeader pageHeader = PageHeader.builder().build()
		Page page = new Page()
		SeriesTemplate seriesTemplate = new SeriesTemplate()
		Series series = new Series()

		when:
		Series outputSeries = seriesProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page
		1 * pageProcessorMock.process(page) >> seriesTemplate
		1 * seriesTemplateProcessorMock.process(seriesTemplate) >> series

		then: 'last processor output is returned'
		outputSeries == series
	}

}