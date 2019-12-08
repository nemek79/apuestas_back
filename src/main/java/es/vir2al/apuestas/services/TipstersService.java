package es.vir2al.apuestas.services;

import java.util.List;

import es.vir2al.apuestas.dtos.TipsterDTO;

/**
 * TipstersService
 */
public interface TipstersService {

  /**
   * Obtiene el tipster indicado por el identificador
   * @param id
   * @return
   * @throws Exception
   */
  public TipsterDTO findById(Long id) throws Exception;

  /**
   * Obtiene la lista de todos los tipster del sistema
   * @return
   * @throws Exception
   */
  public List<TipsterDTO> findAll() throws Exception;

  /**
   * Obtiene la lista de todos los tipster ACTIVOS del sistema
   * @return
   * @throws Exception
   */
  public List<TipsterDTO> findAllActive() throws Exception;
  
  /**
   * Crea un nuevo tipster en el sistema
   * @param tipster
   * @return
   * @throws Exception
   */
  public TipsterDTO create(TipsterDTO tipster) throws Exception;

  /**
   * Actualiza un tipster del sistema
   * @param tipster
   * @throws Exception
   */
  public void update(Long id,TipsterDTO tipster) throws Exception;


  /**
   * Inactiva el tipster indicado
   * @param id
   * @throws Exception
   */
  public void inactivar(Long id) throws Exception;
}
