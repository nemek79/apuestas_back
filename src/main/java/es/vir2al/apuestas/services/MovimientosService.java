package es.vir2al.apuestas.services;

import java.util.Date;
import java.util.List;

import es.vir2al.apuestas.dtos.CasaDTO;
import es.vir2al.apuestas.dtos.MovimientoDTO;

/**
 * MovimientosService
 */
public interface MovimientosService {

  /**
   * Obtiene un movimiento por su identificador
   * @param id
   * @return Movimiento
   * @throws Exception
   */
  public MovimientoDTO findById(Long id) throws Exception;

  /**
   * Obtiene la lista de todos los movimientos del sistema
   * @return
   * @throws Exception
   */
  public List<MovimientoDTO> findAll() throws Exception;

  /**
   * Obtiene la lista de todos los movimientos asociados a una casa determinada
   * @param casa
   * @return
   * @throws Exception
   */
  public List<MovimientoDTO> findByCasa(CasaDTO casa) throws Exception;

  /**
   * Obtiene la lista de todos los movimientos entre unas fechas determinadas
   * @param fechaIni
   * @param fechaFin
   * @return
   * @throws Exception
   */
  public List<MovimientoDTO> findByFechas(Date fechaIni, Date fechaFin) throws Exception;

  /**
   * Obtiene la lista de todos los movimientos asociados a una casa y entre unas fechas determinadas
   * @param casa
   * @param fechaIni
   * @param fechaFin
   * @return
   * @throws Exception
   */
  public List<MovimientoDTO> findByCasaAndFechas(CasaDTO casa,Date fechaIni, Date fechaFin) throws Exception;

  /**
   * Crea un nuevo movimiento en el sistema
   * @param movimiento
   * @return
   * @throws Exception
   */
  public MovimientoDTO create(MovimientoDTO movimiento) throws Exception;

  /**
   * Actualiza un movimiento en el sistema
   * @param movimiento
   * @throws Exception
   */
  public void update(Long id,MovimientoDTO movimiento) throws Exception;

  /**
   * Eliminta un movimiento asociado a un identificador
   * @param id
   * @throws Exception
   */
  public void deleteById(Long id) throws Exception;

}
