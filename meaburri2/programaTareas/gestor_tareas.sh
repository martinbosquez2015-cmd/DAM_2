#!/bin/bash

# Nombre del archivo que servirá como base de datos
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DB_FILE="$DIR/tareas.db"

# Crear el archivo de base de datos si no existe
if [ ! -f "$DB_FILE" ]; then
    touch "$DB_FILE"
fi

# Función para mostrar el menú principal
mostrar_menu() {
    echo "============================="
    echo "      GESTOR DE TAREAS       "
    echo "============================="
    echo "1. Ver tareas (Ordenadas por Estado y Fecha)"
    echo "2. Añadir nueva tarea"
    echo "3. Cambiar estado de una tarea"
    echo "4. Eliminar tarea"
    echo "5. Salir"
    echo "============================="
}

# Función para mostrar la lista de tareas
ver_tareas() {
    if [ ! -s "$DB_FILE" ]; then
        echo -e "\n[!] No hay tareas registradas.\n"
        return
    fi
    
    echo -e "\nID   | DESCRIPCIÓN                    | ESTADO       | FECHA LÍMITE"
    echo "-----------------------------------------------------------------------"
    # Ordena primero por estado (columna 3) y luego por fecha (columna 4)
    # y usa awk para darle un formato de tabla ordenado.
    sort -t'|' -k3,3 -k4,4 "$DB_FILE" | awk -F'|' '{printf "%-4s | %-30s | %-12s | %-10s\n", $1, $2, $3, $4}'
    echo ""
}

# Función para añadir una tarea
anadir_tarea() {
    echo -e "\n--- AÑADIR TAREA ---"
    read -p "Descripción de la tarea: " descripcion
    read -p "Fecha de entrega (Formato AAAA-MM-DD): " fecha

    # Generar un ID automático (busca el mayor ID actual y suma 1)
    if [ ! -s "$DB_FILE" ]; then
        next_id=1
    else
        next_id=$(awk -F'|' '{print $1}' "$DB_FILE" | sort -n | tail -1)
        next_id=$((next_id + 1))
    fi

    # Por defecto, una tarea nueva entra como PENDIENTE
    echo "$next_id|$descripcion|PENDIENTE|$fecha" >> "$DB_FILE"
    echo "[+] Tarea guardada con éxito."
    echo ""
}

# Función para cambiar el estado de una tarea
cambiar_estado() {
    echo -e "\n--- CAMBIAR ESTADO ---"
    ver_tareas
    if [ ! -s "$DB_FILE" ]; then return; fi

    read -p "Introduce el ID de la tarea a modificar: " id_tarea

    # Comprobar si el ID existe en la base de datos
    if ! grep -q "^$id_tarea|" "$DB_FILE"; then
        echo "[!] Error: No se encontró ninguna tarea con el ID $id_tarea."
        return
    fi

    echo "Selecciona el nuevo estado:"
    echo "1) PENDIENTE"
    echo "2) EN CURSO"
    echo "3) ACABADA"
    read -p "Opción (1-3): " op_estado

    case $op_estado in
        1) nuevo_estado="PENDIENTE" ;;
        2) nuevo_estado="EN CURSO" ;;
        3) nuevo_estado="ACABADA" ;;
        *) echo "[!] Opción no válida."; return ;;
    esac

    # Usamos awk para buscar la línea con el ID y reemplazar solo el estado (columna 3)
    awk -F'|' -v id="$id_tarea" -v est="$nuevo_estado" 'BEGIN {OFS="|"} {if($1==id) $3=est; print $0}' "$DB_FILE" > tmp.db
    mv tmp.db "$DB_FILE"
    
    echo "[+] Estado de la tarea $id_tarea actualizado a $nuevo_estado."
    echo ""
}

# Función para eliminar una tarea
eliminar_tarea() {
    echo -e "\n--- ELIMINAR TAREA ---"
    ver_tareas
    if [ ! -s "$DB_FILE" ]; then return; fi

    read -p "Introduce el ID de la tarea a eliminar: " id_tarea

    if ! grep -q "^$id_tarea|" "$DB_FILE"; then
        echo "[!] Error: No se encontró ninguna tarea con el ID $id_tarea."
        return
    fi

    # Usamos grep invertido (-v) para guardar todas las líneas excepto la que queremos borrar
    grep -v "^$id_tarea|" "$DB_FILE" > tmp.db
    mv tmp.db "$DB_FILE"
    
    echo "[-] Tarea $id_tarea eliminada de la base de datos."
    echo ""
}

# Bucle principal (ejecución del programa)
while true; do
    mostrar_menu
    read -p "Elige una opción (1-5): " opcion
    case $opcion in
        1) ver_tareas ;;
        2) anadir_tarea ;;
        3) cambiar_estado ;;
        4) eliminar_tarea ;;
        5) echo "¡Hasta pronto!"; exit 0 ;;
        *) echo -e "\n[!] Opción no válida, por favor elige del 1 al 5.\n" ;;
    esac
done
