package es.vir2al.apuestas.dtos.Request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * ApuestaFilterRequest
 */

public class ApuestaFilterRequest {

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date fechaIni;
  
  @DateTimeFormat(pattern="yyyy-MM-dd")
  private Date fechaFin;

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
