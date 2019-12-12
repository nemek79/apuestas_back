package es.vir2al.apuestas.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.constantes.ConstApp;
import es.vir2al.apuestas.dtos.ApuestaDTO;
import es.vir2al.apuestas.dtos.EstadoDTO;
import es.vir2al.apuestas.models.Apuesta;
import es.vir2al.apuestas.models.Estado;
import es.vir2al.apuestas.repositories.ApuestasDAO;
import es.vir2al.apuestas.repositories.EstadosDAO;
import es.vir2al.apuestas.services.ApuestasService;
import javassist.NotFoundException;

/**
 * ApuestasServiceImpl
 */
@Service
public class ApuestasServiceImpl implements ApuestasService {

  private static final Logger logger = LoggerFactory.getLogger(ApuestasServiceImpl.class);

  @Autowired
  private ApuestasDAO apuestasDAO;

  @Autowired
  private EstadosDAO estadosDAO;

  @Autowired 
  private CasasService casasSRV;

  @Override
  @Transactional(readOnly = true)
  public ApuestaDTO findById(Long id) throws Exception {

    Apuesta apuestaBD = this.apuestasDAO.findById(id)
        .orElseThrow(() -> new NotFoundException("No se ha encontrado la apuesta con Id: " + id));

    logger.warn(apuestaBD.toString());

    return new ApuestaDTO(apuestaBD);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ApuestaDTO> findByFechaEvento(Date fecha) throws Exception {

    List<Apuesta> lstApuestasBD = this.apuestasDAO.findByFechaEvento(fecha);

    return lstApuestasBD.stream().map(apuesta -> {
      try {
        return new ApuestaDTO(apuesta);
      } catch (Exception e) {
        System.err.println(e.getMessage());
        return null;
      }
    })
    .collect(Collectors.toList());

  }

  @Override
  @Transactional(readOnly = true)
  public List<ApuestaDTO> findBetweenFechasEvento(Date fechaIni, Date fechaFin) throws Exception {

    List<Apuesta> lstApuestasBD = this.apuestasDAO.findBetweenFechasEvento(fechaIni, fechaFin);

    return lstApuestasBD.stream().map(apuesta -> {
      try {
        return new ApuestaDTO(apuesta);
      } catch (Exception e) {
        System.err.println(e.getMessage());
        return null;
      }
    })
    .collect(Collectors.toList());

  }

  @Override
  @Transactional(readOnly = true)
  public List<ApuestaDTO> findByEstadoBetweenFechasEvento(EstadoDTO estado, Date fechaIni, Date fechaFin)
      throws Exception {

    List<Apuesta> lstApuestasBD = 
        this.apuestasDAO.findByEstadoBetweenFechasEvento(estado.asEstado().orElseThrow(
          () -> new Exception("No se ha podido realizar la conversión del modelo para el estado con id: "+estado.getId())
        ), 
        fechaIni, 
        fechaFin);

    return lstApuestasBD.stream().map(apuesta -> {
      try {
        return new ApuestaDTO(apuesta);
      } catch (Exception e) {
        System.err.println(e.getMessage());
        return null;
      }
    })
    .collect(Collectors.toList());

  }

  @Override
  @Transactional
  public ApuestaDTO create(ApuestaDTO apuesta) throws Exception {
   
    if (apuesta == null || apuesta.getId() != null) {
      throw new Exception("Error al intentar crear la apuesta. Los atributos no son correctos");
    }

    Apuesta apuestaBD = this.apuestasDAO.save(apuesta.asApuesta().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para la apuesta con id: "+apuesta.getId())
    ));

    if (apuestaBD == null) {
      throw new Exception("Error al crear la apuesta: "+apuesta.toString());
    }

    // Actualizar el importe de la casa de apuestas
    this.casasSRV.actualizarImporte(apuesta.getCasa().getId(), apuesta.getImporte()*-1);

    return new ApuestaDTO(apuestaBD);
  }

