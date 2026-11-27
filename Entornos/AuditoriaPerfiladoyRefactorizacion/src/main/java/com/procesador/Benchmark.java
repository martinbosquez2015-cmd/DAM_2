package com.procesador;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;
import java.util.Scanner;

public class Benchmark {
    public static void main(String[] args) throws InterruptedException {
        int numClientes = 10_000;
        int numPedidos = 50_000;
        double impuesto = 0.21;
        //Scanner sc =  new Scanner(System.in);
        List<String> clientesVip = GeneradorDatosPrueba.generarClientesVip(numClientes);
        List<String> pedidos = GeneradorDatosPrueba.generarPedidos(numPedidos, numClientes);

        // Cambia entre ProcesadorFacturacionLegacy y ProcesadorFacturacionOptimizado
        ProcesadorFacturacion procesador = new ProcesadorFacturacion();

        // Pequeño calentamiento opcional o medición directa (anota si mides en frío o en caliente)
        System.gc();
        long inicioNs = System.nanoTime();

        String informe = procesador.generarInforme(pedidos, clientesVip, impuesto);

        long finNs = System.nanoTime();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();

        double tiempoMs = (finNs - inicioNs) / 1_000_000.0;
        double heapUsadoMb = heapUsage.getUsed() / (1024.0 * 1024.0);

        System.out.printf("Tiempo de ejecución: %.2f ms%n", tiempoMs);
        System.out.printf("Heap actual usado: %.2f MB%n", heapUsadoMb);
        System.out.println("Longitud del informe: " + informe.length() + " caracteres");
        //String pausa = sc.nextLine();
    }
}
