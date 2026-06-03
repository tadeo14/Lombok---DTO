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

    public void setPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio no puede ser nulo ni negativo");
        this.precio = precio;
    }

    public void setStock(int stock) {
        if (stock < 0)
            throw new IllegalArgumentException("El stock no puede ser negativo");
        this.stock = stock;
    }
}
