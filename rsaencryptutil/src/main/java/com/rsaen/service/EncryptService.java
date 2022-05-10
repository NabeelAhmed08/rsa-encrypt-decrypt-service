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
public class EncryptService {

	@Autowired
	CommonService utilService;
	
	/**
	 * Method to RSA Encrypt the input string and return base64URL encoded value
	 * @param secretMessage
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String encryptMessage(String secretMessage ) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		//1.Create Cifer instance with Encrypt mode
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE,utilService.getPublicKeysFromFile() );
		
		//2.Convert message string into bytes
		byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
		
		//3.Call Do final method and pass msg bytes
		byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
		
		//4.Encode the encrypted msg bytes using base64 encoder
		String encodedMessage = Base64.getUrlEncoder().encodeToString(encryptedMessageBytes);
		
		return encodedMessage;
	}
}
