package com.procesador;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ProcesadorFacturacionOptm {

    private static final double DESCUENTO_VIP = 0.15;
    private static final double UMBRAL_IMPORTE = 100.0;

    public String generarInforme(List<String> pedidos, List<String> clientesVip, double impuesto) {
        Set<String> vipSet = new HashSet<>(clientesVip);
        StringBuilder resultado = new StringBuilder();
        int contador = 0;
        double totalGlobal = 0.0;

        for (int i = 0; i < pedidos.size(); i++) {
            String[] datos = pedidos.get(i).split(";", 2);
            String idCliente = datos[0];
            double importe = Double.parseDouble(datos[1]);

            boolean esVip = vipSet.contains(idCliente);
            double importeFinal = calcularImporteFinal(importe, esVip, impuesto);
            totalGlobal += importeFinal;

            if (importeFinal > UMBRAL_IMPORTE) {
                contador++;
                appendLineaPedido(resultado, i, idCliente, importeFinal);
            }
        }

        resultado.append("RESUMEN: ")
                .append(contador)
                .append(" pedidos superiores a 100€. Total: ")
                .append(totalGlobal);

        return resultado.toString();
    }

    private double calcularImporteFinal(double importe, boolean esVip, double impuesto) {
        double conDescuento = esVip ? importe - (importe * DESCUENTO_VIP) : importe;
        return conDescuento + (conDescuento * impuesto);
    }

    private void appendLineaPedido(StringBuilder sb, int indice, String idCliente, double importeFinal) {
        sb.append("Pedido [")
                .append(indice)
                .append("] Cliente: ")
                .append(idCliente)
                .append(" -> Total: ")
                .append(importeFinal)
                .append('\n');
    }
}