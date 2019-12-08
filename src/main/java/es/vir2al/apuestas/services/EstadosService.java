package es.vir2al.apuestas.services;

import java.util.List;

import es.vir2al.apuestas.dtos.EstadoDTO;

/**
 * EstadosService
 */
public interface EstadosService {

  /**
   * Busca un estado determinado por su identificador
   * @param id
   * @return estado
   * @throws Exception
   */
  public EstadoDTO findById(Long id) throws Exception;
  
  /**
   * Devuelve la lista de todos los estados disponibles en el sistema
   * @return lista de estados
   * @throws Exception
   */
  public List<EstadoDTO> findAll() throws Exception;

  /**
   * Crea un nuevo estado en el sistema
   * @param estado
   * @return estado
   * @throws Exception
   */
  public EstadoDTO create(EstadoDTO estado) throws Exception;

  /**
   * Actualiza un estado del sistema
   * @param estado
   * @throws Exception
   */
  public void update(Long id,EstadoDTO estado) throws Exception;

  /**
   * Elimina el estado indicado por el identificador proporcionado como parámetro
   * @param id
   * @throws Exception
   */
  public void deleteById(Long id) throws Exception;
  
}
