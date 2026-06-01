package com.tp.model;

import com.tp.enums.Rol;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends Base {
    private String nombre;
    private String email;
    private String contrasena;
    private Rol rol;
}
