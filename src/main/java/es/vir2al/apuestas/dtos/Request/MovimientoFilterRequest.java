package es.vir2al.apuestas.dtos.Request;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import es.vir2al.apuestas.dtos.CasaDTO;


/**
 * MovimientoRequest
 */

public class MovimientoFilterRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private CasaDTO casa;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date fechaIni;
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date fechaFin;

  public CasaDTO getCasa() {
    return casa;
  }

  public void setCasa(CasaDTO casa) {
    this.casa = casa;
  }

  public Date getFechaIni() {
    return fechaIni;
  }

  public void setFechaIni(Date fechaIni) {
    this.fechaIni = fechaIni;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }
  
}
