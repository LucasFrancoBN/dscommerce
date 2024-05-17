package com.devsuperior.dscommerce.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record UserDTO(
    Long id,
    String name,
    String email,
    String phone,
    LocalDate birthday,
    List<String> roles
) {
}
