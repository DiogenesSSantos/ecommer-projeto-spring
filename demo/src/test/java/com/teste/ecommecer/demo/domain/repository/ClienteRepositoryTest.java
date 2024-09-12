package com.teste.ecommecer.demo.domain.repository;

import com.teste.ecommecer.demo.domain.model.Cliente;
import com.teste.ecommecer.demo.infraestrutura.exception.DadosObrigatorioNaoPreenchidoException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeNãoLocalizadaException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeRequeridaNullExcepiton;
import com.teste.ecommecer.demo.infraestrutura.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@ActiveProfiles("test")

public class ClienteRepositoryTest {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;


    @Test
    public void happy_Path_BuscarTodos() {
        List<Cliente> clientesLocalizados = clienteRepository.findAll();
        assertNotNull(clientesLocalizados);
    }



    @Test
    public void happy_path_buscarId_Test() throws EntidadeNãoLocalizadaException {
        var cliente = clienteService.buscarID(1L);
        assertNotNull(cliente);
    }


    @Test
    @ExceptionHandler(EntidadeNãoLocalizadaException.class)
    public void buscarID_ExceptionNotFound() {
        assertThrows(EntidadeNãoLocalizadaException.class,
                () -> clienteService.buscarID(45L));
    }


    @Test
    @ExceptionHandler(EntidadeRequeridaNullExcepiton.class)
    public void buscarId_ExceptionNull () {
        assertThrows(EntidadeRequeridaNullExcepiton.class ,
                () -> clienteService.buscarID(null));
    }



    @Test
    public void happy_Path_Cadastrar() throws DadosObrigatorioNaoPreenchidoException, NoSuchFieldException {
        var cliente = new Cliente();
        cliente.setNome("Boludo");

        cliente = clienteService.cadastrar(cliente);

        assertNotNull(cliente);
        assertNotNull(cliente.getId());

    }


    @Test
    @ExceptionHandler (DadosObrigatorioNaoPreenchidoException.class)
    public void cadatrar_vazio_DadosObrigatorioNâoPreenchidoException() throws DadosObrigatorioNaoPreenchidoException, NoSuchFieldException {
        var cliente = new Cliente();
        cliente.setNome("");
        clienteService.cadastrar(cliente);
    }



}

