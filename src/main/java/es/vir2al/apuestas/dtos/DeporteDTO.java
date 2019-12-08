package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.vir2al.apuestas.models.Deporte;


/**
 * DeporteDTO
 */
public class DeporteDTO implements Serializable{

  private static final long serialVersionUID = -7088052745015797645L;

  private Long id;
  
  @NotNull
  @Size(min=4, max=32)
  private String descripcion;
  
  /**
   * Constructor de mapeo con el modelo
   * @param deporte
   */
  public DeporteDTO(Deporte deporte) {
    
    this.id = deporte.getId();
    this.descripcion = deporte.getDescripcion();

  }

  /**
   * Obtiene el modelo asciado al dto
   * @return Deporte
   */
  public Optional<Deporte> asDeporte() {

    Deporte deporte = new Deporte();

    deporte.setId(this.id);
    deporte.setDescripcion(this.descripcion);

    return Optional.of(deporte);

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

  public DeporteDTO() {
  }

  
}
