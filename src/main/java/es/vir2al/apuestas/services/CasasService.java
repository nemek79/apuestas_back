package es.vir2al.apuestas.services;

import java.util.List;

import es.vir2al.apuestas.dtos.CasaDTO;

/**
 * CasasService
 */
public interface CasasService {

  /**
   * Obtiene la casa de apuestas indicada por el identificador
   * @param id
   * @return casa de apuestas
   * @throws Exception
   */
  public CasaDTO findById(Long id) throws Exception;

  /**
   * Devuelve la lista de todas las casas de apuestas del sistema
   * @return
   * @throws Exception
   */
  public List<CasaDTO> findAll() throws Exception;

  /**
   * Crea una nueva casa de apuestas en el sistema
   * @param casa
   * @return
   * @throws Exception
   */
  public CasaDTO create(CasaDTO casa) throws Exception;

  /**
   * Actualiza una casa de apuestas existente en el sistema
   * @param casa
   * @throws Exception
   */
  public void update(Long id,CasaDTO casa) throws Exception;

  /**
   * Elimina la casa de apuestas del sistema identificada por su id
   * @param id
   * @throws Exception
   */
  public void deleteById(Long id) throws Exception;


  /**
   * Actualiza el importe de una casa de apuestas
   * NO ES TRANSACIONAL
   * @param idCasa
   * @param importe
   * @throws Exception
   */
  public void actualizarImporte(Long idCasa, Float importe) throws Exception;
}
