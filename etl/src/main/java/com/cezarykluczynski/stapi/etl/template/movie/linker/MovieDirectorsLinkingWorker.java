package com.cezarykluczynski.stapi.etl.template.movie.linker;

import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class MovieDirectorsLinkingWorker implements MovieRealPeopleLinkingWorker {

	private SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelper;

	@Inject
	public MovieDirectorsLinkingWorker(SimpleMovieRealPeopleLinkingWorkerHelper simpleMovieRealPeopleLinkingWorkerHelper) {
		this.simpleMovieRealPeopleLinkingWorkerHelper = simpleMovieRealPeopleLinkingWorkerHelper;
	}

	@Override
	public void link(Set<List<String>> source, Movie baseEntity) {
		baseEntity.getDirectors().addAll(simpleMovieRealPeopleLinkingWorkerHelper
				.linkListsToStaff(source, MovieRealPeopleLinkingWorker.SOURCE));
	}

}
