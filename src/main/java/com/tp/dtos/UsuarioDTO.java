package com.tp.dtos;

public record UsuarioDTO(
        Long id,
        String nombre,
        String apellido,
        String email,
        String celular
) {}
