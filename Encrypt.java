package com.utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Encrypt {
	
  
	public static final String AESCBCPKCS5PADDING = "AES/CBC/PKCS5Padding";
	public static final String UTF8 = "UTF-8";
	private static final Logger logger = LoggerFactory.getLogger(Encrypt.class);

	@Autowired
	public SecretKey secretKey;
	 
	public String decrypt(String strToDecrypt) {
	    
		try {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);	                 
	        Cipher cipher = Cipher.getInstance(AESCBCPKCS5PADDING);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
	        return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	    }
	    catch (Exception e) {
	    	logger.error("Error while decrypting: " + e.toString());
	    }
	    return null;
	}
	
	public String encrypt(String strToEncrypt){
		
	    try {
	        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	        IvParameterSpec ivspec = new IvParameterSpec(iv);
	        Cipher cipher = Cipher.getInstance(AESCBCPKCS5PADDING);
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
	        return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(UTF8)));
	    }
	    catch (Exception e){
	    	logger.error("Error while encrypting: " + e.toString());
	    }
	    return null;
	}	
}
