package com.paizoun.co.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A InvitacionPartido.
 */
@Entity
@Table(name = "invitacion_partido")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InvitacionPartido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_hora_creacion", nullable = false)
    private Instant fechaHoraCreacion;

    @NotNull
    @Column(name = "fecha_hora_partido", nullable = false)
    private Instant fechaHoraPartido;

    @NotNull
    @Column(name = "equipo_a_confirmado", nullable = false)
    private Boolean equipoAConfirmado;

    @NotNull
    @Column(name = "equipo_b_confirmado", nullable = false)
    private Boolean equipoBConfirmado;

    @OneToOne
    @JoinColumn(unique = true)
    private Cancha cancha;

    @OneToOne
    @JoinColumn(unique = true)
    private Equipo equipoA;

    @OneToOne
    @JoinColumn(unique = true)
    private Equipo equipoB;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public InvitacionPartido fechaHoraCreacion(Instant fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
        return this;
    }

    public void setFechaHoraCreacion(Instant fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Instant getFechaHoraPartido() {
        return fechaHoraPartido;
    }

    public InvitacionPartido fechaHoraPartido(Instant fechaHoraPartido) {
        this.fechaHoraPartido = fechaHoraPartido;
        return this;
    }

    public void setFechaHoraPartido(Instant fechaHoraPartido) {
        this.fechaHoraPartido = fechaHoraPartido;
    }

    public Boolean isEquipoAConfirmado() {
        return equipoAConfirmado;
    }

    public InvitacionPartido equipoAConfirmado(Boolean equipoAConfirmado) {
        this.equipoAConfirmado = equipoAConfirmado;
        return this;
    }

    public void setEquipoAConfirmado(Boolean equipoAConfirmado) {
        this.equipoAConfirmado = equipoAConfirmado;
    }

    public Boolean isEquipoBConfirmado() {
        return equipoBConfirmado;
    }

    public InvitacionPartido equipoBConfirmado(Boolean equipoBConfirmado) {
        this.equipoBConfirmado = equipoBConfirmado;
        return this;
    }

    public void setEquipoBConfirmado(Boolean equipoBConfirmado) {
        this.equipoBConfirmado = equipoBConfirmado;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public InvitacionPartido cancha(Cancha cancha) {
        this.cancha = cancha;
        return this;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    public Equipo getEquipoA() {
        return equipoA;
    }

    public InvitacionPartido equipoA(Equipo equipo) {
        this.equipoA = equipo;
        return this;
    }

    public void setEquipoA(Equipo equipo) {
        this.equipoA = equipo;
    }

    public Equipo getEquipoB() {
        return equipoB;
    }

    public InvitacionPartido equipoB(Equipo equipo) {
        this.equipoB = equipo;
        return this;
    }

    public void setEquipoB(Equipo equipo) {
        this.equipoB = equipo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvitacionPartido)) {
            return false;
        }
        return id != null && id.equals(((InvitacionPartido) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InvitacionPartido{" +
            "id=" + getId() +
            ", fechaHoraCreacion='" + getFechaHoraCreacion() + "'" +
            ", fechaHoraPartido='" + getFechaHoraPartido() + "'" +
            ", equipoAConfirmado='" + isEquipoAConfirmado() + "'" +
            ", equipoBConfirmado='" + isEquipoBConfirmado() + "'" +
            "}";
    }
}
