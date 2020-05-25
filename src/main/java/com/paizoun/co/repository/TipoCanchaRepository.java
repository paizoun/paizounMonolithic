package com.paizoun.co.repository;

import com.paizoun.co.domain.TipoCancha;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoCancha entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoCanchaRepository extends JpaRepository<TipoCancha, Long> {
}
