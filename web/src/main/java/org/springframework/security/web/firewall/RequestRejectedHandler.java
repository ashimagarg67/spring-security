/*
 * Copyright 2002-2020 the original author or authors.
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

package org.springframework.security.web.firewall;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Used by {@link org.springframework.security.web.FilterChainProxy} to handle an
 * <code>RequestRejectedException</code>.
 * Supports multiple beans of this handler. The handler is chosen by the result of
 * <code>shouldHandle</code> method - <code>true</code> means it is chosen.
 * If there are more than one handler to choose from, the first matching is used.
 * If no suitable handler is found, the {@link DefaultRequestRejectedHandler} is used.
 *
 * @author Leonard Brünings
 * @author Adam Ostrožlík
 * @since 5.4
 */
public interface RequestRejectedHandler {

	/**
	 * Handles an request rejected failure.
	 * @param request that resulted in an <code>RequestRejectedException</code>
	 * @param response so that the user agent can be advised of the failure
	 * @param requestRejectedException that caused the invocation
	 * @throws IOException in the event of an IOException
	 * @throws ServletException in the event of a ServletException
	 */
	void handle(HttpServletRequest request, HttpServletResponse response,
			RequestRejectedException requestRejectedException) throws IOException, ServletException;

	/**
	 * Determines if this handler should be invoked.
	 * First available handler is invoked if there are multiple suitables ones.
	 * @param request that resulted in an <code>RequestRejectedException</code>
	 */
	default boolean shouldHandle(HttpServletRequest request) {
		return true;
	}
}
