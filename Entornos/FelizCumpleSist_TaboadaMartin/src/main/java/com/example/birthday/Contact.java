package com.example.birthday;

import java.time.LocalDate;

public record Contact(String lastName, String firstName,
                      LocalDate dateOfBirth, String email) {

    public Contact {
        lastName = requireNonBlank(lastName, "El apellido");
        firstName = requireNonBlank(firstName, "El nombre");
        email = requireNonBlank(email, "El correo electrónico");

        if (dateOfBirth == null) {
            throw new IllegalArgumentException(
                    "La fecha de nacimiento es obligatoria.");
        }
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido.");
        }
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " es obligatorio.");
        }
        return value.trim();
    }
}
