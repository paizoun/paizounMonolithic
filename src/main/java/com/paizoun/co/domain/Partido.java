package com.paizoun.co.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Partido.
 */
@Entity
@Table(name = "partido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private Instant fechaHora;

    @Column(name = "finalizado")
    private Boolean finalizado;

    @OneToOne
    @JoinColumn(unique = true)
    private InvitacionPartido invitacionPartido;

    @ManyToOne
    @JsonIgnoreProperties("partidos")
    private EstadoPartido estadoPartido;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }

    public Partido fechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
        return this;
    }

    public void setFechaHora(Instant fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Boolean isFinalizado() {
        return finalizado;
    }

    public Partido finalizado(Boolean finalizado) {
        this.finalizado = finalizado;
        return this;
    }

    public void setFinalizado(Boolean finalizado) {
        this.finalizado = finalizado;
    }

    public InvitacionPartido getInvitacionPartido() {
        return invitacionPartido;
    }

    public Partido invitacionPartido(InvitacionPartido invitacionPartido) {
        this.invitacionPartido = invitacionPartido;
        return this;
    }

    public void setInvitacionPartido(InvitacionPartido invitacionPartido) {
        this.invitacionPartido = invitacionPartido;
    }

    public EstadoPartido getEstadoPartido() {
        return estadoPartido;
    }

    public Partido estadoPartido(EstadoPartido estadoPartido) {
        this.estadoPartido = estadoPartido;
        return this;
    }

    public void setEstadoPartido(EstadoPartido estadoPartido) {
        this.estadoPartido = estadoPartido;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partido)) {
            return false;
        }
        return id != null && id.equals(((Partido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Partido{" +
            "id=" + getId() +
            ", fechaHora='" + getFechaHora() + "'" +
            ", finalizado='" + isFinalizado() + "'" +
            "}";
    }
}
