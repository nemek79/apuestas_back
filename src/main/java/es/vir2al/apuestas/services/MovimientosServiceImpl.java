package es.vir2al.apuestas.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.dtos.CasaDTO;
import es.vir2al.apuestas.dtos.MovimientoDTO;
import es.vir2al.apuestas.models.Movimiento;
import es.vir2al.apuestas.repositories.MovimientosDAO;
import es.vir2al.apuestas.services.MovimientosService;
import javassist.NotFoundException;

/**
 * MovimientosServiceImpl
 */
@Service
public class MovimientosServiceImpl implements MovimientosService {

  @Autowired
  private MovimientosDAO movimientosDAO;

  @Override
  @Transactional(readOnly = true)
  public MovimientoDTO findById(Long id) throws Exception {

    Movimiento movimientoBD = this.movimientosDAO.findById(id)
        .orElseThrow(() -> new NotFoundException("No se ha encontrado el movimiento con Id: " + id));

    return new MovimientoDTO(movimientoBD);
  }

  @Override
  @Transactional(readOnly = true)
  public List<MovimientoDTO> findAll() throws Exception {

    List<Movimiento> lstMovimientosBD = this.movimientosDAO.findAll();

    return lstMovimientosBD.stream()
    .map( movimiento -> {
        try {
          return new MovimientoDTO(movimiento);
        } catch (Exception e) {
          System.err.println(e.getMessage());
          return null;
        }
      }
    )
    .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<MovimientoDTO> findByCasa(CasaDTO casa) throws Exception {

    List<Movimiento> lstMovimientosBD = this.movimientosDAO.findByCasa(casa.asCasa().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para la casa con id: "+casa.getId())
    ));

    return lstMovimientosBD.stream()
    .map( movimiento -> {
        try {
          return new MovimientoDTO(movimiento);
        } catch (Exception e) {
          System.err.println(e.getMessage());
          return null;
        }
      }
    )
    .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<MovimientoDTO> findByFechas(Date fechaIni, Date fechaFin) throws Exception {

    List<Movimiento> lstMovimientosBD = this.movimientosDAO.findByFechas(fechaIni, fechaFin);

    return lstMovimientosBD.stream()
    .map( movimiento -> {
        try {
          return new MovimientoDTO(movimiento);
        } catch (Exception e) {
          System.err.println(e.getMessage());
          return null;
        }
      }
    )
    .collect(Collectors.toList());

  }

  @Override
  @Transactional(readOnly = true)
  public List<MovimientoDTO> findByCasaAndFechas(CasaDTO casa, Date fechaIni, Date fechaFin) throws Exception {

    List<Movimiento> lstMovimientosBD = this.movimientosDAO.findByCasaBetweenFechas(casa.asCasa().orElseThrow(
        () -> new Exception("No se ha podido realizar la conversión del modelo para la casa con id: "+casa.getId())
      ),
      fechaIni,
      fechaFin
    );

    return lstMovimientosBD.stream()
    .map( movimiento -> {
        try {
          return new MovimientoDTO(movimiento);
        } catch (Exception e) {
          System.err.println(e.getMessage());
          return null;
        }
      }
    )
    .collect(Collectors.toList());

  }

  @Override
  @Transactional
  public MovimientoDTO create(MovimientoDTO movimiento) throws Exception {
    
    if (movimiento == null || movimiento.getId() != null) {
      throw new Exception("Error al intentar crear el movimiento. Los atributos no son correctos");
    }

    Movimiento movimientoBD = this.movimientosDAO.save(movimiento.asMovimiento().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para el movimiento con id: "+movimiento.getId())
    ));

    if (movimientoBD == null) {
      throw new Exception("Error al crear el nuevo movimiento: "+movimiento.toString());
    }

    return new MovimientoDTO(movimientoBD);
  }

  @Override
  @Transactional
  public void update(Long id,MovimientoDTO movimiento) throws Exception {
    
    Movimiento movimientoBD = null;

    if (movimiento == null || movimiento.getId() == null || movimiento.getId() <= 0 || movimiento.getId() != id) {
      throw new Exception("Error al intentar actualizar, el movimiento no es correcto");
    }

    movimientoBD = this.movimientosDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra el movimiento con ID: "+id) 
    );

    movimientoBD.setFecha(movimiento.getFecha());
    movimientoBD.setCasa(movimiento.getCasa().asCasa().orElse(null)); // si la casa no es correcta ponemos null y fallará
    movimientoBD.setImporte(movimiento.getImporte());
    movimientoBD.setObservacion(movimiento.getObservacion());
    movimientoBD.setTipo(movimiento.getTipo());


    this.movimientosDAO.save(movimientoBD);
  }

  @Override
  @Transactional
  public void deleteById(Long id) throws Exception {
    
    if (id == null || id <= 0) {
      throw new Exception("Error al intentar eliminar, el identificador del movimiento no es correcto");
    }

    this.movimientosDAO.deleteById(id);
  }

  
}
