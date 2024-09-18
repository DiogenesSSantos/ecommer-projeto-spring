package com.teste.ecommecer.demo.domain.repository;

import com.teste.ecommecer.demo.domain.model.Cliente;
import com.teste.ecommecer.demo.infraestrutura.exception.DadosObrigatorioOuNaoPreenchidoCorretamanteException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeNãoLocalizadaException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeRequeridaNullExcepiton;
import com.teste.ecommecer.demo.infraestrutura.service.ClienteService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

        clientesLocalizados.forEach(System.out::println);
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
    public void happy_Path_Cadastrar() throws DadosObrigatorioOuNaoPreenchidoCorretamanteException, NoSuchFieldException {
        var cliente = new Cliente();
        cliente.setNome("Boludo");

        cliente = clienteService.cadastrar(cliente);

        assertNotNull(cliente);
        assertNotNull(cliente.getId());

    }


    @Test
    @ExceptionHandler (DadosObrigatorioOuNaoPreenchidoCorretamanteException.class)
    public void cadastrar_vazio_DadosObrigatorioNâoPreenchidoException() throws DadosObrigatorioOuNaoPreenchidoCorretamanteException, NoSuchFieldException {
        AtomicReference<Cliente> cliente = new AtomicReference<>(new Cliente());
        cliente.get().setNome("       ");
        assertThrows(DadosObrigatorioOuNaoPreenchidoCorretamanteException.class
                ,() -> cliente.set(clienteService.cadastrar(cliente.get())));

        assertNull(cliente.get().getId());
    }

    @Test
    @ExceptionHandler (NullPointerException.class)
    public void cadastrar_Null_DadosObrigatorioNâoPreenchidoException() throws DadosObrigatorioOuNaoPreenchidoCorretamanteException, NoSuchFieldException {
        Cliente cliente = null;
        assertThrows(NullPointerException.class
                ,() -> clienteService.cadastrar(cliente));
    }


    @Test
    public void happy_path_atualizar() throws EntidadeNãoLocalizadaException, NoSuchFieldException, DadosObrigatorioOuNaoPreenchidoCorretamanteException {
        var cliente = new Cliente();
        cliente.setNome("Diogenes da silva Santos");

        cliente = clienteService.atualizar(1L, cliente);

        Assertions.assertNotNull(cliente.getId());

    }

    @Test
    @ExceptionHandler(DadosObrigatorioOuNaoPreenchidoCorretamanteException.class)
    public void atualizar_CadastroIncompleto_Exceptio_DadosObrigatorioNaoPreenchido () throws EntidadeNãoLocalizadaException, NoSuchFieldException, DadosObrigatorioOuNaoPreenchidoCorretamanteException {
        var cliente  = new Cliente();
        cliente.setNome("");
        cliente = clienteService.atualizar(1L,cliente);

        assertEquals(cliente , null);
        assertNotNull(cliente.getId());


    }

    @Test
    @ExceptionHandler(EntidadeNãoLocalizadaException.class)
    public void atualizar_BuscandoId_Exception_EntidadeNaoLocalizada () throws EntidadeNãoLocalizadaException {
        Assertions.assertThrows(EntidadeNãoLocalizadaException.class,
                () -> clienteService.buscarID(47L));
    }



}

