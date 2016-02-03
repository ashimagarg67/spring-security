package org.springframework.security.authentication.encoding;

import org.springframework.security.crypto.codec.Utf8;

import java.io.UnsupportedEncodingException;

/**
 * Utility for constant time comparison to prevent against timing attacks.
 *
 * @author Rob Winch
 */
class PasswordEncoderUtils {

	/**
	 * Constant time comparison to prevent against timing attacks.
	 * @param expected
	 * @param actual
	 * @return
	 */
	static boolean equals(String expected, String actual) {
		byte[] expectedBytes = bytesUtf8(expected);
		byte[] actualBytes = bytesUtf8(actual);
		int expectedLength = expectedBytes == null ? 0 : expectedBytes.length;
		int actualLength = actualBytes == null ? 0 : actualBytes.length;
		byte[] tmpBytes = new byte[1];
		int result = (expectedLength != actualLength) ? 1 : 0;
		
		tmpBytes[0] = (byte) 0xFF; // value is ignored, just initializing.
		result |= ((expectedBytes == null && actualBytes != null) || (expectedBytes != null && actualBytes == null)) ? 1 : 0;
		
		expectedBytes = (expectedBytes == null ? expectedBytes : tmpBytes);

		for (int i = 0; i < actualLength; i++) {
			result |= expectedBytes[i % (expectedLength!=0?expectedLength:1)] ^ actualBytes[i % actualLength];
		}
		return result == 0;
	}

	private static byte[] bytesUtf8(String s) {
		if (s == null) {
			return null;
		}

		return Utf8.encode(s); // need to check if Utf8.encode() runs in constant time (probably not). This may leak length of string.
	}

	private PasswordEncoderUtils() {
	}
}
