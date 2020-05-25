package com.paizoun.co.web.rest;

import com.paizoun.co.domain.TipoCancha;
import com.paizoun.co.repository.TipoCanchaRepository;
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
 * REST controller for managing {@link com.paizoun.co.domain.TipoCancha}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoCanchaResource {

    private final Logger log = LoggerFactory.getLogger(TipoCanchaResource.class);

    private static final String ENTITY_NAME = "tipoCancha";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoCanchaRepository tipoCanchaRepository;

    public TipoCanchaResource(TipoCanchaRepository tipoCanchaRepository) {
        this.tipoCanchaRepository = tipoCanchaRepository;
    }

    /**
     * {@code POST  /tipo-canchas} : Create a new tipoCancha.
     *
     * @param tipoCancha the tipoCancha to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoCancha, or with status {@code 400 (Bad Request)} if the tipoCancha has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-canchas")
    public ResponseEntity<TipoCancha> createTipoCancha(@Valid @RequestBody TipoCancha tipoCancha) throws URISyntaxException {
        log.debug("REST request to save TipoCancha : {}", tipoCancha);
        if (tipoCancha.getId() != null) {
            throw new BadRequestAlertException("A new tipoCancha cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoCancha result = tipoCanchaRepository.save(tipoCancha);
        return ResponseEntity.created(new URI("/api/tipo-canchas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-canchas} : Updates an existing tipoCancha.
     *
     * @param tipoCancha the tipoCancha to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoCancha,
     * or with status {@code 400 (Bad Request)} if the tipoCancha is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoCancha couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-canchas")
    public ResponseEntity<TipoCancha> updateTipoCancha(@Valid @RequestBody TipoCancha tipoCancha) throws URISyntaxException {
        log.debug("REST request to update TipoCancha : {}", tipoCancha);
        if (tipoCancha.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoCancha result = tipoCanchaRepository.save(tipoCancha);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoCancha.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-canchas} : get all the tipoCanchas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoCanchas in body.
     */
    @GetMapping("/tipo-canchas")
    public List<TipoCancha> getAllTipoCanchas() {
        log.debug("REST request to get all TipoCanchas");
        return tipoCanchaRepository.findAll();
    }

    /**
     * {@code GET  /tipo-canchas/:id} : get the "id" tipoCancha.
     *
     * @param id the id of the tipoCancha to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoCancha, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-canchas/{id}")
    public ResponseEntity<TipoCancha> getTipoCancha(@PathVariable Long id) {
        log.debug("REST request to get TipoCancha : {}", id);
        Optional<TipoCancha> tipoCancha = tipoCanchaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoCancha);
    }

    /**
     * {@code DELETE  /tipo-canchas/:id} : delete the "id" tipoCancha.
     *
     * @param id the id of the tipoCancha to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-canchas/{id}")
    public ResponseEntity<Void> deleteTipoCancha(@PathVariable Long id) {
        log.debug("REST request to delete TipoCancha : {}", id);
        tipoCanchaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
