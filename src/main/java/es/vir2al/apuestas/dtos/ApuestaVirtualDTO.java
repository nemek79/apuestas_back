package es.vir2al.apuestas.dtos;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import es.vir2al.apuestas.constantes.ConstApp;
import es.vir2al.apuestas.models.ApuestaVirtual;

/**
 * ApuestaVirtual
 */
public class ApuestaVirtualDTO implements Serializable {

  private static final long serialVersionUID = -6099767994661234605L;

  SimpleDateFormat sdf = new SimpleDateFormat(ConstApp.INPUT_DATE_FORMAT,Locale.getDefault());

  private Long id;

  private Date fechaAlta;

  @NotNull(message = "La fecha de evento no puede estar vacía")
  private Date fechaEvento;

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

  private Long apuestaId;

  /**
   * Constructor que mapea desde el modelo
   * 
   * @param apuesta
   * @throws Exception
   */
  public ApuestaVirtualDTO(ApuestaVirtual apuesta) throws Exception {

    this.id = apuesta.getId();
    this.apuesta = apuesta.getApuesta();
    this.cuota = apuesta.getCuota();
    this.descripcion = apuesta.getDescripcion();
    this.fechaAlta = apuesta.getFechaAlta();
    this.fechaEvento = apuesta.getFechaEvento();
    this.ganancia = apuesta.getGanancia();
    this.importe = apuesta.getImporte();
    this.stake = apuesta.getStake();

    this.estado = new EstadoDTO(apuesta.getEstado()); // El estado no puede ser nulo en el modelo
    this.tipo = new TipoDTO(apuesta.getTipo()); // El tipo no puede ser nulo
    this.tipster = new TipsterDTO(apuesta.getTipster()); // El tipster no puede ser nulo
    this.torneo = new TorneoDTO(apuesta.getTorneo()); // El torneo no puede ser nulo

    this.apuestaId = apuesta.getApuestaId();

  }

  /**
   * Constructor que mapea desde el modelo
   * 
   * @param apuesta
   * @throws Exception
   */
  public ApuestaVirtualDTO(ApuestaDTO apuesta) throws Exception {

    this.id = null;
    this.apuesta = apuesta.getApuesta();
    this.cuota = apuesta.getCuota();
    this.descripcion = apuesta.getDescripcion();
    this.fechaAlta = apuesta.getFechaAlta();
    this.fechaEvento = apuesta.getFechaEvento();
    this.ganancia = apuesta.getGanancia();
    this.importe = apuesta.getImporte();
    this.stake = apuesta.getStake();

    this.estado = apuesta.getEstado(); // El estado no puede ser nulo en el modelo
    this.tipo = apuesta.getTipo(); // El tipo no puede ser nulo
    this.tipster = apuesta.getTipster(); // El tipster no puede ser nulo
    this.torneo = apuesta.getTorneo(); // El torneo no puede ser nulo

    this.apuestaId = apuesta.getId();

  }

  /**
   * Mapeo del dto al modelo de la base de datos
   * 
   * @return
   * @throws Exception
   */
  public Optional<ApuestaVirtual> asApuestaVirtual() throws Exception {

    ApuestaVirtual apuesta = new ApuestaVirtual();

    apuesta.setId(this.id);
    apuesta.setCuota(this.cuota);
    apuesta.setApuesta(this.apuesta);
    apuesta.setDescripcion(this.descripcion);
    apuesta.setFechaAlta(this.fechaAlta);
    apuesta.setFechaEvento(this.fechaEvento);
    apuesta.setGanancia(this.ganancia);
    apuesta.setImporte(this.importe);
    apuesta.setStake(this.stake);

    apuesta.setEstado(
        this.estado.asEstado().orElseThrow(() -> new Exception("Error al obtener estado de la apuesta: " + this.id)));
    apuesta.setTipo(
        this.tipo.asTipo().orElseThrow(() -> new Exception("Error al obtener el tipo de la apuesta: " + this.id)));
    apuesta.setTipster(this.tipster.asTipster()
        .orElseThrow(() -> new Exception("Error al obtener el tipster de la apuesta: " + this.id)));
    apuesta.setTorneo(this.torneo.asTorneo()
        .orElseThrow(() -> new Exception("Error al obtener el torneo de la apuesta: " + this.id)));

    apuesta.setApuestaId(this.apuestaId);

    return Optional.of(apuesta);

  }

  public void setFromApuesta(ApuestaDTO apuesta) throws Exception {

    this.apuesta = apuesta.getApuesta();
    this.cuota = apuesta.getCuota();
    this.descripcion = apuesta.getDescripcion();
    this.fechaAlta = apuesta.getFechaAlta();
    this.fechaEvento = apuesta.getFechaEvento();
    this.ganancia = apuesta.getGanancia();
    this.importe = apuesta.getImporte();
    this.stake = apuesta.getStake();

    this.estado = apuesta.getEstado(); // El estado no puede ser nulo en el modelo
    this.tipo = apuesta.getTipo(); // El tipo no puede ser nulo
    this.tipster = apuesta.getTipster(); // El tipster no puede ser nulo
    this.torneo = apuesta.getTorneo(); // El torneo no puede ser nulo

    this.apuestaId = apuesta.getId();

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

  public ApuestaVirtualDTO() {
  }

  public Long getApuestaId() {
    return apuestaId;
  }

  public void setApuestaId(Long apuestaId) {
    this.apuestaId = apuestaId;
  }

}
