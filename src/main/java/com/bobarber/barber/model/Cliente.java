package com.bobarber.barber.model;

import com.bobarber.barber.model.enums.Perfil;
import com.bobarber.barber.model.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nome;
    private Integer tipoCliente;

    @JsonIgnore
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis")
    private Set<Integer> perfis = new HashSet<>();

    public Cliente() {
    }

    public Cliente(Long id, String nome, String email, TipoCliente tipoCliente, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipoCliente = (tipoCliente == null) ? null : tipoCliente.getCod();
        this.senha = senha;
    }

    public Set<Perfil> getPerfil() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cliente cliente = (Cliente) o;

        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
