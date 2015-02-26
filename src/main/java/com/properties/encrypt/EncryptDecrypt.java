package com.properties.encrypt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * The Class EncryptDecrypt.
 */
public class EncryptDecrypt {

	/** The encryptor. */
	private StandardPBEStringEncryptor encryptor;

	/**
	 * Instantiates a new encrypt decrypt.
	 */
	public EncryptDecrypt() {

	}

	private void init() {
		encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm("PBEWithMD5AndDES");

	}

	/**
	 * Gets the key list.
	 * 
	 * @param config
	 *            the config
	 * @return the key list
	 */
	private List<String> getKeyList(PropertiesConfiguration config) {
		@SuppressWarnings("unchecked")
		Iterator<String> keys = config.getKeys();
		List<String> keyList = new ArrayList<String>();
		while (keys.hasNext()) {
			keyList.add(keys.next());
		}
		return keyList;
	}

	/**
	 * Encrypt property value.
	 * 
	 * @param propertyFileName
	 *            the property file name
	 * @param password
	 *            the password
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public void encryptPropertyValue(String propertyFileName, String password) throws ConfigurationException {

		System.out.println("Starting encryption operation");
		// Apache Commons Configuration
		PropertiesConfiguration config = new PropertiesConfiguration(propertyFileName);

		List<String> keyList = this.getKeyList(config);

		init();
		encryptor.setPassword(password);

		for (String key : keyList) {
			if ((key.contains("password") || key.contains("key") || key.contains("secret")) && !key.contains("key.prefix") && !key.contains("keystore")) {
				String tmpPwd = config.getString(key);

				if (!tmpPwd.startsWith("ENC(") && !tmpPwd.endsWith(")")) {
					String encryptedPassword = encryptor.encrypt(tmpPwd);
					config.setProperty(key, "ENC(" + encryptedPassword + ")");
				}
			}
		}

		// Save the properties file
		config.save();

	}

	/**
	 * Decrypt property value.
	 * 
	 * @param propertyFileName
	 *            the property file name
	 * @param password
	 *            the password
	 * @throws ConfigurationException
	 *             the configuration exception
	 */
	public void decryptPropertyValue(String propertyFileName, String password) throws ConfigurationException {
		System.out.println("Starting decryption");

		// Apache Commons Configuration
		PropertiesConfiguration config = new PropertiesConfiguration(propertyFileName);

		List<String> keyList = this.getKeyList(config);

		init();
		encryptor.setPassword(password);

		for (String key : keyList) {

			String tmpPwd = config.getString(key);
			if (tmpPwd.startsWith("ENC(") && tmpPwd.endsWith(")")) {

				tmpPwd = tmpPwd.substring(4, tmpPwd.length() - 1);

				String decryptedPassword = encryptor.decrypt(tmpPwd);

				config.setProperty(key, decryptedPassword);
			}
		}

		// Save the properties file
		config.save();
	}
}
