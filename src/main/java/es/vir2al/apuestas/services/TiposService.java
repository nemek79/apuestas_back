package es.vir2al.apuestas.services;

import java.util.List;

import es.vir2al.apuestas.dtos.TipoDTO;

/**
 * TiposService
 */
public interface TiposService {

  /**
   * Obtiene un tipo de apuesta por su identificador
   * @param id
   * @return
   * @throws Exception
   */
  public TipoDTO findById(Long id) throws Exception;
  
  /**
   * Obtiene todos los tipos de apuestas del sistema
   * @return
   * @throws Exception
   */
  public List<TipoDTO> findAll() throws Exception;

  /**
   * Crea un nuevo tipo de apuesta en el sistema
   * @param tipo
   * @return
   * @throws Exception
   */
  public TipoDTO create(TipoDTO tipo) throws Exception;

  /**
   * Actualiza un tipo de apuesta del sistema
   * @param tipo
   * @throws Exception
   */
  public void update(Long id,TipoDTO tipo) throws Exception;

  /**
   * Elimina un tipo de apuesta del sistema
   * @param tipo
   * @throws Exception
   */
  public void delete(TipoDTO tipo) throws Exception;
}
