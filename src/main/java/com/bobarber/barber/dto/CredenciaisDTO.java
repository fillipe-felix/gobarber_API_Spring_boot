package com.bobarber.barber.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CredenciaisDTO implements Serializable {

    private String email;
    private String senha;

    public CredenciaisDTO(){

    }

}
