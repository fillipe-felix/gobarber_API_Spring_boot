package com.bobarber.barber.dto;

import com.bobarber.barber.services.validation.ClienteInsert;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ClienteInsert
public class ClienteNewDTO implements Serializable {

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;
    private Integer tipoCliente;

    @NotEmpty(message = "Preenchimento obrigatório")
    private String senha;

    public ClienteNewDTO(){

    }

}
