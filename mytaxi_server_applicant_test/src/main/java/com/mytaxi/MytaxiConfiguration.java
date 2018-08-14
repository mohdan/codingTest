package com.mytaxi;

import org.springframework.beans.factory.annotation.Value;

@org.springframework.context.annotation.Configuration
public class MytaxiConfiguration
{

    @Value("${mytaxi.service.encryption.key}")
    private String mytaxiServiceEncryptionKey;

    @Value("${mytaxi.service.oauth2.client}")
    private String mytaxiServiceOauth2Client;

    @Value("${mytaxi.service.oauth2.grant}")
    private String[] mytaxiServiceOauth2Grant;

    @Value("${mytaxi.service.oauth2.validity}")
    private Integer mytaxiServiceOauth2Validity;

    @Value("${mytaxi.service.oauth2.secret}")
    private String mytaxiServiceOauth2Secret;


    public String getMytaxiServiceEncryptionKey()
    {
        return mytaxiServiceEncryptionKey;
    }


    public String getMytaxiServiceOauth2Client()
    {
        return mytaxiServiceOauth2Client;
    }


    public String[] getMytaxiServiceOauth2Grant()
    {
        return mytaxiServiceOauth2Grant;
    }


    public Integer getMytaxiServiceOauth2Validity()
    {
        return mytaxiServiceOauth2Validity;
    }


    public String getMytaxiServiceOauth2Secret()
    {
        return mytaxiServiceOauth2Secret;
    }

}
