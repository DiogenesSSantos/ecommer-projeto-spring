package com.teste.ecommecer.demo.controller;

import com.teste.ecommecer.demo.domain.model.Cliente;
import com.teste.ecommecer.demo.domain.repository.ClienteRepository;
import com.teste.ecommecer.demo.infraestrutura.exception.DadosObrigatorioOuNaoPreenchidoCorretamanteException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeNãoLocalizadaException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeRequeridaNullExcepiton;
import com.teste.ecommecer.demo.infraestrutura.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService;
    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteController(ClienteService clienteService , ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteService = clienteService;
    }


    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteRepository.findAll());
    }


    @GetMapping
    @RequestMapping("/{id}")
    public ResponseEntity<?> buscarid(@PathVariable Long id) {
        try {
            var clienteLocalizado = clienteService.buscarID(id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(clienteLocalizado);

        } catch (EntidadeNãoLocalizadaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (EntidadeRequeridaNullExcepiton e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        }

    }


    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar (@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(clienteService.cadastrar(cliente));
        } catch (DadosObrigatorioOuNaoPreenchidoCorretamanteException | NoSuchFieldException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PatchMapping
    @RequestMapping("/atualizar")
    public ResponseEntity<?> atualizar (@Param("id") Long id , Cliente cliente){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(clienteService.atualizar(id , cliente));

        } catch (NoSuchFieldException e) {
            return ResponseEntity.badRequest().body(" ????? ");
        } catch (DadosObrigatorioOuNaoPreenchidoCorretamanteException e) {
            throw new RuntimeException(e);
        } catch (EntidadeNãoLocalizadaException e) {
            throw new RuntimeException(e);
        }

    }



}
