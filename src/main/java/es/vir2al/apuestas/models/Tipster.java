package es.vir2al.apuestas.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa los Tipster
 */

@Entity
@Table(name="t_tipsters")
public class Tipster implements Serializable{

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;

  @NotNull
  @Size(min=4, max=64)
  @Column(name="descripcion",nullable = false,unique = true)
  private String descripcion;

  @Size(max=256)
  @Column(name="url",nullable = true,unique = false)
  private String url;

  @Size(max=64)
  @Column(name="telegram",nullable = true,unique = false)
  private String telegram;

  @Column(name="activo")
  private Boolean activo;

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

  public String getTelegram() {
    return telegram;
  }

  public void setTelegram(String telegram) {
    this.telegram = telegram;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }
  
}
