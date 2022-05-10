package com.rsaen.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rsaen.bean.RequestBean;
import com.rsaen.bean.ResponseBean;
import com.rsaen.service.DecryptService;
import com.rsaen.service.EncryptService;
import com.rsaen.service.KeyGeneratorService;

@RestController
public class RsaController {

	@Autowired
	KeyGeneratorService keyGeneratorService;
	
	@Autowired
	DecryptService decryptService;
	
	@Autowired
	EncryptService encryptService;

	@GetMapping(value = "/generate/keys")
	public ResponseBean generateKeys() {
		keyGeneratorService.generateKeys();
		return new ResponseBean("Keys Generated Sucessfully");

	}

	@GetMapping(value = "/encrypt/{message}")
	public ResponseBean encryptMessage(@PathVariable("message") String messageRequest) {

		try {
			String encryptedMessage = encryptService.encryptMessage(messageRequest);
			return new ResponseBean(encryptedMessage);

		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/rsa")
	public ResponseBean encryptMessage(@RequestBody RequestBean req)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, IOException {

		try {

			if (req.getAction().equalsIgnoreCase("ENCRYPT")) {
				String encryptedMessage = encryptService.encryptMessage(req.getMessage());
				return new ResponseBean(encryptedMessage);
			}

			else if (req.getAction().equalsIgnoreCase("DECRYPT")) {

				String decryptedMessage = decryptService.decryptMessage(req.getMessage());
				return new ResponseBean(decryptedMessage);
			} else {
				throw new Exception("Error");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;

	}

}
