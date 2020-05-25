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
 * A EstadoPartido.
 */
@Entity
@Table(name = "estado_partido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstadoPartido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @OneToMany(mappedBy = "estadoPartido")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Partido> partidos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public EstadoPartido estado(String estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<Partido> getPartidos() {
        return partidos;
    }

    public EstadoPartido partidos(Set<Partido> partidos) {
        this.partidos = partidos;
        return this;
    }

    public EstadoPartido addPartido(Partido partido) {
        this.partidos.add(partido);
        partido.setEstadoPartido(this);
        return this;
    }

    public EstadoPartido removePartido(Partido partido) {
        this.partidos.remove(partido);
        partido.setEstadoPartido(null);
        return this;
    }

    public void setPartidos(Set<Partido> partidos) {
        this.partidos = partidos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoPartido)) {
            return false;
        }
        return id != null && id.equals(((EstadoPartido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstadoPartido{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
