package com.paizoun.co.web.rest;

import com.paizoun.co.PaizounApp;
import com.paizoun.co.domain.InvitacionPartido;
import com.paizoun.co.repository.InvitacionPartidoRepository;

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
 * Integration tests for the {@link InvitacionPartidoResource} REST controller.
 */
@SpringBootTest(classes = PaizounApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class InvitacionPartidoResourceIT {

    private static final Instant DEFAULT_FECHA_HORA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_HORA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_HORA_PARTIDO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_HORA_PARTIDO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_EQUIPO_A_CONFIRMADO = false;
    private static final Boolean UPDATED_EQUIPO_A_CONFIRMADO = true;

    private static final Boolean DEFAULT_EQUIPO_B_CONFIRMADO = false;
    private static final Boolean UPDATED_EQUIPO_B_CONFIRMADO = true;

    @Autowired
    private InvitacionPartidoRepository invitacionPartidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvitacionPartidoMockMvc;

    private InvitacionPartido invitacionPartido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitacionPartido createEntity(EntityManager em) {
        InvitacionPartido invitacionPartido = new InvitacionPartido()
            .fechaHoraCreacion(DEFAULT_FECHA_HORA_CREACION)
            .fechaHoraPartido(DEFAULT_FECHA_HORA_PARTIDO)
            .equipoAConfirmado(DEFAULT_EQUIPO_A_CONFIRMADO)
            .equipoBConfirmado(DEFAULT_EQUIPO_B_CONFIRMADO);
        return invitacionPartido;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvitacionPartido createUpdatedEntity(EntityManager em) {
        InvitacionPartido invitacionPartido = new InvitacionPartido()
            .fechaHoraCreacion(UPDATED_FECHA_HORA_CREACION)
            .fechaHoraPartido(UPDATED_FECHA_HORA_PARTIDO)
            .equipoAConfirmado(UPDATED_EQUIPO_A_CONFIRMADO)
            .equipoBConfirmado(UPDATED_EQUIPO_B_CONFIRMADO);
        return invitacionPartido;
    }

    @BeforeEach
    public void initTest() {
        invitacionPartido = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvitacionPartido() throws Exception {
        int databaseSizeBeforeCreate = invitacionPartidoRepository.findAll().size();

        // Create the InvitacionPartido
        restInvitacionPartidoMockMvc.perform(post("/api/invitacion-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionPartido)))
            .andExpect(status().isCreated());

        // Validate the InvitacionPartido in the database
        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeCreate + 1);
        InvitacionPartido testInvitacionPartido = invitacionPartidoList.get(invitacionPartidoList.size() - 1);
        assertThat(testInvitacionPartido.getFechaHoraCreacion()).isEqualTo(DEFAULT_FECHA_HORA_CREACION);
        assertThat(testInvitacionPartido.getFechaHoraPartido()).isEqualTo(DEFAULT_FECHA_HORA_PARTIDO);
        assertThat(testInvitacionPartido.isEquipoAConfirmado()).isEqualTo(DEFAULT_EQUIPO_A_CONFIRMADO);
        assertThat(testInvitacionPartido.isEquipoBConfirmado()).isEqualTo(DEFAULT_EQUIPO_B_CONFIRMADO);
    }

    @Test
    @Transactional
    public void createInvitacionPartidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invitacionPartidoRepository.findAll().size();

        // Create the InvitacionPartido with an existing ID
        invitacionPartido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvitacionPartidoMockMvc.perform(post("/api/invitacion-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionPartido)))
            .andExpect(status().isBadRequest());

        // Validate the InvitacionPartido in the database
        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaHoraCreacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitacionPartidoRepository.findAll().size();
        // set the field null
        invitacionPartido.setFechaHoraCreacion(null);

        // Create the InvitacionPartido, which fails.

        restInvitacionPartidoMockMvc.perform(post("/api/invitacion-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionPartido)))
            .andExpect(status().isBadRequest());

        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaHoraPartidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitacionPartidoRepository.findAll().size();
        // set the field null
        invitacionPartido.setFechaHoraPartido(null);

        // Create the InvitacionPartido, which fails.

        restInvitacionPartidoMockMvc.perform(post("/api/invitacion-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionPartido)))
            .andExpect(status().isBadRequest());

        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEquipoAConfirmadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitacionPartidoRepository.findAll().size();
        // set the field null
        invitacionPartido.setEquipoAConfirmado(null);

        // Create the InvitacionPartido, which fails.

        restInvitacionPartidoMockMvc.perform(post("/api/invitacion-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionPartido)))
            .andExpect(status().isBadRequest());

        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEquipoBConfirmadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = invitacionPartidoRepository.findAll().size();
        // set the field null
        invitacionPartido.setEquipoBConfirmado(null);

        // Create the InvitacionPartido, which fails.

        restInvitacionPartidoMockMvc.perform(post("/api/invitacion-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionPartido)))
            .andExpect(status().isBadRequest());

        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInvitacionPartidos() throws Exception {
        // Initialize the database
        invitacionPartidoRepository.saveAndFlush(invitacionPartido);

        // Get all the invitacionPartidoList
        restInvitacionPartidoMockMvc.perform(get("/api/invitacion-partidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invitacionPartido.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaHoraCreacion").value(hasItem(DEFAULT_FECHA_HORA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaHoraPartido").value(hasItem(DEFAULT_FECHA_HORA_PARTIDO.toString())))
            .andExpect(jsonPath("$.[*].equipoAConfirmado").value(hasItem(DEFAULT_EQUIPO_A_CONFIRMADO.booleanValue())))
            .andExpect(jsonPath("$.[*].equipoBConfirmado").value(hasItem(DEFAULT_EQUIPO_B_CONFIRMADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getInvitacionPartido() throws Exception {
        // Initialize the database
        invitacionPartidoRepository.saveAndFlush(invitacionPartido);

        // Get the invitacionPartido
        restInvitacionPartidoMockMvc.perform(get("/api/invitacion-partidos/{id}", invitacionPartido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invitacionPartido.getId().intValue()))
            .andExpect(jsonPath("$.fechaHoraCreacion").value(DEFAULT_FECHA_HORA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaHoraPartido").value(DEFAULT_FECHA_HORA_PARTIDO.toString()))
            .andExpect(jsonPath("$.equipoAConfirmado").value(DEFAULT_EQUIPO_A_CONFIRMADO.booleanValue()))
            .andExpect(jsonPath("$.equipoBConfirmado").value(DEFAULT_EQUIPO_B_CONFIRMADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInvitacionPartido() throws Exception {
        // Get the invitacionPartido
        restInvitacionPartidoMockMvc.perform(get("/api/invitacion-partidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvitacionPartido() throws Exception {
        // Initialize the database
        invitacionPartidoRepository.saveAndFlush(invitacionPartido);

        int databaseSizeBeforeUpdate = invitacionPartidoRepository.findAll().size();

        // Update the invitacionPartido
        InvitacionPartido updatedInvitacionPartido = invitacionPartidoRepository.findById(invitacionPartido.getId()).get();
        // Disconnect from session so that the updates on updatedInvitacionPartido are not directly saved in db
        em.detach(updatedInvitacionPartido);
        updatedInvitacionPartido
            .fechaHoraCreacion(UPDATED_FECHA_HORA_CREACION)
            .fechaHoraPartido(UPDATED_FECHA_HORA_PARTIDO)
            .equipoAConfirmado(UPDATED_EQUIPO_A_CONFIRMADO)
            .equipoBConfirmado(UPDATED_EQUIPO_B_CONFIRMADO);

        restInvitacionPartidoMockMvc.perform(put("/api/invitacion-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvitacionPartido)))
            .andExpect(status().isOk());

        // Validate the InvitacionPartido in the database
        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeUpdate);
        InvitacionPartido testInvitacionPartido = invitacionPartidoList.get(invitacionPartidoList.size() - 1);
        assertThat(testInvitacionPartido.getFechaHoraCreacion()).isEqualTo(UPDATED_FECHA_HORA_CREACION);
        assertThat(testInvitacionPartido.getFechaHoraPartido()).isEqualTo(UPDATED_FECHA_HORA_PARTIDO);
        assertThat(testInvitacionPartido.isEquipoAConfirmado()).isEqualTo(UPDATED_EQUIPO_A_CONFIRMADO);
        assertThat(testInvitacionPartido.isEquipoBConfirmado()).isEqualTo(UPDATED_EQUIPO_B_CONFIRMADO);
    }

    @Test
    @Transactional
    public void updateNonExistingInvitacionPartido() throws Exception {
        int databaseSizeBeforeUpdate = invitacionPartidoRepository.findAll().size();

        // Create the InvitacionPartido

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvitacionPartidoMockMvc.perform(put("/api/invitacion-partidos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invitacionPartido)))
            .andExpect(status().isBadRequest());

        // Validate the InvitacionPartido in the database
        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvitacionPartido() throws Exception {
        // Initialize the database
        invitacionPartidoRepository.saveAndFlush(invitacionPartido);

        int databaseSizeBeforeDelete = invitacionPartidoRepository.findAll().size();

        // Delete the invitacionPartido
        restInvitacionPartidoMockMvc.perform(delete("/api/invitacion-partidos/{id}", invitacionPartido.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvitacionPartido> invitacionPartidoList = invitacionPartidoRepository.findAll();
        assertThat(invitacionPartidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
