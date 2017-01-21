package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToLocalDateProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class ActorTemplateToLifeRangeProcessorTest extends Specification {

	private static final String INVALID_TEMPLATE_NAME = 'INVALID_TEMPLATE_NAME'

	private DatelinkTemplateToLocalDateProcessor datelinkTemplateToLocalDateProcessorMock

	private TemplateFilter templateFilterMock

	private ActorTemplateToLifeRangeProcessor actorTemplateToLifeRangeProcessor

	void setup() {
		datelinkTemplateToLocalDateProcessorMock = Mock(DatelinkTemplateToLocalDateProcessor)
		templateFilterMock = Mock(TemplateFilter)
		actorTemplateToLifeRangeProcessor = new ActorTemplateToLifeRangeProcessor(
				datelinkTemplateToLocalDateProcessorMock, templateFilterMock)
	}

	void "template of different title produces null LocalDate"() {
		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(new Template(title: 'different template'))

		then:
		dateRange == null
	}

	void "gets date of birth and date of death from child tempaltes"() {
		LocalDate dateOfBirth = LocalDate.of(1955, 2, 2)
		LocalDate dateOfDeath = LocalDate.of(2009, 5, 5)

		given:
		Template templateDateOfBirth = new Template(title: TemplateName.D)
		Template templateDateOfDeath = new Template(title: TemplateName.DATELINK)
		List<Template> templatesDateOfBirth = Lists.newArrayList(templateDateOfBirth)
		List<Template> templatesDateOfDeath = Lists.newArrayList(templateDateOfDeath)
		Template template = new Template(title: TemplateName.SIDEBAR_ACTOR,
				parts: Lists.newArrayList(
						new Template.Part(
								key: ActorTemplateToLifeRangeProcessor.KEY_DATE_OF_BIRTH,
								templates: templatesDateOfBirth),
						new Template.Part(
								key: ActorTemplateToLifeRangeProcessor.KEY_DATE_OF_DEATH,
								templates: templatesDateOfDeath)))

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		1 * templateFilterMock.filterByTitle(templatesDateOfBirth, TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList(templateDateOfBirth)
		1 * templateFilterMock.filterByTitle(templatesDateOfDeath, TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList(templatesDateOfDeath)
		1 * datelinkTemplateToLocalDateProcessorMock.process(templateDateOfBirth) >> dateOfBirth
		1 * datelinkTemplateToLocalDateProcessorMock.process(templateDateOfDeath) >> dateOfDeath
		dateRange.startDate == dateOfBirth
		dateRange.endDate == dateOfDeath
	}

	void "returns null when no 'd' or 'datelink' templates were found"() {
		given:
		Template template = new Template(title: TemplateName.SIDEBAR_ACTOR,
				parts: Lists.newArrayList())

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		dateRange == null
	}

	void "returns null when templates child templates list is empty or does not contain 'd' nor 'datelink' templates"() {
		given:
		Template invalidTemplate = new Template(title: INVALID_TEMPLATE_NAME)
		Template template = new Template(title: TemplateName.SIDEBAR_ACTOR,
				parts: Lists.newArrayList(
						new Template.Part(
								key: ActorTemplateToLifeRangeProcessor.KEY_DATE_OF_BIRTH,
								templates: Lists.newArrayList()),
						new Template.Part(
								key: ActorTemplateToLifeRangeProcessor.KEY_DATE_OF_DEATH,
								templates: Lists.newArrayList(invalidTemplate))))

		when:
		DateRange dateRange = actorTemplateToLifeRangeProcessor.process(template)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(invalidTemplate), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList()
		dateRange == null
	}

}
