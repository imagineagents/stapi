package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate;
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter;
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToDayMonthYearProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookTemplatePublishedDatesEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template.Part, BookTemplate>> {

	private DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessor;

	@Inject
	public BookTemplatePublishedDatesEnrichingProcessor(DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessor) {
		this.datePartToDayMonthYearProcessor = datePartToDayMonthYearProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, BookTemplate> enrichablePair) throws Exception {
		Template.Part templatePart = enrichablePair.getInput();
		BookTemplate bookTemplate = enrichablePair.getOutput();
		String templatePartKey = templatePart.getKey();

		DayMonthYear dayMonthYear = datePartToDayMonthYearProcessor.process(templatePart);

		if (dayMonthYear == null) {
			return;
		}

		if (BookTemplateParameter.PUBLISHED.equals(templatePartKey)) {
			bookTemplate.setPublishedYear(dayMonthYear.getYear());
			bookTemplate.setPublishedMonth(dayMonthYear.getMonth());
			bookTemplate.setPublishedDay(dayMonthYear.getDay());
		} else if (BookTemplateParameter.AUDIOBOOK_PUBLISHED.equals(templatePartKey)) {
			bookTemplate.setAudiobookPublishedYear(dayMonthYear.getYear());
			bookTemplate.setAudiobookPublishedMonth(dayMonthYear.getMonth());
			bookTemplate.setAudiobookPublishedDay(dayMonthYear.getDay());
		}
	}

}
