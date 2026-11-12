# API REST - Gestión de usuarios

## 1. Descripción del endpoint



Este endpoint permite consultar la información de un usuario registrado en la aplicación mediante su identificador.

**Método:** `GET`

**URL:** `/api/usuarios/{id}`

El sistema recibe el identificador del usuario y devuelve sus datos si existe dentro de la aplicación.

## 2. Parámetros

El endpoint utiliza el siguiente parámetro: 
<div align="center">

| Parámetro | Tipo | Obligatorio | Descripción |
|---|---|---|---|
| `id` | Integer | Sí | Identificador del usuario que queremos consultar. |

</div>



Ejemplo de URL:

    GET /api/usuarios/15

## 3. Respuestas posibles

El endpoint puede devolver diferentes códigos HTTP dependiendo del resultado de la solicitud.

<div align="center">

| Código | Descripción |
|---|---|
| 200 | La solicitud se ha realizado correctamente y se devuelven los datos del usuario. |
| 400 | El identificador proporcionado no es válido. |
| 404 | No se ha encontrado ningún usuario con ese identificador. |
| 500 | Se ha producido un error interno en el servidor. |
</div>

## 4. Ejemplo de solicitud


Para consultar los datos del usuario con identificador 15, se realiza la siguiente petición:

    GET /api/usuarios/15

## 5. Ejemplo de respuesta JSON

Si el usuario existe, el servidor devuelve una respuesta con código 200 y sus datos en formato JSON.

    {
        "id": 15,
        "nombre": "Martin",
        "email": "martin@example.com"
    }

En caso de que el usuario no exista, el servidor devolvería un código 404 con un mensaje indicando que no se ha encontrado.

    {
        "error": "Usuario no encontrado"
    }

De esta manera, el endpoint permite consultar los datos de los usuarios de forma sencilla, utilizando su identificador y devolviendo una respuesta según el resultado de la operación.