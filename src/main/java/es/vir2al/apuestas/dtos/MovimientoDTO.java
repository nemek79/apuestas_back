package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.vir2al.apuestas.models.Movimiento;

/**
 * MovimientoDTO
 */
public class MovimientoDTO implements Serializable {

  private static final long serialVersionUID = 8344602261732981268L;

  private Long id;

  @Size(max=64)
  private String observacion;

  @NotNull(message="El movimiento debe estar asociado a una casa de apuestas")
  private CasaDTO casa;
  
  @NotNull(message="El movimiento debe tener una fecha determinada")
  private Date fecha;

  private Double importe;

  private String tipo; // Ingresar valor desde constante

  /**
   * Constructor para mapear con el modelo de la base de datos
   * @param movimiento
   * @throws Exception
   */
  public MovimientoDTO(Movimiento movimiento) throws Exception {

    this.id = movimiento.getId();
    this.observacion = movimiento.getObservacion();
    this.fecha = movimiento.getFecha();
    this.importe = movimiento.getImporte();
    this.tipo = movimiento.getTipo();

    this.casa = new CasaDTO(movimiento.getCasa());

  }

  /**
   * Mapeo del dto al modelo de la base de datos
   * @return
   * @throws Exception
   */
  public Optional<Movimiento> asMovimiento() throws Exception {

    Movimiento movimiento = new Movimiento();

    movimiento.setId(this.id);
    movimiento.setObservacion(this.observacion);
    movimiento.setFecha(this.fecha);
    movimiento.setImporte(this.importe);
    movimiento.setTipo(this.tipo);

    movimiento.setCasa(this.casa.asCasa().orElseThrow(() -> new Exception("Error al obtener la casa de apuestas")));

    return Optional.of(movimiento);
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

  public CasaDTO getCasa() {
    return casa;
  }

  public void setCasa(CasaDTO casa) {
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

  public MovimientoDTO() {
  }
  
}
