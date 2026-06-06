package com.utn.repository;

import com.utn.entities.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto> {

    public ProductoRepository() {
        super(Producto.class);
    }

    /**
     * Busca productos activos que pertenezcan a la categoría indicada.
     * La consulta JPQL filtra por eliminado = false y por el ID de la categoría
     * usando un parámetro nombrado (:categoriaId), evitando casteos manuales.
     */
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Producto p " +
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
