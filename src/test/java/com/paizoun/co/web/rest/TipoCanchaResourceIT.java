package com.paizoun.co.web.rest;

import com.paizoun.co.PaizounApp;
import com.paizoun.co.domain.TipoCancha;
import com.paizoun.co.repository.TipoCanchaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoCanchaResource} REST controller.
 */
@SpringBootTest(classes = PaizounApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TipoCanchaResourceIT {

    private static final String DEFAULT_NOMBRE_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_TIPO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD_JUGADORES = 1;
    private static final Integer UPDATED_CANTIDAD_JUGADORES = 2;

    @Autowired
    private TipoCanchaRepository tipoCanchaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoCanchaMockMvc;

    private TipoCancha tipoCancha;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCancha createEntity(EntityManager em) {
        TipoCancha tipoCancha = new TipoCancha()
            .nombreTipo(DEFAULT_NOMBRE_TIPO)
            .cantidadJugadores(DEFAULT_CANTIDAD_JUGADORES);
        return tipoCancha;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCancha createUpdatedEntity(EntityManager em) {
        TipoCancha tipoCancha = new TipoCancha()
            .nombreTipo(UPDATED_NOMBRE_TIPO)
            .cantidadJugadores(UPDATED_CANTIDAD_JUGADORES);
        return tipoCancha;
    }

    @BeforeEach
    public void initTest() {
        tipoCancha = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoCancha() throws Exception {
        int databaseSizeBeforeCreate = tipoCanchaRepository.findAll().size();

        // Create the TipoCancha
        restTipoCanchaMockMvc.perform(post("/api/tipo-canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoCancha)))
            .andExpect(status().isCreated());

        // Validate the TipoCancha in the database
        List<TipoCancha> tipoCanchaList = tipoCanchaRepository.findAll();
        assertThat(tipoCanchaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoCancha testTipoCancha = tipoCanchaList.get(tipoCanchaList.size() - 1);
        assertThat(testTipoCancha.getNombreTipo()).isEqualTo(DEFAULT_NOMBRE_TIPO);
        assertThat(testTipoCancha.getCantidadJugadores()).isEqualTo(DEFAULT_CANTIDAD_JUGADORES);
    }

    @Test
    @Transactional
    public void createTipoCanchaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoCanchaRepository.findAll().size();

        // Create the TipoCancha with an existing ID
        tipoCancha.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoCanchaMockMvc.perform(post("/api/tipo-canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoCancha)))
            .andExpect(status().isBadRequest());

        // Validate the TipoCancha in the database
        List<TipoCancha> tipoCanchaList = tipoCanchaRepository.findAll();
        assertThat(tipoCanchaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoCanchaRepository.findAll().size();
        // set the field null
        tipoCancha.setNombreTipo(null);

        // Create the TipoCancha, which fails.

        restTipoCanchaMockMvc.perform(post("/api/tipo-canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoCancha)))
            .andExpect(status().isBadRequest());

        List<TipoCancha> tipoCanchaList = tipoCanchaRepository.findAll();
        assertThat(tipoCanchaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCantidadJugadoresIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoCanchaRepository.findAll().size();
        // set the field null
        tipoCancha.setCantidadJugadores(null);

        // Create the TipoCancha, which fails.

        restTipoCanchaMockMvc.perform(post("/api/tipo-canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoCancha)))
            .andExpect(status().isBadRequest());

        List<TipoCancha> tipoCanchaList = tipoCanchaRepository.findAll();
        assertThat(tipoCanchaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoCanchas() throws Exception {
        // Initialize the database
        tipoCanchaRepository.saveAndFlush(tipoCancha);

        // Get all the tipoCanchaList
        restTipoCanchaMockMvc.perform(get("/api/tipo-canchas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoCancha.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreTipo").value(hasItem(DEFAULT_NOMBRE_TIPO)))
            .andExpect(jsonPath("$.[*].cantidadJugadores").value(hasItem(DEFAULT_CANTIDAD_JUGADORES)));
    }
    
    @Test
    @Transactional
    public void getTipoCancha() throws Exception {
        // Initialize the database
        tipoCanchaRepository.saveAndFlush(tipoCancha);

        // Get the tipoCancha
        restTipoCanchaMockMvc.perform(get("/api/tipo-canchas/{id}", tipoCancha.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoCancha.getId().intValue()))
            .andExpect(jsonPath("$.nombreTipo").value(DEFAULT_NOMBRE_TIPO))
            .andExpect(jsonPath("$.cantidadJugadores").value(DEFAULT_CANTIDAD_JUGADORES));
    }

    @Test
    @Transactional
    public void getNonExistingTipoCancha() throws Exception {
        // Get the tipoCancha
        restTipoCanchaMockMvc.perform(get("/api/tipo-canchas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoCancha() throws Exception {
        // Initialize the database
        tipoCanchaRepository.saveAndFlush(tipoCancha);

        int databaseSizeBeforeUpdate = tipoCanchaRepository.findAll().size();

        // Update the tipoCancha
        TipoCancha updatedTipoCancha = tipoCanchaRepository.findById(tipoCancha.getId()).get();
        // Disconnect from session so that the updates on updatedTipoCancha are not directly saved in db
        em.detach(updatedTipoCancha);
        updatedTipoCancha
            .nombreTipo(UPDATED_NOMBRE_TIPO)
            .cantidadJugadores(UPDATED_CANTIDAD_JUGADORES);

        restTipoCanchaMockMvc.perform(put("/api/tipo-canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoCancha)))
            .andExpect(status().isOk());

        // Validate the TipoCancha in the database
        List<TipoCancha> tipoCanchaList = tipoCanchaRepository.findAll();
        assertThat(tipoCanchaList).hasSize(databaseSizeBeforeUpdate);
        TipoCancha testTipoCancha = tipoCanchaList.get(tipoCanchaList.size() - 1);
        assertThat(testTipoCancha.getNombreTipo()).isEqualTo(UPDATED_NOMBRE_TIPO);
        assertThat(testTipoCancha.getCantidadJugadores()).isEqualTo(UPDATED_CANTIDAD_JUGADORES);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoCancha() throws Exception {
        int databaseSizeBeforeUpdate = tipoCanchaRepository.findAll().size();

        // Create the TipoCancha

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoCanchaMockMvc.perform(put("/api/tipo-canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoCancha)))
            .andExpect(status().isBadRequest());

        // Validate the TipoCancha in the database
        List<TipoCancha> tipoCanchaList = tipoCanchaRepository.findAll();
        assertThat(tipoCanchaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoCancha() throws Exception {
        // Initialize the database
        tipoCanchaRepository.saveAndFlush(tipoCancha);

        int databaseSizeBeforeDelete = tipoCanchaRepository.findAll().size();

        // Delete the tipoCancha
        restTipoCanchaMockMvc.perform(delete("/api/tipo-canchas/{id}", tipoCancha.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoCancha> tipoCanchaList = tipoCanchaRepository.findAll();
        assertThat(tipoCanchaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
