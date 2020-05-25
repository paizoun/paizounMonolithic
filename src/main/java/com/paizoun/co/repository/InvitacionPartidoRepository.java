package com.paizoun.co.repository;

import com.paizoun.co.domain.InvitacionPartido;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InvitacionPartido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InvitacionPartidoRepository extends JpaRepository<InvitacionPartido, Long> {
}
