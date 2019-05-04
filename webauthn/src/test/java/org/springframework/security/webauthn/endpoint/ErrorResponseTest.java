/*
 * Copyright 2002-2019 the original author or authors.
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

package org.springframework.security.webauthn.endpoint;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseTest {

	@Test
	public void constructor_test() {
		ErrorResponse errorResponse = new ErrorResponse("message");
		assertThat(errorResponse.getErrorMessage()).isEqualTo("message");
	}

	@Test
	public void equals_hashCode_test() {
		ErrorResponse instanceA = new ErrorResponse("message");
		ErrorResponse instanceB = new ErrorResponse("message");

		assertThat(instanceA).isEqualTo(instanceB);
		assertThat(instanceA).hasSameHashCodeAs(instanceB);
	}

}
