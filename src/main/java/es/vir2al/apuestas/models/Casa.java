package es.vir2al.apuestas.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa las casas de apuestas
 */
@Entity
@Table(name="t_casas")
public class Casa implements Serializable {

  private static final long serialVersionUID = 3012487047470585006L;

  @Id
  private Long   id;

  @NotNull
  @Size(min=4, max=32)
  @Column(name="descripcion", nullable = false, unique = true)
  private String descripcion;

  @NotNull
  @Size(min=12, max=128)
  @Column(name="url", nullable = false, unique = true)
  private String url;

  @Column(name="cantidad", nullable = false, unique = true)
  private Double cantidad;

  public Casa() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Double getCantidad() {
    return cantidad;
  }

  public void setCantidad(Double cantidad) {
    this.cantidad = cantidad;
  }
  
}
