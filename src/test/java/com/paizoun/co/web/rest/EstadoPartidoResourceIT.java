package com.paizoun.co.web.rest;

import com.paizoun.co.PaizounApp;
import com.paizoun.co.domain.EstadoPartido;
import com.paizoun.co.repository.EstadoPartidoRepository;

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
 * Integration tests for the {@link EstadoPartidoResource} REST controller.
 */
@SpringBootTest(classes = PaizounApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EstadoPartidoResourceIT {

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    @Autowired
    private EstadoPartidoRepository estadoPartidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoPartidoMockMvc;

    private EstadoPartido estadoPartido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoPartido createEntity(EntityManager em) {
        EstadoPartido estadoPartido = new EstadoPartido()
            .estado(DEFAULT_ESTADO);
        return estadoPartido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoPartido createUpdatedEntity(EntityManager em) {
        EstadoPartido estadoPartido = new EstadoPartido()
            .estado(UPDATED_ESTADO);
        return estadoPartido;
    }

    @BeforeEach
    public void initTest() {
        estadoPartido = createEntity(em);
    }

    @Test
    @Transactional
    public void createEstadoPartido() throws Exception {
        int databaseSizeBeforeCreate = estadoPartidoRepository.findAll().size();

        // Create the EstadoPartido
        restEstadoPartidoMockMvc.perform(post("/api/estado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoPartido)))
            .andExpect(status().isCreated());

        // Validate the EstadoPartido in the database
        List<EstadoPartido> estadoPartidoList = estadoPartidoRepository.findAll();
        assertThat(estadoPartidoList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoPartido testEstadoPartido = estadoPartidoList.get(estadoPartidoList.size() - 1);
        assertThat(testEstadoPartido.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createEstadoPartidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = estadoPartidoRepository.findAll().size();

        // Create the EstadoPartido with an existing ID
        estadoPartido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoPartidoMockMvc.perform(post("/api/estado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoPartido)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoPartido in the database
        List<EstadoPartido> estadoPartidoList = estadoPartidoRepository.findAll();
        assertThat(estadoPartidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = estadoPartidoRepository.findAll().size();
        // set the field null
        estadoPartido.setEstado(null);

        // Create the EstadoPartido, which fails.

        restEstadoPartidoMockMvc.perform(post("/api/estado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoPartido)))
            .andExpect(status().isBadRequest());

        List<EstadoPartido> estadoPartidoList = estadoPartidoRepository.findAll();
        assertThat(estadoPartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEstadoPartidos() throws Exception {
        // Initialize the database
        estadoPartidoRepository.saveAndFlush(estadoPartido);

        // Get all the estadoPartidoList
        restEstadoPartidoMockMvc.perform(get("/api/estado-partidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoPartido.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }
    
    @Test
    @Transactional
    public void getEstadoPartido() throws Exception {
        // Initialize the database
        estadoPartidoRepository.saveAndFlush(estadoPartido);

        // Get the estadoPartido
        restEstadoPartidoMockMvc.perform(get("/api/estado-partidos/{id}", estadoPartido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoPartido.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    public void getNonExistingEstadoPartido() throws Exception {
        // Get the estadoPartido
        restEstadoPartidoMockMvc.perform(get("/api/estado-partidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEstadoPartido() throws Exception {
        // Initialize the database
        estadoPartidoRepository.saveAndFlush(estadoPartido);

        int databaseSizeBeforeUpdate = estadoPartidoRepository.findAll().size();

        // Update the estadoPartido
        EstadoPartido updatedEstadoPartido = estadoPartidoRepository.findById(estadoPartido.getId()).get();
        // Disconnect from session so that the updates on updatedEstadoPartido are not directly saved in db
        em.detach(updatedEstadoPartido);
        updatedEstadoPartido
            .estado(UPDATED_ESTADO);

        restEstadoPartidoMockMvc.perform(put("/api/estado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEstadoPartido)))
            .andExpect(status().isOk());

        // Validate the EstadoPartido in the database
        List<EstadoPartido> estadoPartidoList = estadoPartidoRepository.findAll();
        assertThat(estadoPartidoList).hasSize(databaseSizeBeforeUpdate);
        EstadoPartido testEstadoPartido = estadoPartidoList.get(estadoPartidoList.size() - 1);
        assertThat(testEstadoPartido.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingEstadoPartido() throws Exception {
        int databaseSizeBeforeUpdate = estadoPartidoRepository.findAll().size();

        // Create the EstadoPartido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoPartidoMockMvc.perform(put("/api/estado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(estadoPartido)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoPartido in the database
        List<EstadoPartido> estadoPartidoList = estadoPartidoRepository.findAll();
        assertThat(estadoPartidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEstadoPartido() throws Exception {
        // Initialize the database
        estadoPartidoRepository.saveAndFlush(estadoPartido);

        int databaseSizeBeforeDelete = estadoPartidoRepository.findAll().size();

        // Delete the estadoPartido
        restEstadoPartidoMockMvc.perform(delete("/api/estado-partidos/{id}", estadoPartido.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoPartido> estadoPartidoList = estadoPartidoRepository.findAll();
        assertThat(estadoPartidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
