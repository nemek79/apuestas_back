package es.vir2al.apuestas.dtos;

import java.io.Serializable;


/**
 * DataResponse
 */

public class DataResponse implements Serializable {

  private static final long serialVersionUID = 7907095120076681412L;

  private Object data;
  private String mensaje;

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public DataResponse() {
  }
  
  
}
