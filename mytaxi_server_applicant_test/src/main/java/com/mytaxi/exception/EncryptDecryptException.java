package com.mytaxi.exception;

public class EncryptDecryptException extends RuntimeException
{

    private static final long serialVersionUID = 1L;


    public EncryptDecryptException(final String message)
    {
        super(message);
    }

}
