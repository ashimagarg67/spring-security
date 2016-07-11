/*
 * Copyright 2015-2016 the original author or authors.
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

package org.springframework.security.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * Jackson module for spring-security-core. This module register {@link AnonymousAuthenticationTokenMixin},
 * {@link RememberMeAuthenticationTokenMixin}, {@link SimpleGrantedAuthorityMixin}, {@link UnmodifiableSetMixin},
 * {@link UserMixin} and {@link UsernamePasswordAuthenticationTokenMixin}. In order to use this module just
 * add this module into your ObjectMapper configuration.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new CoreJackson2SimpleModule());
 * </pre>
 *
 * @author Jitendra Singh.
 */
public class CoreJackson2SimpleModule extends SimpleModule {

	public CoreJackson2SimpleModule() {
		super(CoreJackson2SimpleModule.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		context.setMixInAnnotations(AnonymousAuthenticationToken.class, AnonymousAuthenticationTokenMixin.class);
		context.setMixInAnnotations(RememberMeAuthenticationToken.class, RememberMeAuthenticationTokenMixin.class);
		context.setMixInAnnotations(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);
		context.setMixInAnnotations(Collections.unmodifiableSet(Collections.EMPTY_SET).getClass(), UnmodifiableSetMixin.class);
		context.setMixInAnnotations(User.class, UserMixin.class);
		context.setMixInAnnotations(UsernamePasswordAuthenticationToken.class, UsernamePasswordAuthenticationTokenMixin.class);
	}
}
