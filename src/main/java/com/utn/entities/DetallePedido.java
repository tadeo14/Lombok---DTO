package com.utn.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedido extends Base implements Calculable {
    private Producto producto;
    private int cantidad;
    private BigDecimal precioUnitario;

    public void setCantidad(int cantidad) {
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        if (precioUnitario == null || precioUnitario.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El precio unitario no puede ser nulo ni negativo");
        this.precioUnitario = precioUnitario;
    }

    @Override
    public BigDecimal calcularTotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
