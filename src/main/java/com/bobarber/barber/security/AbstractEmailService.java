package com.bobarber.barber.security;


import com.bobarber.barber.model.Cliente;
import com.bobarber.barber.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;


public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass){
        SimpleMailMessage simpleMailMessage = prepareNewPasswordEmail(cliente, newPass);
        sendMail(simpleMailMessage);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(cliente.getEmail());
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setSubject("Solicitação de nova senha");
        simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMessage.setText("Nova senha: " + newPass);
        return simpleMailMessage;
    }
}
