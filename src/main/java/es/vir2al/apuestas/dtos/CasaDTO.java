package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.util.Optional;

import es.vir2al.apuestas.models.Casa;

/**
 * CasaDTO
 */

public class CasaDTO implements Serializable {

  private static final long serialVersionUID = 3139387679147482307L;

  private Long id;
  private String descripcion;
  private String url;
  private Double cantidad;

  // Constructor de mapeo desde el Modelo
  public CasaDTO(Casa casa) {
    
    this.id = casa.getId();
    this.descripcion = casa.getDescripcion();
    this.url = casa.getUrl();
    this.cantidad = casa.getCantidad();

  }

  // Funcion de mapeo a Modelo
  public Optional<Casa> asCasa() {

    Casa casa = new Casa();

    casa.setId(this.id);
    casa.setDescripcion(this.descripcion);
    casa.setCantidad(this.cantidad);
    casa.setUrl(this.url);

    return Optional.of(casa);
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

  public CasaDTO() {
  }
  
}
