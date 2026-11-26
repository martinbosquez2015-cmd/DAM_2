# Informe Técnico: Optimización de `ProcesadorFacturacion`

## 1. Resumen ejecutivo
Se diagnosticó y optimizó `ProcesadorFacturacion` sobre un dataset de 50.000 pedidos y 10.000 clientes, manteniendo la salida textual 100 % idéntica (`equals == true`).

## 2. Hallazgos del análisis estático
- [Captura del panel de inspecciones / SonarLint / Problems]
- Problemas detectados: variable sin usar, concatenación de `String` en bucle, condición booleana redundante (`esVip == true`), números mágicos (`0.15`, `100`) y búsqueda lineal anidada.

## 3. Diagnóstico con Profiler (Antes vs. Después)
- **CPU (Antes):** [Indica método/línea con mayor % CPU, p. ej. búsqueda en lista / bucle anidado].
- **Memoria (Antes):** [Indica punto de mayor asignación, p. ej. concatenación `resultado += ...`].
- **Después:** [Describe cómo cae el tiempo y la asignación de objetos tras usar `HashSet` y `StringBuilder`].
- [Insertar capturas del Flame Graph / Call Tree antes y después].

## 4. Refactorizaciones aplicadas
- Extracción de constantes (`DESCUENTO_VIP`, `UMBRAL_IMPORTE`) y eliminación de código muerto.
- Reemplazo de búsqueda lineal por `HashSet` ($O(1)$ promedio) y `String` por `StringBuilder`.
- Extracción de métodos auxiliares (`calcularImporteFinal`, `appendLineaPedido`).

## 5. Tabla comparativa de métricas

| Métrica Evaluada | Versión Inicial | Versión Optimizada | Mejora (%) | Herramienta del IDE utilizada |
| :--- | :--- | :--- | :--- | :--- |
| Tiempo de ejecución (ms) | | | | |
| Memoria máxima asignada (MB) | | | | |
| Nº de *Warnings / Code Smells* | | | | |
| Complejidad ciclomática del método principal | | | | |

## 6. Verificación funcional
Se comprobó con aserción/comparación directa que la cadena devuelta por la versión optimizada es idéntica a la de la versión original.