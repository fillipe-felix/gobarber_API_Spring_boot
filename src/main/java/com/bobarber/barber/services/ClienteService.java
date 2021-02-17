package com.bobarber.barber.services;


import com.bobarber.barber.dto.ClienteDTO;
import com.bobarber.barber.dto.ClienteNewDTO;
import com.bobarber.barber.model.Cliente;
import com.bobarber.barber.model.enums.Perfil;
import com.bobarber.barber.model.enums.TipoCliente;
import com.bobarber.barber.repositories.ClienteRepository;
import com.bobarber.barber.security.UserSpringSecurity;
import com.bobarber.barber.services.exceptions.AuthorizationException;
import com.bobarber.barber.services.exceptions.DataIntegrityException;
import com.bobarber.barber.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public Cliente findById(Integer id) throws ObjectNotFoundException, AuthorizationException {

        UserSpringSecurity user = UserService.authenticated();


        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
                ", Tipo: " + Cliente.class.getName()));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente finByEmail(String email) throws AuthorizationException, ObjectNotFoundException {
        UserSpringSecurity user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = clienteRepository.findByEmail(email);
        if(obj == null){
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + user.getId() + " , Tipo: " + Cliente.class.getName());
        }
        return obj;
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = clienteRepository.save(obj);
        return obj;
    }

    public Cliente update(Cliente obj) throws ObjectNotFoundException, AuthorizationException {
        Cliente newCliente = findById(obj.getId());
        updateData(newCliente, obj);
        return clienteRepository.save(newCliente);
    }

    private void updateData(Cliente newCliente, Cliente obj) {
        newCliente.setNome(obj.getNome());
        newCliente.setEmail(obj.getEmail());
    }

    public void delete(Integer id) throws ObjectNotFoundException, DataIntegrityException, AuthorizationException {
        findById(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir um cliente que possui pedidos");
        }
    }

    //Faz a paginação no banco de dados com os parametros opcionais
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDTO) {
        Cliente cliente = new Cliente(null, objDTO.getNome(), objDTO.getEmail(),
                TipoCliente.toEnum(objDTO.getTipoCliente()), bCryptPasswordEncoder.encode(objDTO.getSenha()));
        return cliente;
    }

}
