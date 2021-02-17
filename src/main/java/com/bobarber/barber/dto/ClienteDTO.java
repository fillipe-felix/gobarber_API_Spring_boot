package com.bobarber.barber.dto;

import com.bobarber.barber.model.Cliente;
import com.bobarber.barber.services.validation.ClienteUpdate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ClienteUpdate
public class ClienteDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    public ClienteDTO(){

    }

    public ClienteDTO(Cliente obj){
        this.id = obj.getId();
        this.nome = obj.getNome();
        this.email = obj.getEmail();
    }


}
