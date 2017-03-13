package com.cezarykluczynski.stapi.server.series.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesPortType;
import com.cezarykluczynski.stapi.server.series.reader.SeriesSoapReader;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public class SeriesSoapEndpoint implements SeriesPortType {

	private SeriesSoapReader seriesSoapReader;

	public SeriesSoapEndpoint(SeriesSoapReader seriesSoapReader) {
		this.seriesSoapReader = seriesSoapReader;
	}

	@Override
	public SeriesBaseResponse getSeriesBase(@WebParam(partName = "request", name = "SeriesBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/series") SeriesBaseRequest request) {
		return seriesSoapReader.readBase(request);
	}

	@Override
	public SeriesFullResponse getSeriesFull(@WebParam(partName = "request", name = "SeriesFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/series") SeriesFullRequest request) {
		return seriesSoapReader.readFull(request);
	}
}
