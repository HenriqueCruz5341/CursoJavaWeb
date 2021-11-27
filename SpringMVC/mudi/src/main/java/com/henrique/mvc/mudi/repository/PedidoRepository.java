package com.henrique.mvc.mudi.repository;

import java.util.List;

import com.henrique.mvc.mudi.model.Pedido;
import com.henrique.mvc.mudi.model.StatusPedido;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Cacheable("pedidosStatus")
    List<Pedido> findByStatus(StatusPedido status, Pageable paginacao);

    @Query("SELECT p FROM Pedido p JOIN p.user u WHERE u.username = :username")
    List<Pedido> findAllByUsuario(@Param("username") String username);

    @Query("SELECT p FROM Pedido p JOIN p.user u WHERE u.username = :username AND p.status = :status")
    List<Pedido> findByStatusEUsuario(@Param("status") StatusPedido status, @Param("username") String username);

}
