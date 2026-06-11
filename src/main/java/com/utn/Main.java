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
            System.out.println("\n==============================");
            System.out.println("       MENU PRINCIPAL         ");
            System.out.println("==============================");
            System.out.println("  1. ABM Categorias");
            System.out.println("  2. ABM Productos");
            System.out.println("  3. Reportes");
            System.out.println("  0. Salir");
            System.out.println("------------------------------");
            System.out.print("Opcion: ");
            switch (leerInt()) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuReportes();
                case 0 -> salir = true;
                default -> System.out.println("[!] Opcion invalida.");
            }
        }
        JPAUtil.close();
        System.out.println("Hasta luego.");
    }

    // ─────────────────────────────────────────────────────────────
    // MENU CATEGORIAS
    // ─────────────────────────────────────────────────────────────
    private static void menuCategorias() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- ABM CATEGORIAS ---");
            System.out.println("  1. Alta");
            System.out.println("  2. Baja logica");
            System.out.println("  3. Modificacion");
            System.out.println("  4. Listado");
            System.out.println("  0. Volver");
            System.out.print("Opcion: ");
            switch (leerInt()) {
                case 1 -> altaCategoria();
                case 2 -> bajaCategoria();
                case 3 -> modificarCategoria();
                case 4 -> listarCategorias();
                case 0 -> volver = true;
                default -> System.out.println("[!] Opcion invalida.");
            }
        }
    }

    private static void altaCategoria() {
        System.out.print("Nombre (obligatorio): ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("[X] El nombre no puede estar vacio.");
            return;
        }
        System.out.print("Descripcion: ");
        String descripcion = sc.nextLine().trim();

        Categoria c = Categoria.builder()
                .eliminado(false)
                .createdAt(LocalDateTime.now())
                .nombre(nombre)
                .descripcion(descripcion)
                .build();

        Categoria guardada = categoriaRepo.guardar(c);
        System.out.println("[OK] Categoria creada con ID: " + guardada.getId());
    }

    private static void bajaCategoria() {
        System.out.print("ID de la categoria a dar de baja: ");
        Long id = leerLong();
        if (id == null) return;

        boolean resultado = categoriaRepo.eliminarLogico(id);
        if (resultado) {
            System.out.println("[OK] Categoria con ID " + id + " dada de baja.");
        } else {
            System.out.println("[X] No existe categoria con ID " + id + ".");
        }
    }

    private static void modificarCategoria() {
        System.out.print("ID de la categoria a modificar: ");
        Long id = leerLong();
        if (id == null) return;

        Optional<Categoria> opt = categoriaRepo.buscarPorId(id);
        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("[X] No existe categoria activa con ID " + id + ".");
            return;
        }
        Categoria c = opt.get();
        System.out.println("Valores actuales:");
        System.out.println("  Nombre     : " + c.getNombre());
        System.out.println("  Descripcion: " + c.getDescripcion());
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = sc.nextLine().trim();
        System.out.print("Nueva descripcion (Enter para mantener): ");
        String descripcion = sc.nextLine().trim();

        if (!nombre.isEmpty())      c.setNombre(nombre);
        if (!descripcion.isEmpty()) c.setDescripcion(descripcion);

        categoriaRepo.guardar(c);
        System.out.println("[OK] Categoria actualizada.");
    }

    private static void listarCategorias() {
        List<Categoria> lista = categoriaRepo.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("No hay categorias activas.");
            return;
        }
        System.out.println("\n  ID   | Nombre                  | Descripcion");
        System.out.println("-------+--------------------------+-------------------------");
        for (Categoria c : lista) {
            System.out.printf("  %-4d | %-24s | %s%n",
                    c.getId(), c.getNombre(), c.getDescripcion());
        }
    }

    // ─────────────────────────────────────────────────────────────
    // MENU PRODUCTOS
    // ─────────────────────────────────────────────────────────────
    private static void menuProductos() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- ABM PRODUCTOS ---");
            System.out.println("  1. Alta");
            System.out.println("  2. Baja logica");
            System.out.println("  3. Modificacion");
            System.out.println("  4. Listado");
            System.out.println("  0. Volver");
            System.out.print("Opcion: ");
            switch (leerInt()) {
                case 1 -> altaProducto();
                case 2 -> bajaProducto();
                case 3 -> modificarProducto();
                case 4 -> listarProductos();
                case 0 -> volver = true;
                default -> System.out.println("[!] Opcion invalida.");
            }
        }
    }

    private static void altaProducto() {
        List<Categoria> categorias = categoriaRepo.listarActivos();
        if (categorias.isEmpty()) {
            System.out.println("[X] No hay categorias activas. Cree una primero.");
            return;
        }
        System.out.println("Categorias disponibles:");
        for (Categoria c : categorias) {
            System.out.printf("  [%d] %s%n", c.getId(), c.getNombre());
        }
        System.out.print("ID de categoria: ");
        Long catId = leerLong();
        if (catId == null) return;
        Optional<Categoria> catOpt = categoriaRepo.buscarPorId(catId);
        if (catOpt.isEmpty() || catOpt.get().isEliminado()) {
            System.out.println("[X] Categoria no valida.");
            return;
        }

        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) { System.out.println("[X] Nombre obligatorio."); return; }

        System.out.print("Descripcion: ");
        String descripcion = sc.nextLine().trim();

        System.out.print("Precio: ");
        Double precio = leerDouble();
        if (precio == null || precio < 0) { System.out.println("[X] Precio invalido."); return; }

        System.out.print("Stock: ");
        Integer stock = leerIntPositivo();
        if (stock == null || stock < 0) { System.out.println("[X] Stock invalido."); return; }

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
        System.out.println("[OK] Producto creado con ID: " + guardado.getId());
    }

    private static void bajaProducto() {
        System.out.print("ID del producto a dar de baja: ");
        Long id = leerLong();
        if (id == null) return;

        Optional<Producto> opt = productoRepo.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("[X] No existe producto con ID " + id + ".");
            return;
        }
        if (opt.get().isEliminado()) {
            System.out.println("[X] El producto \"" + opt.get().getNombre() + "\" ya esta dado de baja.");
            return;
        }
        productoRepo.eliminarLogico(id);
        System.out.println("[OK] Producto \"" + opt.get().getNombre() + "\" dado de baja.");
    }

    private static void modificarProducto() {
        System.out.print("ID del producto a modificar: ");
        Long id = leerLong();
        if (id == null) return;

        Optional<Producto> opt = productoRepo.buscarPorId(id);
        if (opt.isEmpty() || opt.get().isEliminado()) {
            System.out.println("[X] No existe producto activo con ID " + id + ".");
            return;
        }
        Producto p = opt.get();
        System.out.println("Valores actuales:");
        System.out.println("  Nombre : " + p.getNombre());
        System.out.printf("  Precio : %.2f%n", p.getPrecio());
        System.out.println("  Stock  : " + p.getStock());

        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = sc.nextLine().trim();

        System.out.print("Nuevo precio (Enter para mantener): ");
        String precioStr = sc.nextLine().trim();

        System.out.print("Nuevo stock (Enter para mantener): ");
        String stockStr = sc.nextLine().trim();

        if (!nombre.isEmpty()) p.setNombre(nombre);
        if (!precioStr.isEmpty()) {
            try {
                p.setPrecio(Double.parseDouble(precioStr));
            } catch (NumberFormatException e) {
                System.out.println("[!] Precio invalido, se mantiene el valor anterior.");
            }
        }
        if (!stockStr.isEmpty()) {
            try {
                p.setStock(Integer.parseInt(stockStr));
            } catch (NumberFormatException e) {
                System.out.println("[!] Stock invalido, se mantiene el valor anterior.");
            }
        }

        productoRepo.guardar(p);
        System.out.println("[OK] Producto actualizado.");
    }

    private static void listarProductos() {
        List<Producto> lista = productoRepo.listarActivos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos activos.");
            return;
        }
        System.out.println("\n  ID   | Nombre                  |    Precio | Stock | Categoria");
        System.out.println("-------+--------------------------+-----------+-------+----------------");
        for (Producto p : lista) {
            System.out.printf("  %-4d | %-24s | %9.2f | %5d | %s%n",
                    p.getId(), p.getNombre(), p.getPrecio(), p.getStock(),
                    p.getCategoria().getNombre());
        }
    }

    // ─────────────────────────────────────────────────────────────
    // MENU REPORTES
    // ─────────────────────────────────────────────────────────────
    private static void menuReportes() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- REPORTES ---");
            System.out.println("  1. Productos por categoria");
            System.out.println("  0. Volver");
            System.out.print("Opcion: ");
            switch (leerInt()) {
                case 1 -> productosPorCategoria();
                case 0 -> volver = true;
                default -> System.out.println("[!] Opcion invalida.");
            }
        }
    }

    private static void productosPorCategoria() {
        List<Categoria> categorias = categoriaRepo.listarActivos();
        if (categorias.isEmpty()) {
            System.out.println("No hay categorias activas.");
            return;
        }
        System.out.println("Selecciona una categoria:");
        for (Categoria c : categorias) {
            System.out.printf("  [%d] %s%n", c.getId(), c.getNombre());
        }
        System.out.print("ID de categoria: ");
        Long catId = leerLong();
        if (catId == null) return;

        List<Producto> productos = productoRepo.buscarPorCategoria(catId);
        if (productos.isEmpty()) {
            System.out.println("No hay productos activos en esa categoria.");
            return;
        }
        System.out.println("\n  ID   | Nombre                  |    Precio | Stock");
        System.out.println("-------+--------------------------+-----------+------");
        for (Producto p : productos) {
            System.out.printf("  %-4d | %-24s | %9.2f | %d%n",
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
            System.out.println("[X] ID invalido.");
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
