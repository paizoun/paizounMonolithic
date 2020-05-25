package com.paizoun.co.repository;

import com.paizoun.co.domain.ResultadoPartido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ResultadoPartido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResultadoPartidoRepository extends JpaRepository<ResultadoPartido, Long> {
}
