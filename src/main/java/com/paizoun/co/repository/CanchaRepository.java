package com.paizoun.co.repository;

import com.paizoun.co.domain.Cancha;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cancha entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CanchaRepository extends JpaRepository<Cancha, Long> {
}
