package com.properties.encrypt;

public class BaseTest {

	// Name of the properties file that needs encryption
//	protected String properiesPath = "/Users/matejdolanc/JAVA/Test/src/main/resources/";
	protected String properiesPath = "";
	
	// ${env}+${passphrase}
	protected String password = "myPassword";

	protected EncryptDecrypt app = new EncryptDecrypt();

	protected String[] enviroments = { "local", "testing", "dev", "test", "stage" };

}
