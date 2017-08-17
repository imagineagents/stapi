package com.cezarykluczynski.stapi.etl.template.starship_class.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplate;
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.StarshipClassTemplateParameter;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class StarshipClassTemplateContentsEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Template, StarshipClassTemplate>> {

	private final NumberOfPartsProcessor numberOfPartsProcessor;

	private final StarshipClassWarpCapableProcessor starshipClassWarpCapableProcessor;

	@Inject
	public StarshipClassTemplateContentsEnrichingProcessor(NumberOfPartsProcessor numberOfPartsProcessor,
			StarshipClassWarpCapableProcessor starshipClassWarpCapableProcessor) {
		this.numberOfPartsProcessor = numberOfPartsProcessor;
		this.starshipClassWarpCapableProcessor = starshipClassWarpCapableProcessor;
	}

	@Override
	public void enrich(EnrichablePair<Template, StarshipClassTemplate> enrichablePair) throws Exception {
		Template template = enrichablePair.getInput();
		StarshipClassTemplate starshipClassTemplate = enrichablePair.getOutput();

		for (Template.Part part : template.getParts()) {
			String key = part.getKey();
			String value = part.getValue();

			switch (key) {
				case StarshipClassTemplateParameter.DECKS:
					starshipClassTemplate.setNumberOfDecks(numberOfPartsProcessor.process(value));
					break;
				case StarshipClassTemplateParameter.SPEED:
					starshipClassTemplate.setWarpCapable(starshipClassWarpCapableProcessor.process(value));
					break;
				case StarshipClassTemplateParameter.ACTIVE:
					// TODO
					break;
				default:
					break;
			}
		}
	}

}
