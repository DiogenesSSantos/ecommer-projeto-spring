package com.teste.ecommecer.demo.infraestrutura.service;


import com.teste.ecommecer.demo.domain.model.Cliente;
import com.teste.ecommecer.demo.domain.repository.ClienteRepositoryImplements;
import com.teste.ecommecer.demo.infraestrutura.exception.DadosObrigatorioOuNaoPreenchidoCorretamanteException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeNãoLocalizadaException;
import com.teste.ecommecer.demo.infraestrutura.exception.EntidadeRequeridaNullExcepiton;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.teste.ecommecer.demo.infraestrutura.utils.ClienteUtils.validaCliente;
import static com.teste.ecommecer.demo.infraestrutura.utils.ClienteUtils.validaClienteComecaComEspaco;


@Service
public class ClienteService {

    private ClienteRepositoryImplements clienteRepositoryImplements;


    @Autowired
    public ClienteService(ClienteRepositoryImplements clienteRepositoryImplements) {
        this.clienteRepositoryImplements = clienteRepositoryImplements;

    }


    /**
     * @param id chave-primaria do cliente para ser localizado no BD
     * @throws EntidadeRequeridaNullExcepiton (O cliente do recurso passou uma ID "NULL" é lançada a Exception)
     * @throws EntidadeNãoLocalizadaException (O Id passada não está nula mas não possui uma Entidade persistida
     *                                        essa chave-primaria)
     **/
    public Cliente buscarID(Long id) throws EntidadeNãoLocalizadaException, EntidadeRequeridaNullExcepiton {
        if (id == null) {
            throw new EntidadeRequeridaNullExcepiton(String.format("ENTIDADE NÃO ENCONTRADA O ID : ESTÁ NULO %d", id));
        }

        var optionalCliente = clienteRepositoryImplements.buscarId(id);

        return optionalCliente.orElseThrow(() -> new EntidadeNãoLocalizadaException(
                String.format("A ENTIDADE DE ID : %d NÃO FOI LOCALIZADO NO BANCO DE DADOS ", id)));

    }


    /**
     * @param cliente recebe uma entidade de cadastro de cliente
     * @throws DadosObrigatorioOuNaoPreenchidoCorretamanteException caso a entidade passada vem Null ou com os Parametros "" e lançada a exception.
     **/
    public Cliente cadastrar(Cliente cliente) throws DadosObrigatorioOuNaoPreenchidoCorretamanteException, NoSuchFieldException {
        if (cliente == null) {
            throw new NullPointerException("NULL PASSADO NA ENTIDADE");
        }

        if (!validaCliente(cliente)) {
            throw new DadosObrigatorioOuNaoPreenchidoCorretamanteException(String.format(
                    "ERRO NOS DADOS  PASSADO// (%s): DADOS PASSADO-> {'%s'} ",
                    cliente.getClass().getDeclaredField("nome").getName(), cliente.getNome()));
        }

        if(validaClienteComecaComEspaco(cliente)){
            throw new DadosObrigatorioOuNaoPreenchidoCorretamanteException(
                    "O CAMPO NÃO PODE COMEÇAR COM ESPAÇO");
        }


        return clienteRepositoryImplements.cadastrar(cliente);
    }


    public Cliente atualizar(Long id, Cliente cliente) throws EntidadeNãoLocalizadaException, NoSuchFieldException, DadosObrigatorioOuNaoPreenchidoCorretamanteException {
        var clienteLocalizado = buscarID(id);

        if (cliente == null) {
            throw new NullPointerException("ENTIDADE PASSADA ESTÁ VAZIA");
        }
        if (!validaCliente(cliente)) {
            throw new DadosObrigatorioOuNaoPreenchidoCorretamanteException(
                    String.format("DADOS OBRIGATORIO NÃO PREENCHIDO CORRETAMENTE CAMPO -> (%s)",
                            cliente.getClass().getDeclaredField("nome")));
        }

        if(validaClienteComecaComEspaco(cliente)){
            throw new DadosObrigatorioOuNaoPreenchidoCorretamanteException(
                    "O CAMPO NÃO PODE COMEÇAR COM ESPAÇO");
        }

        BeanUtils.copyProperties(clienteLocalizado ,cliente , "nome");
        cliente = clienteRepositoryImplements.atualizar(cliente);

        return cliente;
    }


}
