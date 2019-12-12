package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.vir2al.apuestas.models.Apuesta;

/**
 * ApuestaDTO
 */
public class ApuestaDTO implements Serializable {

  private static final long serialVersionUID = -2053803820408941887L;

  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Madrid")
  private Date fechaAlta;

  @NotNull(message = "La fecha de evento no puede estar vacía")
  @JsonFormat(pattern="yyyy-MM-dd", timezone="Europe/Madrid")
  private Date fechaEvento;

  @NotNull(message = "La apuesta debe estar asociada a una casa de apuestas")
  private CasaDTO casa;

  @NotNull(message = "La apuesta debe estar asociada a un torneo")
  private TorneoDTO torneo;

  @NotNull(message = "La apuesta debe estar asociada a un tipo de apuestas")
  private TipoDTO tipo;

  @NotNull(message = "La apuesta debe estar asociada a un tipster")
  private TipsterDTO tipster;

  @NotNull(message = "La apuesta debe estar asociada a un estado")
  private EstadoDTO estado;

  @NotNull
  @Size(min = 4, max = 128)
  private String descripcion;

  @NotNull
  @Size(min = 2, max = 64)
  private String apuesta;

  private Float cuota;

  private Float importe;

  private Float ganancia;

  private Float stake;

  /**
   * Constructor que mapea desde el modelo
   * 
   * @param apuesta
   * @throws Exception
   */
  public ApuestaDTO(Apuesta apuesta) throws Exception {

    this.id = apuesta.getId();
    this.apuesta = apuesta.getApuesta();
    this.cuota = apuesta.getCuota();
    this.descripcion = apuesta.getDescripcion();
    this.fechaAlta = apuesta.getFechaAlta();
    this.fechaEvento = apuesta.getFechaEvento();
    this.ganancia = apuesta.getGanancia();
    this.importe = apuesta.getImporte();
    this.stake = apuesta.getStake();

    this.casa = new CasaDTO(apuesta.getCasa()); // La casa de apuestas no puede ser nula en el modelo
    this.estado = new EstadoDTO(apuesta.getEstado()); // El estado no puede ser nulo en el modelo
    this.tipo = new TipoDTO(apuesta.getTipo()); // El tipo no puede ser nulo
    this.tipster = new TipsterDTO(apuesta.getTipster()); // El tipster no puede ser nulo
    this.torneo = new TorneoDTO(apuesta.getTorneo()); // El torneo no puede ser nulo

  }

  /**
   * Mapeo del dto al modelo de la base de datos
   * 
   * @return
   * @throws Exception
   */
  public Optional<Apuesta> asApuesta() throws Exception {

    Apuesta apuesta = new Apuesta();

    apuesta.setId(this.id);
    apuesta.setCuota(this.cuota);
    apuesta.setApuesta(this.apuesta);
    apuesta.setDescripcion(this.descripcion);
    apuesta.setFechaAlta(this.fechaAlta);
    apuesta.setFechaEvento(this.fechaEvento);
    apuesta.setGanancia(this.ganancia);
    apuesta.setImporte(this.importe);
    apuesta.setStake(this.stake);

    apuesta.setCasa(
        this.casa.asCasa().orElseThrow(() -> new Exception("Error al obtener la casa de apuestas: " + this.id)));
    apuesta.setEstado(
        this.estado.asEstado().orElseThrow(() -> new Exception("Error al obtener estado de la apuesta: " + this.id)));
    apuesta.setTipo(
        this.tipo.asTipo().orElseThrow(() -> new Exception("Error al obtener el tipo de la apuesta: " + this.id)));
    apuesta.setTipster(this.tipster.asTipster()
        .orElseThrow(() -> new Exception("Error al obtener el tipster de la apuesta: " + this.id)));
    apuesta.setTorneo(this.torneo.asTorneo()
        .orElseThrow(() -> new Exception("Error al obtener el torneo de la apuesta: " + this.id)));

    return Optional.of(apuesta);

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public Date getFechaEvento() {
    return fechaEvento;
  }



  public void setFechaEvento(Date fechaEvento) {
    this.fechaEvento = fechaEvento;
  }

  public CasaDTO getCasa() {
    return casa;
  }

  public void setCasa(CasaDTO casa) {
    this.casa = casa;
  }

  public TorneoDTO getTorneo() {
    return torneo;
  }

  public void setTorneo(TorneoDTO torneo) {
    this.torneo = torneo;
  }

  public TipoDTO getTipo() {
    return tipo;
  }

  public void setTipo(TipoDTO tipo) {
    this.tipo = tipo;
  }

  public TipsterDTO getTipster() {
    return tipster;
  }

  public void setTipster(TipsterDTO tipster) {
    this.tipster = tipster;
  }

  public EstadoDTO getEstado() {
    return estado;
  }

  public void setEstado(EstadoDTO estado) {
    this.estado = estado;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getApuesta() {
    return apuesta;
  }

  public void setApuesta(String apuesta) {
    this.apuesta = apuesta;
  }

  public Float getCuota() {
    return cuota;
  }

  public void setCuota(Float cuota) {
    this.cuota = cuota;
  }

  public Float getImporte() {
    return importe;
  }

  public void setImporte(Float importe) {
    this.importe = importe;
  }

  public Float getGanancia() {
    return ganancia;
  }

  public void setGanancia(Float ganancia) {
    this.ganancia = ganancia;
  }

  public Float getStake() {
    return stake;
  }

  public void setStake(Float stake) {
    this.stake = stake;
  }

  public ApuestaDTO() {
  }

  @Override
  public String toString() {
    return "ApuestaDTO [apuesta=" + apuesta + ", casa=" + casa + ", cuota=" + cuota + ", descripcion=" + descripcion
        + ", estado=" + estado + ", fechaAlta=" + fechaAlta + ", fechaEvento=" + fechaEvento + ", ganancia=" + ganancia
        + ", id=" + id + ", importe=" + importe + ", stake=" + stake + ", tipo=" + tipo + ", tipster=" + tipster
        + ", torneo=" + torneo + "]";
  }


  
}
