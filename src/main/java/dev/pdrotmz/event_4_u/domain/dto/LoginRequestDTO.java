package dev.pdrotmz.event_4_u.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank String email, @NotBlank String password) {
}
