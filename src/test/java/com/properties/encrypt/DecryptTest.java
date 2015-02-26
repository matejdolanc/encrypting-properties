package com.properties.encrypt;

import org.junit.Test;

public class DecryptTest extends BaseTest {

	@Test
	public void decryptTest() throws Exception {

		for (String env : enviroments) {

			String pw = env + password;
			if ("testing".equals(env)) {
				pw = "testing";
			}
			app.decryptPropertyValue(properiesPath + env + ".properties", pw);

		}
	}
}
