package dev.pdrotmz.event_4_u.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(@NotBlank String username, @NotBlank String email, @NotBlank String password) {
}
