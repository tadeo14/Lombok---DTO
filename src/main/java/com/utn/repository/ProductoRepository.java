package com.utn.repository;

import com.utn.entities.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    // Sobreescribe listarActivos para traer la categoria con JOIN FETCH
    // y evitar LazyInitializationException al acceder a producto.getCategoria()
    @Override
    public List<Producto> listarActivos() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Producto p JOIN FETCH p.categoria WHERE p.eliminado = false";
            return em.createQuery(jpql, Producto.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca productos activos que pertenezcan a la categoria indicada.
     * La consulta JPQL filtra por eliminado = false y por el ID de la categoria
     * usando un parametro nombrado (:categoriaId), evitando casteos manuales.
     */
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Producto p JOIN FETCH p.categoria " +
                          "WHERE p.eliminado = false " +
                          "AND p.categoria.id = :categoriaId";
            return em.createQuery(jpql, Producto.class)
                     .setParameter("categoriaId", categoriaId)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}
