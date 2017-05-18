package com.mosida.accountMoniter.account;

import org.apache.regexp.RE;

import java.util.Random;

/**
 * Created by mosida on 4/23/17.
 */
public class RegisterAccount {

    public String firstName;
    public String lastName;
    public String accountName;
    public String password;
    public String phone;
    public String email;
    public String code;
    public boolean male;
    public boolean isGooglePlus;
    public String region;
    public boolean available = true;
    public String reason;
    public String googlePlusFirstName;
    public String googlePlusLastName;


    public RegisterAccount(String firstName, String lastName, String password, String email, boolean male){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.accountName = firstName + lastName + createToken();
        this.male = male;
    }

    @Override
    public String toString()
    {
        return firstName + ", " + lastName + ", " + accountName + ", " + password + ", " + phone + ", " + email + ", " + male + ", " + code + ", " + isGooglePlus;
    }

    private RegisterAccount(String firstName, String lastName, String accountName, String password, String phone,
                            String email, boolean male, String code){
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountName = accountName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.code = code;
        this.male = male;
    }


    private static String createToken()
    {
        String tokenChars = "0123456789";
        String token = "";
        for (int i = 0; i < 7; i++) {
            token += tokenChars.charAt(new Random().nextInt(tokenChars.length()));
        }
        return token;
    }

    public static RegisterAccount parseFrom(String data)
    {
        if(data!=null)
        {
            return null;
        }

        String[] str = data.split(", ");

        return new RegisterAccount(str[0], str[1], str[2], str[3], str[4], str[5], Boolean.valueOf(str[6]), str[7]);
    }

}
