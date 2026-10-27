package com.example.birthday;

public record BirthdayMessage(
        String recipient,
        String subject,
        String body) {
}