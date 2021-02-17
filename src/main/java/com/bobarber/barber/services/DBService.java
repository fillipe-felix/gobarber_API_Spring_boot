package com.bobarber.barber.services;

import com.bobarber.barber.model.Cliente;
import com.bobarber.barber.model.enums.Perfil;
import com.bobarber.barber.model.enums.TipoCliente;
import com.bobarber.barber.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {



    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void instantiateDatabase() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Cliente cliente1 = new Cliente(null, "Maria Silva", "felipesoares_14@hotmail.com",
                TipoCliente.PESSOA_FISICA, bCryptPasswordEncoder.encode("123"));

        Cliente cliente2 = new Cliente(null, "Ana costa", "felipesoares_1993@hotmail.com",
                TipoCliente.PESSOA_FISICA, bCryptPasswordEncoder.encode("123"));
        cliente2.addPerfil(Perfil.ADMIN);


        clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));

    }
}
