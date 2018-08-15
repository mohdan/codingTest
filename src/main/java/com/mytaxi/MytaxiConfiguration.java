package com.mytaxi;

import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration
public class MytaxiConfiguration
{

    /**
     * Key to be used for AES encryption-decryption
     */
    //TODO - used before for encryption-decryption of password before Oauth2 - to be removed.
    @Value("${mytaxi.service.encryption.key}")
    private String mytaxiServiceEncryptionKey;

    /**
     * Below are attributes to be used for Oauth2
     */
    @Value("${mytaxi.service.oauth2.client}")
    private String mytaxiServiceOauth2Client;

    @Value("${mytaxi.service.oauth2.grant}")
    private String[] mytaxiServiceOauth2Grant;

    @Value("${mytaxi.service.oauth2.validity}")
    private Integer mytaxiServiceOauth2Validity;

    @Value("${mytaxi.service.oauth2.secret}")
    private String mytaxiServiceOauth2Secret;

    /**
     * @return the mytaxiServiceEncryptionKey
     */
    public String getMytaxiServiceEncryptionKey()
    {
        return mytaxiServiceEncryptionKey;
    }

    /**
     * @return the mytaxiServiceOauth2Client
     */
    public String getMytaxiServiceOauth2Client()
    {
        return mytaxiServiceOauth2Client;
    }

    /**
     * @return the mytaxiServiceOauth2Grant
     */
    public String[] getMytaxiServiceOauth2Grant()
    {
        return mytaxiServiceOauth2Grant;
    }

    /**
     * @return the mytaxiServiceOauth2Validity
     */
    public Integer getMytaxiServiceOauth2Validity()
    {
        return mytaxiServiceOauth2Validity;
    }

    /**
     * @return the mytaxiServiceOauth2Secret
     */
    public String getMytaxiServiceOauth2Secret()
    {
        return mytaxiServiceOauth2Secret;
    }
    
    

}
