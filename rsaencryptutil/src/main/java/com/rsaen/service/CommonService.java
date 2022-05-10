package com.rsaen.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.stereotype.Service;

@Service
public class CommonService {
	
	protected PublicKey getPublicKeysFromFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File publicKeyFile = new File("public.key");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		return keyFactory.generatePublic(publicKeySpec);
	}
	
	
	protected PrivateKey getPrivateKeysFromFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File privateKeyFile = new File("private.key");
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		return  keyFactory.generatePrivate(privateKeySpec);
	}
	

}
