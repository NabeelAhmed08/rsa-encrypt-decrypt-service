package com.rsaen.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

@Service
public class KeyGeneratorService {
	

	
	public void  generateKeys() {
		KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			KeyPair pair = generator.generateKeyPair();
			
			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();
			
			System.out.println(privateKey.getFormat());
//			System.out.println("private " + privateKey );
//			System.out.println("public " + publicKey );
						
			saveKeysInFile(publicKey,privateKey);
			
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	
	}

	@SuppressWarnings("resource")
	private void saveKeysInFile(PublicKey publicKey, PrivateKey privateKey) throws IOException {
		// TODO Auto-generated method stub
		
		
		 FileOutputStream fos = null;
		    
		 try {
			    fos = new FileOutputStream("public.key");
			    fos.write(publicKey.getEncoded());
			
			    fos = new FileOutputStream("private.key");
			    
			    fos.write(privateKey.getEncoded());
			    
			    
			
			    
			   
		} finally {
			// TODO: handle finally clause
			fos.close();	
		}   
		    
		
	}
	
	
	private PublicKey getPublicKeysFromFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File publicKeyFile = new File("public.key");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		//keyFactory.generatePublic(publicKeySpec);
		return keyFactory.generatePublic(publicKeySpec);
	}
	
	
	private PrivateKey getPrivateKeysFromFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File privateKeyFile = new File("private.key");
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		//keyFactory.generatePrivate(privateKeySpec);
		return  keyFactory.generatePrivate(privateKeySpec);
	}
	
	
	public String encryptMessage(String secretMessage ) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE,getPublicKeysFromFile() );
		
		byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
		byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
		
		String encodedMessage = Base64.getUrlEncoder().encodeToString(encryptedMessageBytes);
		
		return encodedMessage;
	}
	
	
	
	public String decryptMessage(String encodedMessage) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		
		Cipher decryptCipher = Cipher.getInstance("RSA");
		decryptCipher.init(Cipher.DECRYPT_MODE, getPrivateKeysFromFile());
		
		//convert base64 to bytes
		byte[] encryptedMessageInBytes = Base64.getUrlDecoder().decode(encodedMessage);
		byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageInBytes);
		String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
		
		return decryptedMessage;
		
	}
	
	

	
	
	
	
	

}
