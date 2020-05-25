package com.paizoun.co.repository;

import com.paizoun.co.domain.EstadoPartido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EstadoPartido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoPartidoRepository extends JpaRepository<EstadoPartido, Long> {
}
