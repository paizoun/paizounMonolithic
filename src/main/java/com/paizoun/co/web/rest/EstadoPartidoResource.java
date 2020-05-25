package com.paizoun.co.web.rest;

import com.paizoun.co.domain.EstadoPartido;
import com.paizoun.co.repository.EstadoPartidoRepository;
import com.paizoun.co.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.paizoun.co.domain.EstadoPartido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoPartidoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoPartidoResource.class);

    private static final String ENTITY_NAME = "estadoPartido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoPartidoRepository estadoPartidoRepository;

    public EstadoPartidoResource(EstadoPartidoRepository estadoPartidoRepository) {
        this.estadoPartidoRepository = estadoPartidoRepository;
    }

    /**
     * {@code POST  /estado-partidos} : Create a new estadoPartido.
     *
     * @param estadoPartido the estadoPartido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoPartido, or with status {@code 400 (Bad Request)} if the estadoPartido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-partidos")
    public ResponseEntity<EstadoPartido> createEstadoPartido(@Valid @RequestBody EstadoPartido estadoPartido) throws URISyntaxException {
        log.debug("REST request to save EstadoPartido : {}", estadoPartido);
        if (estadoPartido.getId() != null) {
            throw new BadRequestAlertException("A new estadoPartido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoPartido result = estadoPartidoRepository.save(estadoPartido);
        return ResponseEntity.created(new URI("/api/estado-partidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-partidos} : Updates an existing estadoPartido.
     *
     * @param estadoPartido the estadoPartido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoPartido,
     * or with status {@code 400 (Bad Request)} if the estadoPartido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoPartido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-partidos")
    public ResponseEntity<EstadoPartido> updateEstadoPartido(@Valid @RequestBody EstadoPartido estadoPartido) throws URISyntaxException {
        log.debug("REST request to update EstadoPartido : {}", estadoPartido);
        if (estadoPartido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoPartido result = estadoPartidoRepository.save(estadoPartido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoPartido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-partidos} : get all the estadoPartidos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoPartidos in body.
     */
    @GetMapping("/estado-partidos")
    public List<EstadoPartido> getAllEstadoPartidos() {
        log.debug("REST request to get all EstadoPartidos");
        return estadoPartidoRepository.findAll();
    }

    /**
     * {@code GET  /estado-partidos/:id} : get the "id" estadoPartido.
     *
     * @param id the id of the estadoPartido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoPartido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-partidos/{id}")
    public ResponseEntity<EstadoPartido> getEstadoPartido(@PathVariable Long id) {
        log.debug("REST request to get EstadoPartido : {}", id);
        Optional<EstadoPartido> estadoPartido = estadoPartidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoPartido);
    }

    /**
     * {@code DELETE  /estado-partidos/:id} : delete the "id" estadoPartido.
     *
     * @param id the id of the estadoPartido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-partidos/{id}")
    public ResponseEntity<Void> deleteEstadoPartido(@PathVariable Long id) {
        log.debug("REST request to delete EstadoPartido : {}", id);
        estadoPartidoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
