package com.rsaen.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.stereotype.Service;

@Service
public class KeyGeneratorService {

	/**
	 * Method to generate Keys
	 * 
	 */
	public void generateKeys() {
		KeyPairGenerator generator = null ;
		try {
			//Generate Key pairs
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			KeyPair keyPair = generator.generateKeyPair();

			//Save Keys into files  
			saveKeysInFile(keyPair.getPublic(), keyPair.getPrivate());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

	/**
	 * Method to Save Keys in file
	 * @param publicKey
	 * @param privateKey
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private void saveKeysInFile(PublicKey publicKey, PrivateKey privateKey) throws IOException {

		FileOutputStream fos = null;

		fos = new FileOutputStream("public.key");
		fos.write(publicKey.getEncoded());
		fos = new FileOutputStream("private.key");
		fos.write(privateKey.getEncoded());
	}

}
