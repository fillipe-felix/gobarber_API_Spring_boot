package com.bobarber.barber.services;


import com.bobarber.barber.model.Cliente;
import org.springframework.mail.SimpleMailMessage;


public interface EmailService {

    void sendMail(SimpleMailMessage msg);


    void sendNewPasswordEmail(Cliente cliente, String newPass);
}
