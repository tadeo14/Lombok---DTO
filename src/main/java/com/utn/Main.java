package com.utn;

import com.utn.dtos.UsuarioDTO;
import com.utn.entities.*;
import com.utn.enums.Estado;
import com.utn.enums.FormaPago;
import com.utn.enums.Rol;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = emf.createEntityManager();

        // Limpiar datos previos para ejecuciones repetidas
        em.getTransaction().begin();
        em.createQuery("DELETE FROM DetallePedido").executeUpdate();
        em.createQuery("DELETE FROM Pedido").executeUpdate();
        em.createQuery("DELETE FROM Producto").executeUpdate();
        em.createQuery("DELETE FROM Categoria").executeUpdate();
        em.createQuery("DELETE FROM Usuario").executeUpdate();
        em.getTransaction().commit();

        // ================================================================
        // PERSISTIR
        // ================================================================
        em.getTransaction().begin();

        // --- Categorías ---
        Categoria electronica = Categoria.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Electrónica").descripcion("Dispositivos electrónicos")
                .build();
        Categoria ropa = Categoria.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Ropa").descripcion("Indumentaria y accesorios")
                .build();
        Categoria hogar = Categoria.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Hogar").descripcion("Artículos para el hogar")
                .build();

        em.persist(electronica);
        em.persist(ropa);
        em.persist(hogar);

        // --- Productos ---
        Producto p1 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Notebook").descripcion("Laptop 15 pulgadas").precio(150000.0).stock(10)
                .imagen("notebook.jpg").disponible(true).categoria(electronica).build();
        Producto p2 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Mouse").descripcion("Mouse inalámbrico").precio(5000.0).stock(50)
                .imagen("mouse.jpg").disponible(true).categoria(electronica).build();
        Producto p3 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Teclado").descripcion("Teclado mecánico").precio(12000.0).stock(30)
                .imagen("teclado.jpg").disponible(true).categoria(electronica).build();
        Producto p4 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Monitor").descripcion("Monitor 24 pulgadas Full HD").precio(80000.0).stock(15)
                .imagen("monitor.jpg").disponible(true).categoria(electronica).build();
        Producto p5 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Remera").descripcion("Remera de algodón").precio(3500.0).stock(100)
                .imagen("remera.jpg").disponible(true).categoria(ropa).build();
        Producto p6 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Pantalón").descripcion("Jean clásico").precio(8000.0).stock(60)
                .imagen("pantalon.jpg").disponible(true).categoria(ropa).build();
        Producto p7 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Zapatillas").descripcion("Zapatillas deportivas").precio(25000.0).stock(40)
                .imagen("zapatillas.jpg").disponible(true).categoria(ropa).build();
        Producto p8 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Silla").descripcion("Silla ergonómica de oficina").precio(45000.0).stock(20)
                .imagen("silla.jpg").disponible(true).categoria(hogar).build();
        Producto p9 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Lámpara").descripcion("Lámpara LED de escritorio").precio(7000.0).stock(35)
                .imagen("lampara.jpg").disponible(true).categoria(hogar).build();
        Producto p10 = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Escritorio").descripcion("Escritorio de madera 120cm").precio(60000.0).stock(8)
                .imagen("escritorio.jpg").disponible(true).categoria(hogar).build();

        em.persist(p1); em.persist(p2); em.persist(p3); em.persist(p4); em.persist(p5);
        em.persist(p6); em.persist(p7); em.persist(p8); em.persist(p9); em.persist(p10);

        // --- Usuarios ---
        Usuario usuario1 = Usuario.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Ana").apellido("García").email("ana@mail.com")
                .celular("1122334455").contrasena("pass123").rol(Rol.USUARIO).build();
        Usuario usuario2 = Usuario.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Carlos").apellido("López").email("carlos@mail.com")
                .celular("1199887766").contrasena("pass456").rol(Rol.ADMIN).build();

        em.persist(usuario1);
        em.persist(usuario2);

        // --- Pedidos ---
        Pedido pedido1 = Pedido.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .usuario(usuario1).estado(Estado.PENDIENTE).formaPago(FormaPago.TARJETA)
                .fecha(LocalDate.now()).detalles(new HashSet<>()).build();
        pedido1.addDetallePedido(1, p1);
        pedido1.addDetallePedido(2, p2);
        pedido1.calcularTotal();

        Pedido pedido2 = Pedido.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .usuario(usuario1).estado(Estado.CONFIRMADO).formaPago(FormaPago.TRANSFERENCIA)
                .fecha(LocalDate.now()).detalles(new HashSet<>()).build();
        pedido2.addDetallePedido(3, p5);
        pedido2.addDetallePedido(1, p6);
        pedido2.addDetallePedido(1, p7);
        pedido2.calcularTotal();

        Pedido pedido3 = Pedido.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .usuario(usuario2).estado(Estado.TERMINADO).formaPago(FormaPago.EFECTIVO)
                .fecha(LocalDate.now()).detalles(new HashSet<>()).build();
        pedido3.addDetallePedido(1, p8);
        pedido3.addDetallePedido(2, p9);
        pedido3.calcularTotal();

        em.persist(pedido1);
        em.persist(pedido2);
        em.persist(pedido3);

        em.getTransaction().commit();
        System.out.println("\n✔ Datos persistidos correctamente.\n");

        // ================================================================
        // ACTUALIZAR 2 productos (punto 5)
        // ================================================================
        em.getTransaction().begin();

        p1.setPrecio(145000.0);
        p1.setStock(8);
        em.merge(p1);

        p5.setPrecio(3800.0);
        p5.setDisponible(false);
        em.merge(p5);

        em.getTransaction().commit();
        System.out.println("✔ Productos actualizados:");
        System.out.println("  Notebook → precio: " + p1.getPrecio() + ", stock: " + p1.getStock());
        System.out.println("  Remera   → precio: " + p5.getPrecio() + ", disponible: " + p5.getDisponible());

        // ================================================================
        // BUSCAR usuario por id (punto 6)
        // ================================================================
        Long idBuscado = usuario1.getId();
        Usuario encontradoPorId = em.find(Usuario.class, idBuscado);
        System.out.println("\n✔ Buscar Usuario por id=" + idBuscado + ":");
        System.out.println("  " + encontradoPorId.getNombre() + " " + encontradoPorId.getApellido());

        // ================================================================
        // BUSCAR usuario por mail (punto 7)
        // ================================================================
        TypedQuery<Usuario> queryMail = em.createQuery(
                "SELECT u FROM Usuario u WHERE u.email = :mail", Usuario.class);
        queryMail.setParameter("mail", "carlos@mail.com");
        Usuario encontradoPorMail = queryMail.getSingleResult();
        System.out.println("\n✔ Buscar Usuario por mail 'carlos@mail.com':");
        System.out.println("  " + encontradoPorMail.getNombre() + " " + encontradoPorMail.getApellido()
                + " | rol: " + encontradoPorMail.getRol());

        // ================================================================
        // BORRAR 1 producto (punto 8)
        // ================================================================
        em.getTransaction().begin();
        Producto aEliminar = em.find(Producto.class, p10.getId());
        em.remove(aEliminar);
        em.getTransaction().commit();
        System.out.println("\n✔ Producto eliminado: " + p10.getNombre() + " (id=" + p10.getId() + ")");

        // ================================================================
        // SALIDA POR CONSOLA (requisitos Lombok TP anterior)
        // ================================================================
        List<Producto> productos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();

        System.out.println("\n=== Producto individual ===");
        System.out.println(em.find(Producto.class, p1.getId()));

        System.out.println("\n=== Listado completo de productos ===");
        productos.forEach(System.out::println);

        System.out.println("\n=== Pedidos del usuario con más pedidos ===");
        List<Pedido> pedidos = em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
        Map<Usuario, List<Pedido>> pedidosPorUsuario = pedidos.stream()
                .collect(Collectors.groupingBy(Pedido::getUsuario));
        pedidosPorUsuario.entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()))
                .ifPresent(entry -> {
                    Usuario u = entry.getKey();
                    System.out.println("Usuario: " + u.getNombre() + " " + u.getApellido()
                            + " (" + entry.getValue().size() + " pedidos)");
                    entry.getValue().forEach(p ->
                            System.out.println("  Pedido id=" + p.getId() + " | Total: $" + p.getTotal()));
                });

        System.out.println("\n=== Comparación equals ===");
        Producto duplicado = Producto.builder()
                .eliminado(false).createdAt(LocalDateTime.now())
                .nombre("Notebook").descripcion("Otra desc").precio(1.0).stock(1)
                .imagen("x.jpg").disponible(true).categoria(electronica).build();
        productos.forEach(p ->
                System.out.println("¿Notebook equals " + p.getNombre() + "? " + duplicado.equals(p)));

        System.out.println("\n=== UsuarioDTO ===");
        UsuarioDTO dto1 = new UsuarioDTO(usuario1.getId(), usuario1.getNombre(), usuario1.getApellido(), usuario1.getEmail(), usuario1.getCelular());
        UsuarioDTO dto2 = new UsuarioDTO(usuario2.getId(), usuario2.getNombre(), usuario2.getApellido(), usuario2.getEmail(), usuario2.getCelular());
        System.out.println(dto1);
        System.out.println(dto2);

        em.close();
        emf.close();
    }
}
