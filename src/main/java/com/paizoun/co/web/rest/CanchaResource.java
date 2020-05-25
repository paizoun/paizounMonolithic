package com.paizoun.co.web.rest;

import com.paizoun.co.domain.Cancha;
import com.paizoun.co.repository.CanchaRepository;
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
 * REST controller for managing {@link com.paizoun.co.domain.Cancha}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CanchaResource {

    private final Logger log = LoggerFactory.getLogger(CanchaResource.class);

    private static final String ENTITY_NAME = "cancha";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CanchaRepository canchaRepository;

    public CanchaResource(CanchaRepository canchaRepository) {
        this.canchaRepository = canchaRepository;
    }

    /**
     * {@code POST  /canchas} : Create a new cancha.
     *
     * @param cancha the cancha to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cancha, or with status {@code 400 (Bad Request)} if the cancha has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/canchas")
    public ResponseEntity<Cancha> createCancha(@Valid @RequestBody Cancha cancha) throws URISyntaxException {
        log.debug("REST request to save Cancha : {}", cancha);
        if (cancha.getId() != null) {
            throw new BadRequestAlertException("A new cancha cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cancha result = canchaRepository.save(cancha);
        return ResponseEntity.created(new URI("/api/canchas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /canchas} : Updates an existing cancha.
     *
     * @param cancha the cancha to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cancha,
     * or with status {@code 400 (Bad Request)} if the cancha is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cancha couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/canchas")
    public ResponseEntity<Cancha> updateCancha(@Valid @RequestBody Cancha cancha) throws URISyntaxException {
        log.debug("REST request to update Cancha : {}", cancha);
        if (cancha.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cancha result = canchaRepository.save(cancha);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cancha.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /canchas} : get all the canchas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of canchas in body.
     */
    @GetMapping("/canchas")
    public ResponseEntity<List<Cancha>> getAllCanchas(Pageable pageable) {
        log.debug("REST request to get a page of Canchas");
        Page<Cancha> page = canchaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /canchas/:id} : get the "id" cancha.
     *
     * @param id the id of the cancha to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cancha, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/canchas/{id}")
    public ResponseEntity<Cancha> getCancha(@PathVariable Long id) {
        log.debug("REST request to get Cancha : {}", id);
        Optional<Cancha> cancha = canchaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cancha);
    }

    /**
     * {@code DELETE  /canchas/:id} : delete the "id" cancha.
     *
     * @param id the id of the cancha to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/canchas/{id}")
    public ResponseEntity<Void> deleteCancha(@PathVariable Long id) {
        log.debug("REST request to delete Cancha : {}", id);
        canchaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
