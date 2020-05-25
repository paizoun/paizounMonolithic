package com.paizoun.co.web.rest;

import com.paizoun.co.PaizounApp;
import com.paizoun.co.domain.Cancha;
import com.paizoun.co.repository.CanchaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CanchaResource} REST controller.
 */
@SpringBootTest(classes = PaizounApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CanchaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    @Autowired
    private CanchaRepository canchaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCanchaMockMvc;

    private Cancha cancha;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cancha createEntity(EntityManager em) {
        Cancha cancha = new Cancha()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .direccion(DEFAULT_DIRECCION)
            .ubicacion(DEFAULT_UBICACION)
            .imagen(DEFAULT_IMAGEN)
            .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .estado(DEFAULT_ESTADO);
        return cancha;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cancha createUpdatedEntity(EntityManager em) {
        Cancha cancha = new Cancha()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION)
            .ubicacion(UPDATED_UBICACION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .estado(UPDATED_ESTADO);
        return cancha;
    }

    @BeforeEach
    public void initTest() {
        cancha = createEntity(em);
    }

    @Test
    @Transactional
    public void createCancha() throws Exception {
        int databaseSizeBeforeCreate = canchaRepository.findAll().size();

        // Create the Cancha
        restCanchaMockMvc.perform(post("/api/canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cancha)))
            .andExpect(status().isCreated());

        // Validate the Cancha in the database
        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeCreate + 1);
        Cancha testCancha = canchaList.get(canchaList.size() - 1);
        assertThat(testCancha.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCancha.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testCancha.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testCancha.getUbicacion()).isEqualTo(DEFAULT_UBICACION);
        assertThat(testCancha.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testCancha.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testCancha.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testCancha.isEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createCanchaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = canchaRepository.findAll().size();

        // Create the Cancha with an existing ID
        cancha.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCanchaMockMvc.perform(post("/api/canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cancha)))
            .andExpect(status().isBadRequest());

        // Validate the Cancha in the database
        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = canchaRepository.findAll().size();
        // set the field null
        cancha.setNombre(null);

        // Create the Cancha, which fails.

        restCanchaMockMvc.perform(post("/api/canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cancha)))
            .andExpect(status().isBadRequest());

        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = canchaRepository.findAll().size();
        // set the field null
        cancha.setDireccion(null);

        // Create the Cancha, which fails.

        restCanchaMockMvc.perform(post("/api/canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cancha)))
            .andExpect(status().isBadRequest());

        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUbicacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = canchaRepository.findAll().size();
        // set the field null
        cancha.setUbicacion(null);

        // Create the Cancha, which fails.

        restCanchaMockMvc.perform(post("/api/canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cancha)))
            .andExpect(status().isBadRequest());

        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = canchaRepository.findAll().size();
        // set the field null
        cancha.setEstado(null);

        // Create the Cancha, which fails.

        restCanchaMockMvc.perform(post("/api/canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cancha)))
            .andExpect(status().isBadRequest());

        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCanchas() throws Exception {
        // Initialize the database
        canchaRepository.saveAndFlush(cancha);

        // Get all the canchaList
        restCanchaMockMvc.perform(get("/api/canchas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cancha.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION)))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCancha() throws Exception {
        // Initialize the database
        canchaRepository.saveAndFlush(cancha);

        // Get the cancha
        restCanchaMockMvc.perform(get("/api/canchas/{id}", cancha.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cancha.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.ubicacion").value(DEFAULT_UBICACION))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCancha() throws Exception {
        // Get the cancha
        restCanchaMockMvc.perform(get("/api/canchas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCancha() throws Exception {
        // Initialize the database
        canchaRepository.saveAndFlush(cancha);

        int databaseSizeBeforeUpdate = canchaRepository.findAll().size();

        // Update the cancha
        Cancha updatedCancha = canchaRepository.findById(cancha.getId()).get();
        // Disconnect from session so that the updates on updatedCancha are not directly saved in db
        em.detach(updatedCancha);
        updatedCancha
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .direccion(UPDATED_DIRECCION)
            .ubicacion(UPDATED_UBICACION)
            .imagen(UPDATED_IMAGEN)
            .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .estado(UPDATED_ESTADO);

        restCanchaMockMvc.perform(put("/api/canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCancha)))
            .andExpect(status().isOk());

        // Validate the Cancha in the database
        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeUpdate);
        Cancha testCancha = canchaList.get(canchaList.size() - 1);
        assertThat(testCancha.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCancha.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testCancha.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testCancha.getUbicacion()).isEqualTo(UPDATED_UBICACION);
        assertThat(testCancha.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testCancha.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testCancha.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testCancha.isEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingCancha() throws Exception {
        int databaseSizeBeforeUpdate = canchaRepository.findAll().size();

        // Create the Cancha

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCanchaMockMvc.perform(put("/api/canchas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cancha)))
            .andExpect(status().isBadRequest());

        // Validate the Cancha in the database
        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCancha() throws Exception {
        // Initialize the database
        canchaRepository.saveAndFlush(cancha);

        int databaseSizeBeforeDelete = canchaRepository.findAll().size();

        // Delete the cancha
        restCanchaMockMvc.perform(delete("/api/canchas/{id}", cancha.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cancha> canchaList = canchaRepository.findAll();
        assertThat(canchaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
