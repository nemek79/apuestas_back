package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.vir2al.apuestas.models.Tipster;

/**
 * TipsterDTO
 */

public class TipsterDTO implements Serializable {

  private static final long serialVersionUID = 5366543847322798303L;

  private Long id;
  
  @NotNull
  @Size(min=4, max=64)
  private String descripcion;

  @Size(max=256)
  private String url;

  @Size(max=64)
  private String telegram;

  private Boolean activo;

  /**
   * Constructor de mapeo con el modelo de base de datos
   * @param tipster
   */
  public TipsterDTO(Tipster tipster) {

    this.id = tipster.getId();
    this.descripcion = tipster.getDescripcion();
    this.url = tipster.getUrl();
    this.telegram = tipster.getTelegram();
    this.activo = tipster.getActivo();

  }

  /**
   * Mapea con el modelo de base de datos
   * @return
   */
  public Optional<Tipster> asTipster() {

    Tipster tipster = new Tipster();

    tipster.setId(this.id);
    tipster.setDescripcion(this.descripcion);
    tipster.setUrl(this.url);
    tipster.setTelegram(this.telegram);
    tipster.setActivo(this.activo);

    return Optional.of(tipster);

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

  public TipsterDTO() {
  }
  
}
