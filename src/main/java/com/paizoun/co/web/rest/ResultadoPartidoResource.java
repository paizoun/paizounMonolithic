package com.paizoun.co.web.rest;

import com.paizoun.co.domain.ResultadoPartido;
import com.paizoun.co.repository.ResultadoPartidoRepository;
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
 * REST controller for managing {@link com.paizoun.co.domain.ResultadoPartido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResultadoPartidoResource {

    private final Logger log = LoggerFactory.getLogger(ResultadoPartidoResource.class);

    private static final String ENTITY_NAME = "resultadoPartido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultadoPartidoRepository resultadoPartidoRepository;

    public ResultadoPartidoResource(ResultadoPartidoRepository resultadoPartidoRepository) {
        this.resultadoPartidoRepository = resultadoPartidoRepository;
    }

    /**
     * {@code POST  /resultado-partidos} : Create a new resultadoPartido.
     *
     * @param resultadoPartido the resultadoPartido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultadoPartido, or with status {@code 400 (Bad Request)} if the resultadoPartido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultado-partidos")
    public ResponseEntity<ResultadoPartido> createResultadoPartido(@Valid @RequestBody ResultadoPartido resultadoPartido) throws URISyntaxException {
        log.debug("REST request to save ResultadoPartido : {}", resultadoPartido);
        if (resultadoPartido.getId() != null) {
            throw new BadRequestAlertException("A new resultadoPartido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultadoPartido result = resultadoPartidoRepository.save(resultadoPartido);
        return ResponseEntity.created(new URI("/api/resultado-partidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultado-partidos} : Updates an existing resultadoPartido.
     *
     * @param resultadoPartido the resultadoPartido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultadoPartido,
     * or with status {@code 400 (Bad Request)} if the resultadoPartido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultadoPartido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultado-partidos")
    public ResponseEntity<ResultadoPartido> updateResultadoPartido(@Valid @RequestBody ResultadoPartido resultadoPartido) throws URISyntaxException {
        log.debug("REST request to update ResultadoPartido : {}", resultadoPartido);
        if (resultadoPartido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultadoPartido result = resultadoPartidoRepository.save(resultadoPartido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultadoPartido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultado-partidos} : get all the resultadoPartidos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultadoPartidos in body.
     */
    @GetMapping("/resultado-partidos")
    public List<ResultadoPartido> getAllResultadoPartidos() {
        log.debug("REST request to get all ResultadoPartidos");
        return resultadoPartidoRepository.findAll();
    }

    /**
     * {@code GET  /resultado-partidos/:id} : get the "id" resultadoPartido.
     *
     * @param id the id of the resultadoPartido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultadoPartido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultado-partidos/{id}")
    public ResponseEntity<ResultadoPartido> getResultadoPartido(@PathVariable Long id) {
        log.debug("REST request to get ResultadoPartido : {}", id);
        Optional<ResultadoPartido> resultadoPartido = resultadoPartidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(resultadoPartido);
    }

    /**
     * {@code DELETE  /resultado-partidos/:id} : delete the "id" resultadoPartido.
     *
     * @param id the id of the resultadoPartido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultado-partidos/{id}")
    public ResponseEntity<Void> deleteResultadoPartido(@PathVariable Long id) {
        log.debug("REST request to delete ResultadoPartido : {}", id);
        resultadoPartidoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
