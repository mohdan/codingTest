package com.mytaxi.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mytaxi.MytaxiConfiguration;

@Component
public class EncryptionUtils
{

    @Autowired
    MytaxiConfiguration mytaxiConfiguration;

    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtils.class);


    public String encrypt(String toEncrypt) throws Exception
    {
        LOGGER.debug("Encryption");
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey key = new SecretKeySpec(mytaxiConfiguration.getMytaxiServiceEncryptionKey().getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(toEncrypt.getBytes());
        byte[] encryptedValue = Base64.encodeBase64(encrypted);
        return new String(encryptedValue);
    }


    public String decrypt(String encrypted) throws Exception
    {
        LOGGER.debug("Decryption");
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey key = new SecretKeySpec(mytaxiConfiguration.getMytaxiServiceEncryptionKey().getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.decodeBase64(encrypted.getBytes());
        byte[] original = cipher.doFinal(decodedBytes);
        return new String(original);
    }
}
