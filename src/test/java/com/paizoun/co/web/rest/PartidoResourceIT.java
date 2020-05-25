package com.paizoun.co.web.rest;

import com.paizoun.co.PaizounApp;
import com.paizoun.co.domain.Partido;
import com.paizoun.co.repository.PartidoRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PartidoResource} REST controller.
 */
@SpringBootTest(classes = PaizounApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PartidoResourceIT {

    private static final Instant DEFAULT_FECHA_HORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_HORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_FINALIZADO = false;
    private static final Boolean UPDATED_FINALIZADO = true;

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartidoMockMvc;

    private Partido partido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partido createEntity(EntityManager em) {
        Partido partido = new Partido()
            .fechaHora(DEFAULT_FECHA_HORA)
            .finalizado(DEFAULT_FINALIZADO);
        return partido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partido createUpdatedEntity(EntityManager em) {
        Partido partido = new Partido()
            .fechaHora(UPDATED_FECHA_HORA)
            .finalizado(UPDATED_FINALIZADO);
        return partido;
    }

    @BeforeEach
    public void initTest() {
        partido = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartido() throws Exception {
        int databaseSizeBeforeCreate = partidoRepository.findAll().size();

        // Create the Partido
        restPartidoMockMvc.perform(post("/api/partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partido)))
            .andExpect(status().isCreated());

        // Validate the Partido in the database
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeCreate + 1);
        Partido testPartido = partidoList.get(partidoList.size() - 1);
        assertThat(testPartido.getFechaHora()).isEqualTo(DEFAULT_FECHA_HORA);
        assertThat(testPartido.isFinalizado()).isEqualTo(DEFAULT_FINALIZADO);
    }

    @Test
    @Transactional
    public void createPartidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partidoRepository.findAll().size();

        // Create the Partido with an existing ID
        partido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartidoMockMvc.perform(post("/api/partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partido)))
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaHoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = partidoRepository.findAll().size();
        // set the field null
        partido.setFechaHora(null);

        // Create the Partido, which fails.

        restPartidoMockMvc.perform(post("/api/partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partido)))
            .andExpect(status().isBadRequest());

        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartidos() throws Exception {
        // Initialize the database
        partidoRepository.saveAndFlush(partido);

        // Get all the partidoList
        restPartidoMockMvc.perform(get("/api/partidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partido.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaHora").value(hasItem(DEFAULT_FECHA_HORA.toString())))
            .andExpect(jsonPath("$.[*].finalizado").value(hasItem(DEFAULT_FINALIZADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPartido() throws Exception {
        // Initialize the database
        partidoRepository.saveAndFlush(partido);

        // Get the partido
        restPartidoMockMvc.perform(get("/api/partidos/{id}", partido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partido.getId().intValue()))
            .andExpect(jsonPath("$.fechaHora").value(DEFAULT_FECHA_HORA.toString()))
            .andExpect(jsonPath("$.finalizado").value(DEFAULT_FINALIZADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPartido() throws Exception {
        // Get the partido
        restPartidoMockMvc.perform(get("/api/partidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartido() throws Exception {
        // Initialize the database
        partidoRepository.saveAndFlush(partido);

        int databaseSizeBeforeUpdate = partidoRepository.findAll().size();

        // Update the partido
        Partido updatedPartido = partidoRepository.findById(partido.getId()).get();
        // Disconnect from session so that the updates on updatedPartido are not directly saved in db
        em.detach(updatedPartido);
        updatedPartido
            .fechaHora(UPDATED_FECHA_HORA)
            .finalizado(UPDATED_FINALIZADO);

        restPartidoMockMvc.perform(put("/api/partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartido)))
            .andExpect(status().isOk());

        // Validate the Partido in the database
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeUpdate);
        Partido testPartido = partidoList.get(partidoList.size() - 1);
        assertThat(testPartido.getFechaHora()).isEqualTo(UPDATED_FECHA_HORA);
        assertThat(testPartido.isFinalizado()).isEqualTo(UPDATED_FINALIZADO);
    }

    @Test
    @Transactional
    public void updateNonExistingPartido() throws Exception {
        int databaseSizeBeforeUpdate = partidoRepository.findAll().size();

        // Create the Partido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartidoMockMvc.perform(put("/api/partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partido)))
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartido() throws Exception {
        // Initialize the database
        partidoRepository.saveAndFlush(partido);

        int databaseSizeBeforeDelete = partidoRepository.findAll().size();

        // Delete the partido
        restPartidoMockMvc.perform(delete("/api/partidos/{id}", partido.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Partido> partidoList = partidoRepository.findAll();
        assertThat(partidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
