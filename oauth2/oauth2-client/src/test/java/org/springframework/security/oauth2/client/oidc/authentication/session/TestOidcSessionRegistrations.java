/*
 * Copyright 2002-2023 the original author or authors.
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

package org.springframework.security.oauth2.client.oidc.authentication.session;

import java.util.Map;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.TestOidcUsers;

public final class TestOidcSessionRegistrations {

	public static OidcSessionRegistration create() {
		return create("sessionId");
	}

	public static OidcSessionRegistration create(String sessionId) {
		return create(sessionId, TestOidcUsers.create());
	}

	public static OidcSessionRegistration create(String sessionId, OidcUser user) {
		return new OidcSessionRegistration(sessionId, Map.of("_csrf", "token"), user);
	}

	private TestOidcSessionRegistrations() {

	}

}
