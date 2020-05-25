package com.paizoun.co.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ResultadoPartido.
 */
@Entity
@Table(name = "resultado_partido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultadoPartido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "goles_eqiopo_a", nullable = false)
    private Integer golesEqiopoA;

    @NotNull
    @Column(name = "goles_eqiopo_b", nullable = false)
    private Integer golesEqiopoB;

    @Column(name = "gano_equipo_a")
    private Boolean ganoEquipoA;

    @Column(name = "gano_equipo_b")
    private Boolean ganoEquipoB;

    @OneToOne
    @JoinColumn(unique = true)
    private Partido partido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGolesEqiopoA() {
        return golesEqiopoA;
    }

    public ResultadoPartido golesEqiopoA(Integer golesEqiopoA) {
        this.golesEqiopoA = golesEqiopoA;
        return this;
    }

    public void setGolesEqiopoA(Integer golesEqiopoA) {
        this.golesEqiopoA = golesEqiopoA;
    }

    public Integer getGolesEqiopoB() {
        return golesEqiopoB;
    }

    public ResultadoPartido golesEqiopoB(Integer golesEqiopoB) {
        this.golesEqiopoB = golesEqiopoB;
        return this;
    }

    public void setGolesEqiopoB(Integer golesEqiopoB) {
        this.golesEqiopoB = golesEqiopoB;
    }

    public Boolean isGanoEquipoA() {
        return ganoEquipoA;
    }

    public ResultadoPartido ganoEquipoA(Boolean ganoEquipoA) {
        this.ganoEquipoA = ganoEquipoA;
        return this;
    }

    public void setGanoEquipoA(Boolean ganoEquipoA) {
        this.ganoEquipoA = ganoEquipoA;
    }

    public Boolean isGanoEquipoB() {
        return ganoEquipoB;
    }

    public ResultadoPartido ganoEquipoB(Boolean ganoEquipoB) {
        this.ganoEquipoB = ganoEquipoB;
        return this;
    }

    public void setGanoEquipoB(Boolean ganoEquipoB) {
        this.ganoEquipoB = ganoEquipoB;
    }

    public Partido getPartido() {
        return partido;
    }

    public ResultadoPartido partido(Partido partido) {
        this.partido = partido;
        return this;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultadoPartido)) {
            return false;
        }
        return id != null && id.equals(((ResultadoPartido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResultadoPartido{" +
            "id=" + getId() +
            ", golesEqiopoA=" + getGolesEqiopoA() +
            ", golesEqiopoB=" + getGolesEqiopoB() +
            ", ganoEquipoA='" + isGanoEquipoA() + "'" +
            ", ganoEquipoB='" + isGanoEquipoB() + "'" +
            "}";
    }
}
