package com.saniyat.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
	private final WebClient webClient;

	@Autowired
	public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
			@Value("${auth.service.url}") String authServiceUrl) {
		this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
	}

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

			if (token == null || !token.startsWith("Bearer ")) {
				ServerHttpResponse response = exchange.getResponse();
				response.setStatusCode(HttpStatus.BAD_REQUEST);
				response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				String message = "{\"error\": \"Missing Authorization header\"}";
				DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());
				return response.writeWith(Mono.just(buffer));
			}

			return webClient.get()
					.uri("/validate")
					.header(HttpHeaders.AUTHORIZATION, token)
					.retrieve()
					.toBodilessEntity()
					.then(chain.filter(exchange));
		};
	}
}
