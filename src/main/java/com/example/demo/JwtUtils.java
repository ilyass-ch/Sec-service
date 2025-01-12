package com.example.demo;

public class JwtUtils {
    public static final String SECRET = "Mysecret123";
    public static final String Auth_Header = "Authorization";
    public static final long EXPIRE_ACCESS_TOKEN = 1*60*1000;
    public static final long EXPIRE_REFRESH_TOKEN = 30*60*1000;

}
