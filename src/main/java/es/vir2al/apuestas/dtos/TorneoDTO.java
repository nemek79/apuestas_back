package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.vir2al.apuestas.models.Torneo;

/**
 * TorneoDTO
 */

public class TorneoDTO implements Serializable {

  private static final long serialVersionUID = -4660164119175559266L;

  private Long id;

  @NotNull
  @Size(min=4, max=32)
  private String descripcion;

  @NotNull(message="El torneo debe estar asociado a un deporte")
  private DeporteDTO deporte;

  /**
   * Constructor de mapeo con el modelo de base de datos
   * @param torneo
   */
  public TorneoDTO(Torneo torneo) throws Exception {
    
    this.id = torneo.getId();
    this.descripcion = torneo.getDescripcion();
    // El deporte no puede ser nulo por modelo
    // pero por seguridad controlamos el error
    this.deporte = new DeporteDTO(torneo.getDeporte());

  }
  
  /**
   * Mapeo del dto al modelo de la base de datos
   * @return
   * @throws Exception
   */
  public Optional<Torneo> asTorneo() throws Exception {

    Torneo torneo = new Torneo();

    torneo.setId(this.id);
    torneo.setDescripcion(this.descripcion);

    torneo.setDeporte(this.deporte.asDeporte().orElseThrow(() -> new Exception("Error al obtener el deporte")));

    return Optional.of(torneo);

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

  public DeporteDTO getDeporte() {
    return deporte;
  }

  public void setDeporte(DeporteDTO deporte) {
    this.deporte = deporte;
  }

  public TorneoDTO() {
  }
  
}
