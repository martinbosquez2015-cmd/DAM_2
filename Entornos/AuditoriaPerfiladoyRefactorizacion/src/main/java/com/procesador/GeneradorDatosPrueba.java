package com.procesador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneradorDatosPrueba {

    public static List<String> generarClientesVip(int cantidad) throws InterruptedException {
        List<String> clientes = new ArrayList<>(cantidad);
        for (int i = 0; i < cantidad; i++) {
            // Por ejemplo, 1 de cada 5 clientes es VIP
            if (i % 5 == 0) {
                clientes.add("CLI-" + i);
            }
            Thread.sleep(5);
        }
        return clientes;
    }

    public static List<String> generarPedidos(int cantidadPedidos, int totalClientes) {
        List<String> pedidos = new ArrayList<>(cantidadPedidos);
        Random random = new Random(42L);
        for (int i = 0; i < cantidadPedidos; i++) {
            int clienteId = random.nextInt(totalClientes);
            double importe = Math.round((10.0 + random.nextDouble() * 490.0) * 100.0) / 100.0;
            pedidos.add("CLI-" + clienteId + ";" + importe);
        }
        return pedidos;
    }
}