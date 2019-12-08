package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.vir2al.apuestas.models.Tipo;

/**
 * TipoDTO
 */

public class TipoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  @NotNull
  @Size(min=4, max=32)
  private String descripcion;

  /**
   * Constructor de mapeo con el modelo
   * @param tipo
   */
  public TipoDTO(Tipo tipo) {

    this.id = tipo.getId();
    this.descripcion = tipo.getDescripcion();

  }

  /**
   * Mapea con el modelo de base de datos
   * @return
   */
  public Optional<Tipo> asTipo() {

    Tipo tipo = new Tipo();

    tipo.setId(this.id);
    tipo.setDescripcion(this.descripcion);

    return Optional.of(tipo);

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

  public TipoDTO() {
  }
  
}
