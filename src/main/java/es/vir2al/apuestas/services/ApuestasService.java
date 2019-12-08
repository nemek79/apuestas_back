package es.vir2al.apuestas.services;

import java.util.Date;
import java.util.List;

import es.vir2al.apuestas.dtos.ApuestaDTO;
import es.vir2al.apuestas.dtos.EstadoDTO;

/**
 * ApuestasService
 */
public interface ApuestasService {

  /**
   * Obtiene la apuesta por su identificador
   * @param id
   * @return
   * @throws Exception
   */
  public ApuestaDTO findById(Long id) throws Exception;

  /**
   * Obtiene todas las apuestas con una fecha de evento determinada
   * @param fecha
   * @return
   * @throws Exception
   */
  public List<ApuestaDTO> findByFechaEvento(Date fecha) throws Exception;

  /**
   * Obtiene todas las apuestas entre unas fechas determinadas (incluidas)
   * @param fechaIni
   * @param fechaFin
   * @return
   * @throws Exception
   */
  public List<ApuestaDTO> findBetweenFechasEvento(Date fechaIni, Date fechaFin) throws Exception; 

  /**
   * Obtien todas las apuestas en un estado determinado dentro de unas fechas determinadas (incluidas)
   * @param estado
   * @param fechaIni
   * @param fechaFin
   * @return
   * @throws Exception
   */
  public List<ApuestaDTO> findByEstadoBetweenFechasEvento(EstadoDTO estado, Date fechaIni, Date fechaFin) throws Exception;  


  /**
   * Crea una nueva apuesta en el sistema
   * @param apuesta
   * @return
   * @throws Exception
   */
  public ApuestaDTO create(ApuestaDTO apuesta) throws Exception;

  /**
   * Actualiza una apuesta existente en el sistema
   * @param apuesta
   * @throws Exception
   */
  public void update(Long id, ApuestaDTO apuesta) throws Exception;

  /**
   * Elimina una apuesta del sistema
   * @param id
   * @throws Exception
   */
  public void deleteById(Long id) throws Exception;


  /**
   * Actualiza una apuesta al estado indicado
   * @param id
   * @param estadoId
   * @throws Exception
   */
  public void updateEstado(Long id, Long estadoId) throws Exception;

}
