package com.cezarykluczynski.stapi.etl.template.series.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.YearRange;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;

@Data
public class SeriesTemplate {

	private String title;

	private Page page;

	private String abbreviation;

	private YearRange productionYearRange;

	private DateRange originalRunDateRange;
}