package com.example.smile_ukraine;

public class ReadWriteUserDetails {
    public String email, password, phone_number;
    public ReadWriteUserDetails(String textEmail, String textPassword, String textPhoneNumber) {
        this.email = textEmail;
        this.password = textPassword;
        this.phone_number = textPhoneNumber;
    }
}
