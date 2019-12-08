package es.vir2al.apuestas.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa una apuesta
 */
@Entity
@Table(name="t_apuestas")
public class Apuesta implements Serializable{

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

	@Column(name="fecha_alta")
	@Temporal(TemporalType.DATE)
  private Date fechaAlta;

  @NotNull(message="La fecha de evento no puede estar vacía")
	@Column(name="fecha_evento")
	@Temporal(TemporalType.DATE)
  private Date fechaEvento;

  @NotNull(message="La apuesta debe estar asociada a una casa de apuestas")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="casa_id")
  private Casa casa;
  
  @NotNull(message="La apuesta debe estar asociada a un torneo")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="torneo_id")
  private Torneo torneo;
  
  @NotNull(message="La apuesta debe estar asociada a un tipo de apuestas")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="tipo_id")
  private Tipo tipo;
  
  @NotNull(message="La apuesta debe estar asociada a un tipster")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="tipster_id")
  private Tipster tipster;
  
  @NotNull(message="La apuesta debe estar asociada a un estado")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="estado_id")
  private Estado estado;
  
  @NotNull
  @Size(min=4, max=128)
  @Column(name="descripcion",nullable = false,unique = false)
  private String descripcion;

  @NotNull
  @Size(min=2, max=64)
  @Column(name="apuesta",nullable = false,unique = false)
  private String apuesta;

  @Column(name="cuota",nullable = false,unique = false)
  private Float cuota;

  @Column(name="importe",nullable = false,unique = false)
  private Float importe;

  @Column(name="ganancia",nullable = true,unique = false)
  private Float ganancia;

  @Column(name="stake",nullable = false,unique = false)
  private Float stake;

  @PrePersist
  public void PrePersist() {
    this.fechaAlta = new Date();
  }

  public Apuesta() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public Date getFechaEvento() {
    return fechaEvento;
  }

  public void setFechaEvento(Date fechaEvento) {
    this.fechaEvento = fechaEvento;
  }

  public Casa getCasa() {
    return casa;
  }

  public void setCasa(Casa casa) {
    this.casa = casa;
  }

  public Torneo getTorneo() {
    return torneo;
  }

  public void setTorneo(Torneo torneo) {
    this.torneo = torneo;
  }

  public Tipo getTipo() {
    return tipo;
  }

  public void setTipo(Tipo tipo) {
    this.tipo = tipo;
  }

  public Tipster getTipster() {
    return tipster;
  }

  public void setTipster(Tipster tipster) {
    this.tipster = tipster;
  }

  public Estado getEstado() {
    return estado;
  }

  public void setEstado(Estado estado) {
    this.estado = estado;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getApuesta() {
    return apuesta;
  }

  public void setApuesta(String apuesta) {
    this.apuesta = apuesta;
  }

  public Float getCuota() {
    return cuota;
  }

  public void setCuota(Float cuota) {
    this.cuota = cuota;
  }

  public Float getImporte() {
    return importe;
  }

  public void setImporte(Float importe) {
    this.importe = importe;
  }

  public Float getGanancia() {
    return ganancia;
  }

  public void setGanancia(Float ganancia) {
    this.ganancia = ganancia;
  }

  public Float getStake() {
    return stake;
  }

  public void setStake(Float stake) {
    this.stake = stake;
  }

  
  
}
