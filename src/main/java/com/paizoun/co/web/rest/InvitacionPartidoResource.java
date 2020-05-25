package com.paizoun.co.web.rest;

import com.paizoun.co.domain.InvitacionPartido;
import com.paizoun.co.repository.InvitacionPartidoRepository;
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
 * REST controller for managing {@link com.paizoun.co.domain.InvitacionPartido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InvitacionPartidoResource {

    private final Logger log = LoggerFactory.getLogger(InvitacionPartidoResource.class);

    private static final String ENTITY_NAME = "invitacionPartido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvitacionPartidoRepository invitacionPartidoRepository;

    public InvitacionPartidoResource(InvitacionPartidoRepository invitacionPartidoRepository) {
        this.invitacionPartidoRepository = invitacionPartidoRepository;
    }

    /**
     * {@code POST  /invitacion-partidos} : Create a new invitacionPartido.
     *
     * @param invitacionPartido the invitacionPartido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invitacionPartido, or with status {@code 400 (Bad Request)} if the invitacionPartido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invitacion-partidos")
    public ResponseEntity<InvitacionPartido> createInvitacionPartido(@Valid @RequestBody InvitacionPartido invitacionPartido) throws URISyntaxException {
        log.debug("REST request to save InvitacionPartido : {}", invitacionPartido);
        if (invitacionPartido.getId() != null) {
            throw new BadRequestAlertException("A new invitacionPartido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvitacionPartido result = invitacionPartidoRepository.save(invitacionPartido);
        return ResponseEntity.created(new URI("/api/invitacion-partidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invitacion-partidos} : Updates an existing invitacionPartido.
     *
     * @param invitacionPartido the invitacionPartido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invitacionPartido,
     * or with status {@code 400 (Bad Request)} if the invitacionPartido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invitacionPartido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invitacion-partidos")
    public ResponseEntity<InvitacionPartido> updateInvitacionPartido(@Valid @RequestBody InvitacionPartido invitacionPartido) throws URISyntaxException {
        log.debug("REST request to update InvitacionPartido : {}", invitacionPartido);
        if (invitacionPartido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvitacionPartido result = invitacionPartidoRepository.save(invitacionPartido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, invitacionPartido.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invitacion-partidos} : get all the invitacionPartidos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invitacionPartidos in body.
     */
    @GetMapping("/invitacion-partidos")
    public ResponseEntity<List<InvitacionPartido>> getAllInvitacionPartidos(Pageable pageable) {
        log.debug("REST request to get a page of InvitacionPartidos");
        Page<InvitacionPartido> page = invitacionPartidoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invitacion-partidos/:id} : get the "id" invitacionPartido.
     *
     * @param id the id of the invitacionPartido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invitacionPartido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invitacion-partidos/{id}")
    public ResponseEntity<InvitacionPartido> getInvitacionPartido(@PathVariable Long id) {
        log.debug("REST request to get InvitacionPartido : {}", id);
        Optional<InvitacionPartido> invitacionPartido = invitacionPartidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(invitacionPartido);
    }

    /**
     * {@code DELETE  /invitacion-partidos/:id} : delete the "id" invitacionPartido.
     *
     * @param id the id of the invitacionPartido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invitacion-partidos/{id}")
    public ResponseEntity<Void> deleteInvitacionPartido(@PathVariable Long id) {
        log.debug("REST request to delete InvitacionPartido : {}", id);
        invitacionPartidoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
