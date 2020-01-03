package es.vir2al.apuestas.dtos.Responses;

import java.io.Serializable;

/**
 * AvisoResponse
 */
public class AvisoResponse implements Serializable {

  private static final long serialVersionUID = -7962202823883046510L;

  private String tipo;
  private String descripcion;

  public AvisoResponse(){
    this.tipo = null;
    this.descripcion = null;
  }

  public AvisoResponse(String tipo, String descripcion) {
    this.tipo = tipo;
    this.descripcion = descripcion;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  @Override
  public String toString() {
    return "AvisoResponse [descripcion=" + descripcion + ", tipo=" + tipo + "]";
  }

}
