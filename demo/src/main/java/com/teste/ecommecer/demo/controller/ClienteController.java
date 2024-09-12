package com.teste.ecommecer.demo.controller;

import com.teste.ecommecer.demo.domain.model.Cliente;
import com.teste.ecommecer.demo.domain.repository.ClienteRepository;
import com.teste.ecommecer.demo.infraestrutura.exception.DadosObrigatorioNaoPreenchidoException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeNãoLocalizadaException;
import com.teste.ecommecer.demo.domain.repository.ClienteRepositoryImplements;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeRequeridaNullExcepiton;
import com.teste.ecommecer.demo.infraestrutura.service.ClienteService;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteRepository.findAll());
    }


    @GetMapping
    @RequestMapping("/{id}")
    @ResponseBody
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
        } catch (DadosObrigatorioNaoPreenchidoException | NoSuchFieldException e) {
            return ResponseEntity.badRequest().body("CLIENTE PASSADO NÃO PODE SER " +
                    "CADASTRADO ALGUMA CAMPO ESTÁ INCORRETO");
        }
    }


}
