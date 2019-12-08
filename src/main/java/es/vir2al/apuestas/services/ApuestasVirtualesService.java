package es.vir2al.apuestas.services;

import java.util.Date;
import java.util.List;

import es.vir2al.apuestas.dtos.ApuestaVirtualDTO;

/**
 * ApuestasVirtualesService
 */
public interface ApuestasVirtualesService {

  public ApuestaVirtualDTO findById(Long id) throws Exception;
  public List<ApuestaVirtualDTO> findByFechaEvento(Date fecha) throws Exception;
  public ApuestaVirtualDTO create(ApuestaVirtualDTO apuesta) throws Exception;
  public void update(Long id, ApuestaVirtualDTO apuesta) throws Exception;
  public void updateEstado(Long id, Long estadoId) throws Exception;
  public ApuestaVirtualDTO findByApuestaAsociada(Long id) throws Exception;
}
