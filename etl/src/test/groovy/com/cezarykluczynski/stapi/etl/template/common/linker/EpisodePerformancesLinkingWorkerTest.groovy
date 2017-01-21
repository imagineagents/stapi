package com.cezarykluczynski.stapi.etl.template.common.linker

import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformanceDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.EpisodePerformancesEntitiesDTO
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodePerformancesExtractingProcessor
import com.cezarykluczynski.stapi.etl.episode.creation.service.EpisodePerformancesToEntityMapper
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class EpisodePerformancesLinkingWorkerTest extends Specification {

	private CharacterRepository characterRepositoryMock

	private PerformerRepository performerRepositoryMock

	private EpisodePerformancesExtractingProcessor episodePerformancesExtractorMock

	private EpisodePerformancesToEntityMapper episodePerformancesToEntityMapperMock

	private EpisodePerformancesLinkingWorker episodePerformancesLinkingProcessor

	void setup() {
		characterRepositoryMock = Mock(CharacterRepository)
		performerRepositoryMock = Mock(PerformerRepository)
		episodePerformancesExtractorMock = Mock(EpisodePerformancesExtractingProcessor)
		episodePerformancesToEntityMapperMock = Mock(EpisodePerformancesToEntityMapper)
		episodePerformancesLinkingProcessor = new EpisodePerformancesLinkingWorker(characterRepositoryMock,
				performerRepositoryMock, episodePerformancesExtractorMock, episodePerformancesToEntityMapperMock)
	}

	void "when page has category episode, EpisodePerformancesExtractor is called"() {
		given:
		List<EpisodePerformanceDTO> episodePerformanceDTOList = Mock(List)
		Set<Character> charactersSet = Mock(Set)
		Set<Performer> performersSet = Mock(Set)
		EpisodePerformancesEntitiesDTO episodePerformancesEntitiesDTO = Mock(EpisodePerformancesEntitiesDTO)
		Episode episode = Mock(Episode)
		Page page = new Page(
				categories: Lists.newArrayList(
						new CategoryHeader(title: CategoryName.TOS_EPISODES)
				)
		)

		when:
		episodePerformancesLinkingProcessor.link(page, episode)

		then:
		1 * episodePerformancesExtractorMock.process(page) >> episodePerformanceDTOList
		1 * episodePerformancesToEntityMapperMock.mapToEntities(episodePerformanceDTOList, episode) >> episodePerformancesEntitiesDTO
		1 * episodePerformancesEntitiesDTO.characterSet >> charactersSet
		1 * characterRepositoryMock.save(charactersSet)
		1 * episodePerformancesEntitiesDTO.performerSet >> performersSet
		1 * performerRepositoryMock.save(performersSet)
	}

}
