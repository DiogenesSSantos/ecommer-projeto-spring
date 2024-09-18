package com.teste.ecommecer.demo.domain.repository;

import com.teste.ecommecer.demo.domain.model.Cliente;
import com.teste.ecommecer.demo.domain.repository.ClienteRepository;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeNãoLocalizadaException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeRequeridaNullExcepiton;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class ClienteRepositoryImplements {

    @Autowired
    private ClienteRepository manager;

    @Transactional
    public Cliente add(Cliente cliente) {
        return manager.save(cliente);
    }

    @Transactional
    public Optional<Cliente> buscarId(Long id)  {
        return manager.findById(id);
    }

    @Transactional
    public List<Cliente> buscarTodos() {
        return manager.findAll();

    }

    public Cliente cadastrar(Cliente cliente) {
        return manager.save(cliente);

    }

    public Cliente atualizar(Cliente clienteLocalizado) {
        return manager.save(clienteLocalizado);
    }



}
