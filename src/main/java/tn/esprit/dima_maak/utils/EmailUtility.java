package tn.esprit.dima_maak.utils;

public class EmailUtility {
    public static String getEmailMessage(String name, String host, String token){
        return "Hello "+ name
                +"\n\nYour new account has been created. Please click the link below to verify your account. \n\n"
                + getVerificationUrl(host,token)
                +"\n\nThe Dima Maak support Team";
    }

    /*public static String getVerificationUrl(String host, String token) {
        return host + "/user?token="+token;
    }*/
    public static String getVerificationUrl(String host, String token) {
        return "http://localhost:4200" + "/verify?token="+token;
    }
}
