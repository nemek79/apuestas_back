package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.vir2al.apuestas.models.Estado;


/**
 * EstadoDTO
 */

public class EstadoDTO implements Serializable {

  private static final long serialVersionUID = 8729965678111064563L;

  private Long id;

  @NotNull
  @Size(min = 4, max = 32)
  private String descripcion;

  /**
   * Constructor de mapeo con el modelo
   * @param estado
   */
  public EstadoDTO(Estado estado) {

    this.id = estado.getId();
    this.descripcion = estado.getDescripcion();

  }

  /**
   * Mapea con el modelo de base de datos
   * @return
   */
  public Optional<Estado> asEstado() {

    Estado estado = new Estado();

    estado.setId(this.id);
    estado.setDescripcion(this.descripcion);

    return Optional.of(estado);

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

  public EstadoDTO() {
  }


}
