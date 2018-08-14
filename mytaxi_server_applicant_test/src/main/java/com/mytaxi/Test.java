package com.mytaxi;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Test
{

    public static void main(String arg[])
    {

        int count = 0;

        while (count < 10)
        {
            count++;
            String text = "driver0" + count + "pw";
            System.out.println("Driver " + text);
            System.out.println("Driver Pass :" + BCrypt.hashpw(text, BCrypt.gensalt()));

        }

    }

}
