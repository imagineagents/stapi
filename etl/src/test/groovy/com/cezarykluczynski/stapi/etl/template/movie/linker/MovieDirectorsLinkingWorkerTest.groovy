package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Sets
import spock.lang.Specification

class MovieDirectorsLinkingWorkerTest extends Specification {

	private SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelperMock

	private MovieDirectorsLinkingWorker movieDirectorsLinkingWorker

	def setup() {
		simpleMovieRealPeopleLinkingWorkerHelperMock = Mock(SimpleMovieRealPeopleLinkingWorkerHelper)
		movieDirectorsLinkingWorker = new MovieDirectorsLinkingWorker(simpleMovieRealPeopleLinkingWorkerHelperMock)
	}

	def "adds directors found by SimpleMovieRealPeopleLinkingWorkerHelper"() {
		given:
		LinkedHashSet<List<String>> source = Sets.newHashSet()
		Staff director = new Staff()
		Movie baseEntity = new Movie()

		when:
		movieDirectorsLinkingWorker.link(source, baseEntity)

		then:
		1 * simpleMovieRealPeopleLinkingWorkerHelperMock.linkListsToStaff(source, MediaWikiSource.MEMORY_ALPHA_EN) >> Sets.newHashSet(director)
		0 * _
		baseEntity.directors.size() == 1
		baseEntity.directors.contains director
	}

}