  @Override
  @Transactional
  public void update(Long id,ApuestaDTO apuesta) throws Exception {
    
    Apuesta apuestaBD = null;

    if (apuesta == null || apuesta.getId() == null || apuesta.getId() <= 0 || apuesta.getId() != id) {
      throw new Exception("Error al intentar actualizar, el estado no es correcto");
    }

    apuestaBD = this.apuestasDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra la apuesta con ID: "+id)
    );

    apuestaBD.setApuesta(apuesta.getApuesta());
    apuestaBD.setCuota(apuesta.getCuota());
    apuestaBD.setDescripcion(apuesta.getDescripcion());
    //apuestaBD.setFechaAlta(apuesta.getFechaAlta());
    apuestaBD.setFechaEvento(apuesta.getFechaEvento());
    apuestaBD.setGanancia(apuesta.getGanancia());
    apuestaBD.setImporte(apuesta.getImporte());
    apuestaBD.setStake(apuesta.getStake());
    apuestaBD.setCasa(apuesta.getCasa().asCasa().orElseThrow(
      () -> new Exception("La casa de apuesta no es correcta")
    ));
    apuestaBD.setEstado(apuesta.getEstado().asEstado().orElseThrow(
      () -> new Exception("El estado no es correcto")
    ));
    apuestaBD.setTipo(apuesta.getTipo().asTipo().orElseThrow(
      () -> new Exception("El tipo no es correcto")
    ));
    apuestaBD.setTipster(apuesta.getTipster().asTipster().orElseThrow(
      () -> new Exception("La tipster no es correcto")
    ));
    apuestaBD.setTorneo(apuesta.getTorneo().asTorneo().orElseThrow(
      () -> new Exception("El torneo no es correcto")
    ));
    
    this.apuestasDAO.save(apuestaBD);

    // se actualiza la apuesta virtual

  }

  @Override
  @Transactional
  public void deleteById(Long id) throws Exception {
    
    // TODO Por seguridad, no permitimos el borrado por ahora

    throw new Exception("El borrado de apuestas no se encuentra disponible. Por favor, contacte con el administrador del sistema");


  }

  @Override
  @Transactional
  public void updateEstado(Long id, Long estadoId) throws Exception {
    
    // Obtener la apuesta de la base de datos que se va a actualizar

    Apuesta apuesta = this.apuestasDAO.findById(id).orElseThrow(
      () -> new Exception("La apuesta para actualizar no es correcta")
    );

    if (apuesta.getEstado().getId() > 2) {
      new Exception("El estado de la apuesta no se puede actualizar");
    }

    // Obtener el nuevo estado
    Estado estado = this.estadosDAO.findById(estadoId).orElseThrow(
      () -> new Exception("Error al obtener el nuevo estado de la apuesta")
    );

    Integer newEstadoId = Math.round(estadoId);

    if (newEstadoId == ConstApp.ESTADO_GANADA) {

      Float ganancia = (apuesta.getCuota() * apuesta.getImporte()) - apuesta.getImporte();
      apuesta.setGanancia(ganancia);
      // se actualiza el importe de la casa de apuestas con el importe gastado más el importe de la ganancia
      this.casasSRV.actualizarImporte(apuesta.getCasa().getId(), apuesta.getGanancia()+apuesta.getImporte());

    } else if ( newEstadoId == ConstApp.ESTADO_PERDIDA) {

      // Cuando se pierde se genera una perdida igual al importe de la apuesta
      apuesta.setGanancia(apuesta.getImporte()*-1);

    } else if ( newEstadoId == ConstApp.ESTADO_CANCELADA || 
                newEstadoId == ConstApp.ESTADO_SUSPENDIDA ||
                newEstadoId == ConstApp.ESTADO_PUSH
              ) {

      apuesta.setGanancia(0f);
      // se actualiza el importe de la casa de apuestas con el importe gastado 
      this.casasSRV.actualizarImporte(apuesta.getCasa().getId(), apuesta.getImporte());

    } else {

      new Exception("No se puede actualizar la apuesta al nuevo estado");

    }

    apuesta.setEstado(estado);
    this.apuestasDAO.save(apuesta);

    return;
  }
 
}
