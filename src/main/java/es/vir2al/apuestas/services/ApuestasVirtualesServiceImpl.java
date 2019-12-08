package es.vir2al.apuestas.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.constantes.ConstApp;
import es.vir2al.apuestas.dtos.ApuestaVirtualDTO;
import es.vir2al.apuestas.models.ApuestaVirtual;
import es.vir2al.apuestas.models.Estado;
import es.vir2al.apuestas.repositories.ApuestasVirtualDAO;
import es.vir2al.apuestas.repositories.EstadosDAO;
import javassist.NotFoundException;

/**
 * ApuestasVirtualesServiceImpl
 */
@Service
public class ApuestasVirtualesServiceImpl implements ApuestasVirtualesService {

  @Autowired
  private ApuestasVirtualDAO apuestasDAO;

  @Autowired
  private EstadosDAO estadosDAO;

  @Override
  @Transactional
  public ApuestaVirtualDTO findById(Long id) throws Exception {

    ApuestaVirtual apuestaBD = this.apuestasDAO.findById(id)
        .orElseThrow(() -> new NotFoundException("No se ha encontrado la apuesta virtual con Id: " + id));

    return new ApuestaVirtualDTO(apuestaBD);
  }

  @Override
  @Transactional
  public List<ApuestaVirtualDTO> findByFechaEvento(Date fecha) throws Exception {

    List<ApuestaVirtual> lstApuestasBD = this.apuestasDAO.findByFechaEvento(fecha);

    return lstApuestasBD.stream().map(apuesta -> {
      try {
        return new ApuestaVirtualDTO(apuesta);
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    })
    .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ApuestaVirtualDTO create(ApuestaVirtualDTO apuesta) throws Exception {

    if (apuesta == null || apuesta.getId() != null) {
      throw new Exception("Error al intentar crear la apuesta virtual. Los atributos no son correctos");
    }

    // En las apuestas virtuales el importe siempre es igual al stake ya que se medirá en unidades
    apuesta.setImporte(apuesta.getStake());

    ApuestaVirtual apuestaBD = this.apuestasDAO.save(apuesta.asApuestaVirtual().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para la apuesta con id: "+apuesta.getId())
    ));

    if (apuestaBD == null) {
      throw new Exception("Error al crear la apuesta virtual: "+apuesta.toString());
    }

    return new ApuestaVirtualDTO(apuestaBD);
  }

  @Override
  @Transactional
  public void update(Long id, ApuestaVirtualDTO apuesta) throws Exception {
    
    ApuestaVirtual apuestaBD = null;

    if (apuesta == null || apuesta.getId() == null || apuesta.getId() <= 0 || apuesta.getId() != id) {
      throw new Exception("Error al intentar actualizar, el estado no es correcto");
    }

    apuestaBD = this.apuestasDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra la apuesta virtual con ID: "+id)
    );  
    
    apuestaBD.setApuesta(apuesta.getApuesta());
    apuestaBD.setCuota(apuesta.getCuota());
    apuestaBD.setDescripcion(apuesta.getDescripcion());
    //apuestaBD.setFechaAlta(apuesta.getFechaAlta());
    apuestaBD.setFechaEvento(apuesta.getFechaEvento());
    apuestaBD.setGanancia(apuesta.getGanancia());
    apuestaBD.setStake(apuesta.getStake());

    // En las apuestas virtuales el importe siempre es igual al stake ya que se medirá en unidades
    apuesta.setImporte(apuesta.getStake());

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
  }

  @Override
  @Transactional
  public void updateEstado(Long id, Long estadoId) throws Exception {
 
    ApuestaVirtual apuesta = this.apuestasDAO.findById(id).orElseThrow(
      () -> new Exception("La apuesta para actualizar no es correcta")
    );

    if (apuesta.getEstado().getId() <= 2) {
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

    } else if ( newEstadoId == ConstApp.ESTADO_PERDIDA) {
    
      // Cuando se pierde se genera una perdida igual al importe de la apuesta
      apuesta.setGanancia(apuesta.getImporte()*-1);

    } else if ( newEstadoId == ConstApp.ESTADO_CANCELADA || 
                newEstadoId == ConstApp.ESTADO_SUSPENDIDA ||
                newEstadoId == ConstApp.ESTADO_PUSH
              ) {

      apuesta.setGanancia(0f);

    } else {
      new Exception("No se puede actualizar la apuesta al nuevo estado");
    }

    apuesta.setEstado(estado);
    this.apuestasDAO.save(apuesta);

    return;
  }

  @Override
  @Transactional
  public ApuestaVirtualDTO findByApuestaAsociada(Long id) throws Exception {

    ApuestaVirtual apuestaBD = this.apuestasDAO.findByApuestaId(id);

    if (apuestaBD == null) throw new NotFoundException("No se ha encontrado la apuesta virtual con Id: " + id);

    return new ApuestaVirtualDTO(apuestaBD);

  }

  
}
