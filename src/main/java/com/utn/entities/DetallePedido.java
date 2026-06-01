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

    @Override
    public BigDecimal calcularTotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
