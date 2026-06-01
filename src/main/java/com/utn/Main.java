package com.utn;

import com.utn.dtos.UsuarioDTO;
import com.utn.entities.*;
import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import com.utn.enums.Rol;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // --- Categorías ---
        Categoria electronica = Categoria.builder()
                .id(1L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Electrónica").descripcion("Dispositivos electrónicos")
                .build();

        Categoria ropa = Categoria.builder()
                .id(2L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Ropa").descripcion("Indumentaria y accesorios")
                .build();

        Categoria hogar = Categoria.builder()
                .id(3L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Hogar").descripcion("Artículos para el hogar")
                .build();

        // --- Productos ---
        Producto p1 = Producto.builder()
                .id(1L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Notebook").descripcion("Laptop 15 pulgadas").precio(new BigDecimal("150000")).stock(10).categoria(electronica)
                .build();

        Producto p2 = Producto.builder()
                .id(2L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Mouse").descripcion("Mouse inalámbrico").precio(new BigDecimal("5000")).stock(50).categoria(electronica)
                .build();

        Producto p3 = Producto.builder()
                .id(3L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Teclado").descripcion("Teclado mecánico").precio(new BigDecimal("12000")).stock(30).categoria(electronica)
                .build();

        Producto p4 = Producto.builder()
                .id(4L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Monitor").descripcion("Monitor 24 pulgadas Full HD").precio(new BigDecimal("80000")).stock(15).categoria(electronica)
                .build();

        Producto p5 = Producto.builder()
                .id(5L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Remera").descripcion("Remera de algodón").precio(new BigDecimal("3500")).stock(100).categoria(ropa)
                .build();

        Producto p6 = Producto.builder()
                .id(6L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Pantalón").descripcion("Jean clásico").precio(new BigDecimal("8000")).stock(60).categoria(ropa)
                .build();

        Producto p7 = Producto.builder()
                .id(7L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Zapatillas").descripcion("Zapatillas deportivas").precio(new BigDecimal("25000")).stock(40).categoria(ropa)
                .build();

        Producto p8 = Producto.builder()
                .id(8L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Silla").descripcion("Silla ergonómica de oficina").precio(new BigDecimal("45000")).stock(20).categoria(hogar)
                .build();

        Producto p9 = Producto.builder()
                .id(9L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Lámpara").descripcion("Lámpara LED de escritorio").precio(new BigDecimal("7000")).stock(35).categoria(hogar)
                .build();

        Producto p10 = Producto.builder()
                .id(10L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Escritorio").descripcion("Escritorio de madera 120cm").precio(new BigDecimal("60000")).stock(8).categoria(hogar)
                .build();

        List<Producto> productos = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);

        // --- Usuarios ---
        Usuario usuario1 = Usuario.builder()
                .id(1L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Ana").apellido("García").email("ana@mail.com").celular("1122334455").contrasena("pass123").rol(Rol.USUARIO)
                .build();

        Usuario usuario2 = Usuario.builder()
                .id(2L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Carlos").apellido("López").email("carlos@mail.com").celular("1199887766").contrasena("pass456").rol(Rol.ADMIN)
                .build();

        // --- Pedidos ---
        Pedido pedido1 = Pedido.builder()
                .id(1L).eliminado(false).createdAt(LocalDateTime.now())
                .usuario(usuario1).estado(Estado.PENDIENTE).formaPago(FormaPago.TARJETA_CREDITO)
                .detalles(List.of(
                        DetallePedido.builder().id(1L).eliminado(false).createdAt(LocalDateTime.now())
                                .producto(p1).cantidad(1).precioUnitario(p1.getPrecio()).build(),
                        DetallePedido.builder().id(2L).eliminado(false).createdAt(LocalDateTime.now())
                                .producto(p2).cantidad(2).precioUnitario(p2.getPrecio()).build()
                ))
                .build();

        Pedido pedido2 = Pedido.builder()
                .id(2L).eliminado(false).createdAt(LocalDateTime.now())
                .usuario(usuario1).estado(Estado.CONFIRMADO).formaPago(FormaPago.TRANSFERENCIA)
                .detalles(List.of(
                        DetallePedido.builder().id(3L).eliminado(false).createdAt(LocalDateTime.now())
                                .producto(p5).cantidad(3).precioUnitario(p5.getPrecio()).build(),
                        DetallePedido.builder().id(4L).eliminado(false).createdAt(LocalDateTime.now())
                                .producto(p6).cantidad(1).precioUnitario(p6.getPrecio()).build(),
                        DetallePedido.builder().id(5L).eliminado(false).createdAt(LocalDateTime.now())
                                .producto(p7).cantidad(1).precioUnitario(p7.getPrecio()).build()
                ))
                .build();

        Pedido pedido3 = Pedido.builder()
                .id(3L).eliminado(false).createdAt(LocalDateTime.now())
                .usuario(usuario2).estado(Estado.TERMINADO).formaPago(FormaPago.EFECTIVO)
                .detalles(List.of(
                        DetallePedido.builder().id(6L).eliminado(false).createdAt(LocalDateTime.now())
                                .producto(p8).cantidad(1).precioUnitario(p8.getPrecio()).build(),
                        DetallePedido.builder().id(7L).eliminado(false).createdAt(LocalDateTime.now())
                                .producto(p9).cantidad(2).precioUnitario(p9.getPrecio()).build()
                ))
                .build();

        List<Pedido> pedidos = List.of(pedido1, pedido2, pedido3);

        // ================================================================
        // 1. Producto individual
        // ================================================================
        System.out.println("=== Producto individual ===");
        System.out.println(p1);

        // ================================================================
        // 2. Listado completo de productos
        // ================================================================
        System.out.println("\n=== Listado de productos ===");
        productos.forEach(System.out::println);

        // ================================================================
        // 3. Pedidos del usuario con mayor cantidad de pedidos
        // ================================================================
        System.out.println("\n=== Pedidos del usuario con más pedidos ===");
        Usuario usuarioConMasPedidos = List.of(usuario1, usuario2).stream()
                .max((u1, u2) -> Long.compare(
                        pedidos.stream().filter(p -> p.getUsuario().equals(u1)).count(),
                        pedidos.stream().filter(p -> p.getUsuario().equals(u2)).count()
                ))
                .orElseThrow();

        System.out.println("Usuario: " + usuarioConMasPedidos.getNombre() + " " + usuarioConMasPedidos.getApellido());
        pedidos.stream()
                .filter(p -> p.getUsuario().equals(usuarioConMasPedidos))
                .forEach(p -> System.out.println(p + "\n  Total: $" + p.calcularTotal()));

        // ================================================================
        // 4. Comparación con equals
        // ================================================================
        System.out.println("\n=== Comparación con equals ===");
        Producto productoComparar = Producto.builder()
                .id(99L).eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Notebook").descripcion("Otra descripción").precio(new BigDecimal("99999")).stock(1).categoria(electronica)
                .build();

        System.out.println("Producto a comparar: " + productoComparar);
        productos.forEach(p -> System.out.println(
                "¿" + productoComparar.getNombre() + " equals " + p.getNombre() + "? " + productoComparar.equals(p)
        ));

        // ================================================================
        // 5. UsuarioDTO (record)
        // ================================================================
        System.out.println("\n=== UsuarioDTO ===");
        UsuarioDTO dto1 = new UsuarioDTO(usuario1.getId(), usuario1.getNombre(), usuario1.getApellido(), usuario1.getEmail(), usuario1.getCelular());
        UsuarioDTO dto2 = new UsuarioDTO(usuario2.getId(), usuario2.getNombre(), usuario2.getApellido(), usuario2.getEmail(), usuario2.getCelular());
        System.out.println(dto1);
        System.out.println(dto2);
    }
}
