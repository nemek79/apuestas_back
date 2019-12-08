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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa movimientos en casas de apuestas
 */

@Entity
@Table(name="t_movimientos")
public class Movimiento implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @Size(max=64)
  @Column(name="observacion",nullable = true,unique = false)
  private String observacion;

  @NotNull(message="El movimiento debe estar asociado a una casa de apuestas")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="casa_id")
  private Casa casa;
  
  @NotNull(message="El movimiento debe tener una fecha determinada")
	@Column(name="fecha")
	@Temporal(TemporalType.DATE)
  private Date fecha;
  
  @Column(name="importe",nullable = false,unique = false)
  private Double importe;
  
  @Column(name="tipo",nullable = false,unique = false)
  private String tipo; // Ingresar valor desde constante

  public Movimiento() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getObservacion() {
    return observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public Casa getCasa() {
    return casa;
  }

  public void setCasa(Casa casa) {
    this.casa = casa;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Double getImporte() {
    return importe;
  }

  public void setImporte(Double importe) {
    this.importe = importe;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
  
}
