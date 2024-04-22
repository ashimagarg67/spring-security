/*
 * Copyright 2002-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.oauth2.client.endpoint;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * Default Converter used by the
 * {@link OAuth2AuthorizationCodeGrantRequestEntityConverter} that convert from an
 * implementation of an {@link AbstractOAuth2AuthorizationGrantRequest} to a
 * {@link RequestEntity} representation of an OAuth 2.0 Access Token Request for the
 * specific Authorization Grant.
 *
 * @author Peter Eastham
 * @author Joe Grandja
 * @since 6.3
 * @see OAuth2ClientCredentialsGrantRequestEntityConverter
 */
public class DefaultOAuth2TokenRequestHeadersConverter<T extends AbstractOAuth2AuthorizationGrantRequest>
		implements Converter<T, HttpHeaders> {

	private static final HttpHeaders DEFAULT_TOKEN_HEADERS = getDefaultTokenRequestHeaders();

	private boolean encodeClientCredentials = true;

	private static HttpHeaders getDefaultTokenRequestHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		final MediaType contentType = MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
		headers.setContentType(contentType);
		return headers;
	}

	@Override
	public HttpHeaders convert(T source) {
		HttpHeaders headers = new HttpHeaders();
		headers.addAll(DEFAULT_TOKEN_HEADERS);
		ClientRegistration clientRegistration = source.getClientRegistration();
		if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.equals(clientRegistration.getClientAuthenticationMethod())) {
			String clientId = this.encodeClientCredentials ? encodeClientCredential(clientRegistration.getClientId())
					: clientRegistration.getClientId();
			String clientSecret = this.encodeClientCredentials
					? encodeClientCredential(clientRegistration.getClientSecret())
					: clientRegistration.getClientSecret();
			headers.setBasicAuth(clientId, clientSecret);
		}
		return headers;
	}

	private static String encodeClientCredential(String clientCredential) {
		return URLEncoder.encode(clientCredential, StandardCharsets.UTF_8);
	}

	public void setEncodeClientCredentials(boolean encodeClientCredentials) {
		this.encodeClientCredentials = encodeClientCredentials;
	}

}
