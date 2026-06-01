package com.tp.model;

import com.tp.enums.Estado;
import com.tp.enums.FormaPago;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido extends Base {
    private Usuario usuario;
    private List<DetallePedido> detalles;
    private Estado estado;
    private FormaPago formaPago;
}
