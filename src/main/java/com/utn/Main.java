package com.utn;

import com.utn.entities.Categoria;
import com.utn.entities.Producto;
import com.utn.repository.CategoriaRepository;
import com.utn.repository.ProductoRepository;
import com.utn.util.JPAUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final CategoriaRepository categoriaRepo = new CategoriaRepository();
    private static final ProductoRepository productoRepo = new ProductoRepository();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n╔══════════════════════════════╗");
            System.out.println("║       MENÚ PRINCIPAL         ║");
            System.out.println("╠══════════════════════════════╣");
            System.out.println("║  1. ABM Categorías           ║");
            System.out.println("║  2. ABM Productos            ║");
            System.out.println("║  3. Reportes                 ║");
            System.out.println("║  0. Salir                    ║");
            System.out.println("╚══════════════════════════════╝");
            System.out.print("Opción: ");
            switch (leerInt()) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuReportes();
                case 0 -> salir = true;
                default -> System.out.println("⚠ Opción inválida.");
            }
        }
        JPAUtil.close();
        System.out.println("Hasta luego.");
    }

    // ─────────────────────────────────────────────────────────────
    // MENÚ CATEGORÍAS
    // ─────────────────────────────────────────────────────────────
    private static void menuCategorias() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n── ABM CATEGORÍAS ──────────────");
            System.out.println("  1. Alta");
            System.out.println("  2. Baja lógica");
            System.out.println("  3. Modificación");
            System.out.println("  4. Listado");
            System.out.println("  0. Volver");
            System.out.print("Opción: ");
            switch (leerInt()) {
                case 1 -> altaCategoria();
                case 2 -> bajaCategoria();
                case 3 -> modificarCategoria();
                case 4 -> listarCategorias();
                case 0 -> volver = true;
                default -> System.out.println("⚠ Opción inválida.");
            }
        }
    }

    private static void altaCategoria() {
        System.out.print("Nombre (obligatorio): ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("✖ El nombre no puede estar vacío.");
            return;
        }
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine().trim();

        Categoria c = Categoria.builder()
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre(nombre)
                .descripcion(descripcion)
                .build();

        Categoria guardada = categoriaRepo.guardar(c);
        System.out.println("✔ Categoría creada con ID: " + guardada.getId());
    }

    private static void bajaCategoria() {
        System.out.print("ID de la categoría a dar de baja: ");
        Long id = leerLong();
        if (id == null) return;

        boolean resultado = categoriaRepo.eliminarLogico(id);
        if (resultado) {
            System.out.println("✔ Categoría con ID " + id + " dada de baja.");
        } else {
            System.out.println("✖ No existe categoría con ID " + id + ".");
        }
    }

    private static void modificarCategoria() {
        System.out.print("ID de la categoría a modificar: ");
        Long id = leerLong();
        if (id == null) return;

        Optional<Categoria> opt = categoriaRepo.buscarPorId(id);
        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("✖ No existe categoría activa con ID " + id + ".");
            return;
        }
        Categoria c = opt.get();
        System.out.println("Valores actuales → Nombre: \"" + c.getNombre() + "\" | Descripción: \"" + c.getDescripcion() + "\"");
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = sc.nextLine().trim();
        System.out.print("Nueva descripción (Enter para mantener): ");
        String descripcion = sc.nextLine().trim();

        if (!nombre.isEmpty())      c.setNombre(nombre);
        if (!descripcion.isEmpty()) c.setDescripcion(descripcion);

        categoriaRepo.guardar(c);
        System.out.println("✔ Categoría actualizada.");
    }

    private static void listarCategorias() {
        List<Categoria> lista = categoriaRepo.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías activas.");
            return;
        }
        System.out.println("\n  ID  │ Nombre                  │ Descripción");
        System.out.println("──────┼─────────────────────────┼─────────────────────────");
        for (Categoria c : lista) {
            System.out.printf("  %-4d│ %-23s │ %s%n",
                    c.getId(), c.getNombre(), c.getDescripcion());
        }
    }

    // ─────────────────────────────────────────────────────────────
    // MENÚ PRODUCTOS
    // ─────────────────────────────────────────────────────────────
    private static void menuProductos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n── ABM PRODUCTOS ───────────────");
            System.out.println("  1. Alta");
            System.out.println("  2. Baja lógica");
            System.out.println("  3. Modificación");
            System.out.println("  4. Listado");
            System.out.println("  0. Volver");
            System.out.print("Opción: ");
            switch (leerInt()) {
                case 1 -> altaProducto();
                case 2 -> bajaProducto();
                case 3 -> modificarProducto();
                case 4 -> listarProductos();
                case 0 -> volver = true;
                default -> System.out.println("⚠ Opción inválida.");
            }
        }
    }

    private static void altaProducto() {
        List<Categoria> categorias = categoriaRepo.listarActivos();
        if (categorias.isEmpty()) {
            System.out.println("✖ No hay categorías activas. Cree una primero.");
            return;
        }
        System.out.println("Categorías disponibles:");
        for (Categoria c : categorias) {
            System.out.printf("  [%d] %s%n", c.getId(), c.getNombre());
        }
        System.out.print("ID de categoría: ");
        Long catId = leerLong();
        if (catId == null) return;
        Optional<Categoria> catOpt = categoriaRepo.buscarPorId(catId);
        if (catOpt.isEmpty() || catOpt.get().isEliminado()) {
            System.out.println("✖ Categoría no válida.");
            return;
        }

        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) { System.out.println("✖ Nombre obligatorio."); return; }

        System.out.print("Descripción: ");
        String descripcion = sc.nextLine().trim();

        System.out.print("Precio: ");
        Double precio = leerDouble();
        if (precio == null || precio < 0) { System.out.println("✖ Precio inválido."); return; }

        System.out.print("Stock: ");
        Integer stock = leerIntPositivo();
        if (stock == null || stock < 0) { System.out.println("✖ Stock inválido."); return; }

        Producto p = Producto.builder()
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .stock(stock)
                .disponible(true)
                .imagen("")
                .categoria(catOpt.get())
                .build();

        Producto guardado = productoRepo.guardar(p);
        System.out.println("✔ Producto creado con ID: " + guardado.getId());
    }

    private static void bajaProducto() {
        System.out.print("ID del producto a dar de baja: ");
        Long id = leerLong();
        if (id == null) return;

        Optional<Producto> opt = productoRepo.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("✖ No existe producto con ID " + id + ".");
            return;
        }
        if (opt.get().isEliminado()) {
            System.out.println("✖ El producto \"" + opt.get().getNombre() + "\" ya está dado de baja.");
            return;
        }
        productoRepo.eliminarLogico(id);
        System.out.println("✔ Producto \"" + opt.get().getNombre() + "\" dado de baja.");
    }

    private static void modificarProducto() {
        System.out.print("ID del producto a modificar: ");
        Long id = leerLong();
        if (id == null) return;

        Optional<Producto> opt = productoRepo.buscarPorId(id);
        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("✖ No existe producto activo con ID " + id + ".");
            return;
        }
        Producto p = opt.get();
        System.out.printf("Valores actuales → Nombre: \"%s\" | Precio: %.2f | Stock: %d%n",
                p.getNombre(), p.getPrecio(), p.getStock());

        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = sc.nextLine().trim();

        System.out.print("Nuevo precio (Enter para mantener): ");
        String precioStr = sc.nextLine().trim();

        System.out.print("Nuevo stock (Enter para mantener): ");
        String stockStr = sc.nextLine().trim();

        if (!nombre.isEmpty()) p.setNombre(nombre);
        if (!precioStr.isEmpty()) {
            try {
                double precio = Double.parseDouble(precioStr);
                p.setPrecio(precio);
            } catch (NumberFormatException e) {
                System.out.println("⚠ Precio inválido, se mantiene el valor anterior.");
            }
        }
        if (!stockStr.isEmpty()) {
            try {
                p.setStock(Integer.parseInt(stockStr));
            } catch (NumberFormatException e) {
                System.out.println("⚠ Stock inválido, se mantiene el valor anterior.");
            }
        }

        productoRepo.guardar(p);
        System.out.println("✔ Producto actualizado.");
    }

    private static void listarProductos() {
        List<Producto> lista = productoRepo.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos activos.");
            return;
        }
        System.out.println("\n  ID  │ Nombre                  │    Precio │ Stock │ Categoría");
        System.out.println("──────┼─────────────────────────┼───────────┼───────┼──────────────");
        for (Producto p : lista) {
            System.out.printf("  %-4d│ %-23s │ %9.2f │ %5d │ %s%n",
                    p.getId(), p.getNombre(), p.getPrecio(), p.getStock(),
                    p.getCategoria().getNombre());
        }
    }

    // ─────────────────────────────────────────────────────────────
    // MENÚ REPORTES
    // ─────────────────────────────────────────────────────────────
    private static void menuReportes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n── REPORTES ────────────────────");
            System.out.println("  1. Productos por categoría");
            System.out.println("  0. Volver");
            System.out.print("Opción: ");
            switch (leerInt()) {
                case 1 -> productosPorCategoria();
                case 0 -> volver = true;
                default -> System.out.println("⚠ Opción inválida.");
            }
        }
    }

    private static void productosPorCategoria() {
        List<Categoria> categorias = categoriaRepo.listarActivos();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorías activas.");
            return;
        }
        System.out.println("Seleccioná una categoría:");
        for (Categoria c : categorias) {
            System.out.printf("  [%d] %s%n", c.getId(), c.getNombre());
        }
        System.out.print("ID de categoría: ");
        Long catId = leerLong();
        if (catId == null) return;

        List<Producto> productos = productoRepo.buscarPorCategoria(catId);
        if (productos.isEmpty()) {
            System.out.println("No hay productos activos en esa categoría.");
            return;
        }
        System.out.println("\n  ID  │ Nombre                  │    Precio │ Stock");
        System.out.println("──────┼─────────────────────────┼───────────┼──────");
        for (Producto p : productos) {
            System.out.printf("  %-4d│ %-23s │ %9.2f │ %d%n",
                    p.getId(), p.getNombre(), p.getPrecio(), p.getStock());
        }
    }

    // ─────────────────────────────────────────────────────────────
    // HELPERS de lectura
    // ─────────────────────────────────────────────────────────────
    private static int leerInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static Integer leerIntPositivo() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Long leerLong() {
        try {
            return Long.parseLong(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("✖ ID inválido.");
            return null;
        }
    }

    private static Double leerDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
