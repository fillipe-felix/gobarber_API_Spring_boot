package com.bobarber.barber.controllers.exception;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldMessage implements Serializable {

    private String fieldName;
    private String message;

    public FieldMessage(){

    }

    public FieldMessage(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
