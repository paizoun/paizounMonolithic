package com.paizoun.co.web.rest;

import com.paizoun.co.domain.Partido;
import com.paizoun.co.repository.PartidoRepository;
import com.paizoun.co.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.paizoun.co.domain.Partido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PartidoResource {

    private final Logger log = LoggerFactory.getLogger(PartidoResource.class);

    private static final String ENTITY_NAME = "partido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartidoRepository partidoRepository;

    public PartidoResource(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    /**
     * {@code POST  /partidos} : Create a new partido.
     *
     * @param partido the partido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partido, or with status {@code 400 (Bad Request)} if the partido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/partidos")
    public ResponseEntity<Partido> createPartido(@Valid @RequestBody Partido partido) throws URISyntaxException {
        log.debug("REST request to save Partido : {}", partido);
        if (partido.getId() != null) {
            throw new BadRequestAlertException("A new partido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Partido result = partidoRepository.save(partido);
        return ResponseEntity.created(new URI("/api/partidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /partidos} : Updates an existing partido.
     *
     * @param partido the partido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partido,
     * or with status {@code 400 (Bad Request)} if the partido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/partidos")
    public ResponseEntity<Partido> updatePartido(@Valid @RequestBody Partido partido) throws URISyntaxException {
        log.debug("REST request to update Partido : {}", partido);
        if (partido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Partido result = partidoRepository.save(partido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /partidos} : get all the partidos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partidos in body.
     */
    @GetMapping("/partidos")
    public ResponseEntity<List<Partido>> getAllPartidos(Pageable pageable) {
        log.debug("REST request to get a page of Partidos");
        Page<Partido> page = partidoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /partidos/:id} : get the "id" partido.
     *
     * @param id the id of the partido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/partidos/{id}")
    public ResponseEntity<Partido> getPartido(@PathVariable Long id) {
        log.debug("REST request to get Partido : {}", id);
        Optional<Partido> partido = partidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(partido);
    }

    /**
     * {@code DELETE  /partidos/:id} : delete the "id" partido.
     *
     * @param id the id of the partido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/partidos/{id}")
    public ResponseEntity<Void> deletePartido(@PathVariable Long id) {
        log.debug("REST request to delete Partido : {}", id);
        partidoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
