package es.vir2al.apuestas.dtos.Responses;

import java.util.List;

import es.vir2al.apuestas.dtos.ApuestaDTO;

/**
 * InfoDiaResponse
 */

public class InfoDiaResponse {

  private List<ApuestaDTO> lstApuestas;

  public List<ApuestaDTO> getLstApuestas() {
    return lstApuestas;
  }

  public void setLstApuestas(List<ApuestaDTO> lstApuestas) {
    this.lstApuestas = lstApuestas;
  }

  
}
