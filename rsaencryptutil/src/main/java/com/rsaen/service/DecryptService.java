package com.rsaen.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DecryptService {
	
	@Autowired
	CommonService utilService;
	
	public String decryptMessage(String encodedMessage) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		
		Cipher decryptCipher = Cipher.getInstance("RSA");
		decryptCipher.init(Cipher.DECRYPT_MODE, utilService.getPrivateKeysFromFile());
		
		//convert base64 to bytes
		byte[] encryptedMessageInBytes = Base64.getUrlDecoder().decode(encodedMessage);
		byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageInBytes);
		String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
		
		return decryptedMessage;
		
	}

}
