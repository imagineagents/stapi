package com.cezarykluczynski.stapi.etl.comicStrip.creation.processor

import com.cezarykluczynski.stapi.etl.template.comicStrip.dto.ComicStripTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractComicStripTest
import com.google.common.collect.Sets

class ComicStripTemplateProcessorTest extends AbstractComicStripTest {

	private final Page page = Mock(Page)

	private GuidGenerator guidGeneratorMock

	private ComicStripTemplateProcessor comicStripTemplateProcessor

	void setup() {
		guidGeneratorMock = Mock(GuidGenerator)
		comicStripTemplateProcessor = new ComicStripTemplateProcessor(guidGeneratorMock)
	}

	void "converts ComicStripTemplate to ComicStrip"() {
		given:
		ComicSeries comicSeries1 = Mock(ComicSeries)
		ComicSeries comicSeries2 = Mock(ComicSeries)
		Staff writer1 = Mock(Staff)
		Staff writer2 = Mock(Staff)
		Staff artist1 = Mock(Staff)
		Staff artist2 = Mock(Staff)
		Character character1 = Mock(Character)
		Character character2 = Mock(Character)

		ComicStripTemplate comicStripTemplate = new ComicStripTemplate(
				page: page,
				title: TITLE,
				periodical: PERIODICAL,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedMonthFrom: PUBLISHED_MONTH_FROM,
				publishedDayFrom: PUBLISHED_DAY_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				publishedMonthTo: PUBLISHED_MONTH_TO,
				publishedDayTo: PUBLISHED_DAY_TO,
				numberOfPages: NUMBER_OF_PAGES,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				comicSeries: Sets.newHashSet(comicSeries1, comicSeries2),
				writers: Sets.newHashSet(writer1, writer2),
				artists: Sets.newHashSet(artist1, artist2),
				characters: Sets.newHashSet(character1, character2))

		when:
		ComicStrip comicStrip = comicStripTemplateProcessor.process(comicStripTemplate)

		then:
		1 * guidGeneratorMock.generateFromPage(page, ComicStrip) >> GUID
		comicStrip.guid == GUID
		comicStrip.page == page
		comicStrip.title == TITLE
		comicStrip.periodical == PERIODICAL
		comicStrip.publishedYearFrom == PUBLISHED_YEAR_FROM
		comicStrip.publishedMonthFrom == PUBLISHED_MONTH_FROM
		comicStrip.publishedDayFrom == PUBLISHED_DAY_FROM
		comicStrip.publishedYearTo == PUBLISHED_YEAR_TO
		comicStrip.publishedMonthTo == PUBLISHED_MONTH_TO
		comicStrip.publishedDayTo == PUBLISHED_DAY_TO
		comicStrip.numberOfPages == NUMBER_OF_PAGES
		comicStrip.yearFrom == YEAR_FROM
		comicStrip.yearTo == YEAR_TO
		comicStrip.comicSeries.size() == 2
		comicStrip.comicSeries.contains comicSeries1
		comicStrip.comicSeries.contains comicSeries2
		comicStrip.writers.size() == 2
		comicStrip.writers.contains writer1
		comicStrip.writers.contains writer2
		comicStrip.artists.size() == 2
		comicStrip.artists.contains artist1
		comicStrip.artists.contains artist2
		comicStrip.characters.size() == 2
		comicStrip.characters.contains character1
		comicStrip.characters.contains character2
	}

}