package jp.co.nss.ojt2024.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUser {
    private String username;
    private String password;

    // Getters and Setters
}