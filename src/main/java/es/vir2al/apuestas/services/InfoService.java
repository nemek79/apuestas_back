package es.vir2al.apuestas.services;

import java.util.List;

import es.vir2al.apuestas.dtos.Responses.AvisoResponse;
import es.vir2al.apuestas.dtos.Responses.InfoDiaResponse;

/**
 * InfoService
 */
public interface InfoService {

  
  public InfoDiaResponse getInfoDia() throws Exception;
  public List<AvisoResponse> getAvisos() throws Exception;

}
