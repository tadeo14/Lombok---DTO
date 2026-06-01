package com.utn.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, of = {"nombre", "categoria"})
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Producto extends Base {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;
    private Categoria categoria;
}
