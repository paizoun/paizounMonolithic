package com.paizoun.co.web.rest;

import com.paizoun.co.PaizounApp;
import com.paizoun.co.domain.ResultadoPartido;
import com.paizoun.co.repository.ResultadoPartidoRepository;

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
 * Integration tests for the {@link ResultadoPartidoResource} REST controller.
 */
@SpringBootTest(classes = PaizounApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ResultadoPartidoResourceIT {

    private static final Integer DEFAULT_GOLES_EQIOPO_A = 1;
    private static final Integer UPDATED_GOLES_EQIOPO_A = 2;

    private static final Integer DEFAULT_GOLES_EQIOPO_B = 1;
    private static final Integer UPDATED_GOLES_EQIOPO_B = 2;

    private static final Boolean DEFAULT_GANO_EQUIPO_A = false;
    private static final Boolean UPDATED_GANO_EQUIPO_A = true;

    private static final Boolean DEFAULT_GANO_EQUIPO_B = false;
    private static final Boolean UPDATED_GANO_EQUIPO_B = true;

    @Autowired
    private ResultadoPartidoRepository resultadoPartidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultadoPartidoMockMvc;

    private ResultadoPartido resultadoPartido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultadoPartido createEntity(EntityManager em) {
        ResultadoPartido resultadoPartido = new ResultadoPartido()
            .golesEqiopoA(DEFAULT_GOLES_EQIOPO_A)
            .golesEqiopoB(DEFAULT_GOLES_EQIOPO_B)
            .ganoEquipoA(DEFAULT_GANO_EQUIPO_A)
            .ganoEquipoB(DEFAULT_GANO_EQUIPO_B);
        return resultadoPartido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultadoPartido createUpdatedEntity(EntityManager em) {
        ResultadoPartido resultadoPartido = new ResultadoPartido()
            .golesEqiopoA(UPDATED_GOLES_EQIOPO_A)
            .golesEqiopoB(UPDATED_GOLES_EQIOPO_B)
            .ganoEquipoA(UPDATED_GANO_EQUIPO_A)
            .ganoEquipoB(UPDATED_GANO_EQUIPO_B);
        return resultadoPartido;
    }

    @BeforeEach
    public void initTest() {
        resultadoPartido = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultadoPartido() throws Exception {
        int databaseSizeBeforeCreate = resultadoPartidoRepository.findAll().size();

        // Create the ResultadoPartido
        restResultadoPartidoMockMvc.perform(post("/api/resultado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadoPartido)))
            .andExpect(status().isCreated());

        // Validate the ResultadoPartido in the database
        List<ResultadoPartido> resultadoPartidoList = resultadoPartidoRepository.findAll();
        assertThat(resultadoPartidoList).hasSize(databaseSizeBeforeCreate + 1);
        ResultadoPartido testResultadoPartido = resultadoPartidoList.get(resultadoPartidoList.size() - 1);
        assertThat(testResultadoPartido.getGolesEqiopoA()).isEqualTo(DEFAULT_GOLES_EQIOPO_A);
        assertThat(testResultadoPartido.getGolesEqiopoB()).isEqualTo(DEFAULT_GOLES_EQIOPO_B);
        assertThat(testResultadoPartido.isGanoEquipoA()).isEqualTo(DEFAULT_GANO_EQUIPO_A);
        assertThat(testResultadoPartido.isGanoEquipoB()).isEqualTo(DEFAULT_GANO_EQUIPO_B);
    }

    @Test
    @Transactional
    public void createResultadoPartidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadoPartidoRepository.findAll().size();

        // Create the ResultadoPartido with an existing ID
        resultadoPartido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadoPartidoMockMvc.perform(post("/api/resultado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadoPartido)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoPartido in the database
        List<ResultadoPartido> resultadoPartidoList = resultadoPartidoRepository.findAll();
        assertThat(resultadoPartidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGolesEqiopoAIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoPartidoRepository.findAll().size();
        // set the field null
        resultadoPartido.setGolesEqiopoA(null);

        // Create the ResultadoPartido, which fails.

        restResultadoPartidoMockMvc.perform(post("/api/resultado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadoPartido)))
            .andExpect(status().isBadRequest());

        List<ResultadoPartido> resultadoPartidoList = resultadoPartidoRepository.findAll();
        assertThat(resultadoPartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGolesEqiopoBIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultadoPartidoRepository.findAll().size();
        // set the field null
        resultadoPartido.setGolesEqiopoB(null);

        // Create the ResultadoPartido, which fails.

        restResultadoPartidoMockMvc.perform(post("/api/resultado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadoPartido)))
            .andExpect(status().isBadRequest());

        List<ResultadoPartido> resultadoPartidoList = resultadoPartidoRepository.findAll();
        assertThat(resultadoPartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultadoPartidos() throws Exception {
        // Initialize the database
        resultadoPartidoRepository.saveAndFlush(resultadoPartido);

        // Get all the resultadoPartidoList
        restResultadoPartidoMockMvc.perform(get("/api/resultado-partidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadoPartido.getId().intValue())))
            .andExpect(jsonPath("$.[*].golesEqiopoA").value(hasItem(DEFAULT_GOLES_EQIOPO_A)))
            .andExpect(jsonPath("$.[*].golesEqiopoB").value(hasItem(DEFAULT_GOLES_EQIOPO_B)))
            .andExpect(jsonPath("$.[*].ganoEquipoA").value(hasItem(DEFAULT_GANO_EQUIPO_A.booleanValue())))
            .andExpect(jsonPath("$.[*].ganoEquipoB").value(hasItem(DEFAULT_GANO_EQUIPO_B.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getResultadoPartido() throws Exception {
        // Initialize the database
        resultadoPartidoRepository.saveAndFlush(resultadoPartido);

        // Get the resultadoPartido
        restResultadoPartidoMockMvc.perform(get("/api/resultado-partidos/{id}", resultadoPartido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultadoPartido.getId().intValue()))
            .andExpect(jsonPath("$.golesEqiopoA").value(DEFAULT_GOLES_EQIOPO_A))
            .andExpect(jsonPath("$.golesEqiopoB").value(DEFAULT_GOLES_EQIOPO_B))
            .andExpect(jsonPath("$.ganoEquipoA").value(DEFAULT_GANO_EQUIPO_A.booleanValue()))
            .andExpect(jsonPath("$.ganoEquipoB").value(DEFAULT_GANO_EQUIPO_B.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResultadoPartido() throws Exception {
        // Get the resultadoPartido
        restResultadoPartidoMockMvc.perform(get("/api/resultado-partidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultadoPartido() throws Exception {
        // Initialize the database
        resultadoPartidoRepository.saveAndFlush(resultadoPartido);

        int databaseSizeBeforeUpdate = resultadoPartidoRepository.findAll().size();

        // Update the resultadoPartido
        ResultadoPartido updatedResultadoPartido = resultadoPartidoRepository.findById(resultadoPartido.getId()).get();
        // Disconnect from session so that the updates on updatedResultadoPartido are not directly saved in db
        em.detach(updatedResultadoPartido);
        updatedResultadoPartido
            .golesEqiopoA(UPDATED_GOLES_EQIOPO_A)
            .golesEqiopoB(UPDATED_GOLES_EQIOPO_B)
            .ganoEquipoA(UPDATED_GANO_EQUIPO_A)
            .ganoEquipoB(UPDATED_GANO_EQUIPO_B);

        restResultadoPartidoMockMvc.perform(put("/api/resultado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultadoPartido)))
            .andExpect(status().isOk());

        // Validate the ResultadoPartido in the database
        List<ResultadoPartido> resultadoPartidoList = resultadoPartidoRepository.findAll();
        assertThat(resultadoPartidoList).hasSize(databaseSizeBeforeUpdate);
        ResultadoPartido testResultadoPartido = resultadoPartidoList.get(resultadoPartidoList.size() - 1);
        assertThat(testResultadoPartido.getGolesEqiopoA()).isEqualTo(UPDATED_GOLES_EQIOPO_A);
        assertThat(testResultadoPartido.getGolesEqiopoB()).isEqualTo(UPDATED_GOLES_EQIOPO_B);
        assertThat(testResultadoPartido.isGanoEquipoA()).isEqualTo(UPDATED_GANO_EQUIPO_A);
        assertThat(testResultadoPartido.isGanoEquipoB()).isEqualTo(UPDATED_GANO_EQUIPO_B);
    }

    @Test
    @Transactional
    public void updateNonExistingResultadoPartido() throws Exception {
        int databaseSizeBeforeUpdate = resultadoPartidoRepository.findAll().size();

        // Create the ResultadoPartido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadoPartidoMockMvc.perform(put("/api/resultado-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadoPartido)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadoPartido in the database
        List<ResultadoPartido> resultadoPartidoList = resultadoPartidoRepository.findAll();
        assertThat(resultadoPartidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultadoPartido() throws Exception {
        // Initialize the database
        resultadoPartidoRepository.saveAndFlush(resultadoPartido);

        int databaseSizeBeforeDelete = resultadoPartidoRepository.findAll().size();

        // Delete the resultadoPartido
        restResultadoPartidoMockMvc.perform(delete("/api/resultado-partidos/{id}", resultadoPartido.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultadoPartido> resultadoPartidoList = resultadoPartidoRepository.findAll();
        assertThat(resultadoPartidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
