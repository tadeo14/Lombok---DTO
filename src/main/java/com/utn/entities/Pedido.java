package com.utn.entities;

import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido extends Base implements Calculable {
    private Usuario usuario;
    private List<DetallePedido> detalles;
    private Estado estado;
    private FormaPago formaPago;

    @Override
    public BigDecimal calcularTotal() {
        return detalles.stream()
                .map(DetallePedido::calcularTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
