package com.paizoun.co.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoCancha.
 */
@Entity
@Table(name = "tipo_cancha")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoCancha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre_tipo", nullable = false)
    private String nombreTipo;

    @NotNull
    @Column(name = "cantidad_jugadores", nullable = false)
    private Integer cantidadJugadores;

    @OneToMany(mappedBy = "tipoCancha")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cancha> canchas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public TipoCancha nombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
        return this;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public Integer getCantidadJugadores() {
        return cantidadJugadores;
    }

    public TipoCancha cantidadJugadores(Integer cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
        return this;
    }

    public void setCantidadJugadores(Integer cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }

    public Set<Cancha> getCanchas() {
        return canchas;
    }

    public TipoCancha canchas(Set<Cancha> canchas) {
        this.canchas = canchas;
        return this;
    }

    public TipoCancha addCancha(Cancha cancha) {
        this.canchas.add(cancha);
        cancha.setTipoCancha(this);
        return this;
    }

    public TipoCancha removeCancha(Cancha cancha) {
        this.canchas.remove(cancha);
        cancha.setTipoCancha(null);
        return this;
    }

    public void setCanchas(Set<Cancha> canchas) {
        this.canchas = canchas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoCancha)) {
            return false;
        }
        return id != null && id.equals(((TipoCancha) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoCancha{" +
            "id=" + getId() +
            ", nombreTipo='" + getNombreTipo() + "'" +
            ", cantidadJugadores=" + getCantidadJugadores() +
            "}";
    }
}
