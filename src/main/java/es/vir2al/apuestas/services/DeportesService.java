package es.vir2al.apuestas.services;

import java.util.List;

import es.vir2al.apuestas.dtos.DeporteDTO;
import es.vir2al.apuestas.dtos.TorneoDTO;

/**
 * DeportesService
 * Servicio para Deportes y Torneos
 */
public interface DeportesService {

  // =======================================
  // DEPORTES
  // =======================================

  /**
   * Busca un deporte por su identificador
   * @param id
   * @return Deporte
   * @throws Exception
   */
  public DeporteDTO findDeporteById(Long id) throws Exception;

  /**
   * Obtiene la lista de todos los deportes del sistema
   * @return deportes
   * @throws Exception
   */
  public List<DeporteDTO> findAllDeportes() throws Exception;

  /**
   * Crea un uevo deporte en el sistema
   * @param deporte
   * @return
   * @throws Exception
   */
  public DeporteDTO create(DeporteDTO deporte) throws Exception;

  /**
   * Actualiza un deporte existente en el sistema
   * @param deporte
   * @throws Exception
   */
  public void update(Long id,DeporteDTO deporte) throws Exception;

  /**
   * Elimina un deporte del sistema (y todos sus torneos) por el identificador
   * @param id
   * @throws Exception
   */
  public void deleteDeporteById(Long id) throws Exception;

  // =======================================
  // TORNEOS
  // =======================================
  
  /**
   * Busca un torneo por su identificador
   * @param id
   * @return Torneo
   * @throws Exception
   */
  public TorneoDTO findTorneoById(Long id) throws Exception;

  /**
   * Obtiene la lista de todos los torneos del sistema
   * @return Lista de torneos
   * @throws Exception
   */
  public List<TorneoDTO> findAllTorneos() throws Exception;

  /**
   * Devuelve la lista de todos los torneos asociados a un deporte
   * @param deporte
   * @return lista de torneos
   * @throws Exception
   */
  public List<TorneoDTO> findAllTorneosByDeporte(DeporteDTO deporte) throws Exception;

  /**
   * Crea un nuevo torneo en el sistema
   * @param torneo
   * @return
   * @throws Exception
   */
  public TorneoDTO create(TorneoDTO torneo) throws Exception;

  /**
   * Actualiza un torneo existente en el sistema
   * @param torneo
   * @throws Exception
   */
  public void update(Long id,TorneoDTO torneo) throws Exception;

  /**
   * Elimina un torneo del sistema por su identificador
   * @param id
   * @throws Exception
   */
  public void deleteTorneoById(Long id) throws Exception;
}
