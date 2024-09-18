package com.teste.ecommecer.demo.infraestrutura.utils;

import com.teste.ecommecer.demo.domain.model.Cliente;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;


@UtilityClass
public class ClienteUtils {


    public static boolean validaCliente(Cliente cliente) {
        return StringUtils.hasText(cliente.getNome());
    }

    public static Boolean validaClienteComecaComEspaco (Cliente cliente) {
        return cliente.getNome().startsWith(" ");
    }



}
