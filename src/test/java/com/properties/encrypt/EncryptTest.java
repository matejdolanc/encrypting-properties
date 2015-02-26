package com.properties.encrypt;

import org.junit.Test;

public class EncryptTest extends BaseTest {

	@Test
	public void encryptDecryptTest() throws Exception {

		for (String env : enviroments) {

			System.out.println(env);
			
			String pw = env + password;
			if ("testing".equals(env)) {
				pw = "testing";
			}

			app.encryptPropertyValue(properiesPath + env + ".properties", pw);
		}
	}
}
