package es.vir2al.apuestas.dtos;

import java.io.Serializable;



/**
 * ErrorResponse
 */

public class ErrorResponse implements Serializable {

  private static final long serialVersionUID = 3398338952891540459L;

  private String mensaje;
  private String descripcion;

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public ErrorResponse() {
  }
  
}
