/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth2.core.endpoint;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * A representation of an <i>OAuth 2.0 Access Token Response</i>.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see OAuth2AccessToken
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-5.1">Section 5.1 Access Token Response</a>
 */
public final class OAuth2AccessTokenResponse {
	private OAuth2AccessToken accessToken;
	private Map<String,Object> additionalParameters;

	private OAuth2AccessTokenResponse() {
	}

	public String getTokenValue() {
		return this.accessToken.getTokenValue();
	}

	public OAuth2AccessToken.TokenType getTokenType() {
		return this.accessToken.getTokenType();
	}

	public Instant getIssuedAt() {
		return this.accessToken.getIssuedAt();
	}

	public Instant getExpiresAt() {
		return this.accessToken.getExpiresAt();
	}

	public Set<String> getScopes() {
		return this.accessToken.getScopes();
	}

	public Map<String, Object> getAdditionalParameters() {
		return this.additionalParameters;
	}

	public static Builder withToken(String tokenValue) {
		return new Builder(tokenValue);
	}

	public static class Builder {
		private String tokenValue;
		private OAuth2AccessToken.TokenType tokenType;
		private long expiresIn;
		private Set<String> scopes;
		private Map<String,Object> additionalParameters;

		private Builder(String tokenValue) {
			this.tokenValue = tokenValue;
		}

		public Builder tokenType(OAuth2AccessToken.TokenType tokenType) {
			this.tokenType = tokenType;
			return this;
		}

		public Builder expiresIn(long expiresIn) {
			this.expiresIn = expiresIn;
			return this;
		}

		public Builder scopes(Set<String> scopes) {
			this.scopes = scopes;
			return this;
		}

		public Builder additionalParameters(Map<String,Object> additionalParameters) {
			this.additionalParameters = additionalParameters;
			return this;
		}

		public OAuth2AccessTokenResponse build() {
			Assert.isTrue(this.expiresIn >= 0, "expiresIn must be a positive number");
			Instant issuedAt = Instant.now();
			OAuth2AccessTokenResponse accessTokenResponse = new OAuth2AccessTokenResponse();
			accessTokenResponse.accessToken = new OAuth2AccessToken(this.tokenType, this.tokenValue, issuedAt,
				issuedAt.plusSeconds(this.expiresIn), this.scopes);
			accessTokenResponse.additionalParameters = Collections.unmodifiableMap(
				CollectionUtils.isEmpty(this.additionalParameters) ? Collections.emptyMap() : this.additionalParameters);
			return accessTokenResponse;
		}
	}
}
