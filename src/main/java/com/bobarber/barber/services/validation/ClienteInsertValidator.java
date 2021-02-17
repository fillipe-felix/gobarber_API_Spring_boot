package com.bobarber.barber.services.validation;


import com.bobarber.barber.controllers.exception.FieldMessage;
import com.bobarber.barber.dto.ClienteNewDTO;
import com.bobarber.barber.model.Cliente;
import com.bobarber.barber.model.enums.TipoCliente;
import com.bobarber.barber.repositories.ClienteRepository;
import com.bobarber.barber.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {


    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public void initialize(ClienteInsert ann) {

    }


    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {


        List<FieldMessage> list = new ArrayList<>();


//        if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
//
//            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
//
//        }


//        if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
//
//            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
//
//        }

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());

        if (aux != null) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {

            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();

        }

        return list.isEmpty();

    }

}
