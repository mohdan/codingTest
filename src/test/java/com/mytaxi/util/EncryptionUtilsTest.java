package com.mytaxi.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.mytaxi.MytaxiServerApplicantTestApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MytaxiServerApplicantTestApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
public class EncryptionUtilsTest
{

    @Autowired
    EncryptionUtils encryptionUtils;


    @Test
    public void encryptTest() throws Exception
    {
        String toEncrypt = "My taxi coding test is good";
        toEncrypt = "driver06pw";
        String encrypted = encryptionUtils.encrypt(toEncrypt);
        String decrypted = encryptionUtils.decrypt(encrypted);
        System.out.println();
        System.out.println(encrypted);
        System.out.println();
        Assert.assertEquals(toEncrypt, decrypted);

    }


    @Test
    public void decryptTest() throws Exception
    {
        String toEncrypt = "My taxi coding test is good";
        String encrypted = encryptionUtils.encrypt(toEncrypt);
        String decrypted = encryptionUtils.decrypt(encrypted);
        Assert.assertEquals(toEncrypt, decrypted);
    }
}
