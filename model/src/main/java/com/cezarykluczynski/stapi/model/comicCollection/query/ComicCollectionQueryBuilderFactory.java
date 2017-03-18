package com.cezarykluczynski.stapi.model.comicCollection.query;

import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionQueryBuilderFactory extends AbstractQueryBuilderFactory<ComicCollection> {

	@Inject
	public ComicCollectionQueryBuilderFactory(JpaContext jpaContext) {
		super(jpaContext, ComicCollection.class);
	}

}