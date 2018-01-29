package br.com.produban.agencia.ligacoes.reader.service;

import java.net.URI;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.produban.agencia.ligacoes.dto.LigacoesDto;

@Component
public class AgenciaLigacoesService {

	@Autowired
	private RestTemplate restTemplate;

	private static Logger logger = Logger.getLogger(LigacoesDto.class);

	private String url;

	public LigacoesDto save(LigacoesDto dto) {

		URI uri = UriComponentsBuilder.fromHttpUrl(url)
				// .pathSegment("api", "deploy", "remedypeticao")
				.build().toUri();
		ResponseEntity<LigacoesDto> result = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(dto, new HttpHeaders()), LigacoesDto.class);

		return result.getBody();
	}

}
