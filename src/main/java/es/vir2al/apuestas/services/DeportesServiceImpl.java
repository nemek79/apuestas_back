package es.vir2al.apuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.dtos.DeporteDTO;
import es.vir2al.apuestas.dtos.TorneoDTO;
import es.vir2al.apuestas.models.Deporte;
import es.vir2al.apuestas.models.Torneo;
import es.vir2al.apuestas.repositories.DeportesDAO;
import es.vir2al.apuestas.repositories.TorneosDAO;
import es.vir2al.apuestas.services.DeportesService;
import javassist.NotFoundException;

/**
 * DeportesServiceImpl
 */

@Service
public class DeportesServiceImpl implements DeportesService {

  @Autowired
  private DeportesDAO deportesDAO;

  @Autowired
  private TorneosDAO torneosDAO;

  @Override
  @Transactional(readOnly = true)
  public DeporteDTO findDeporteById(Long id) throws Exception {
    
    Deporte deporteBD = this.deportesDAO.findById(id).orElseThrow( () ->
      new NotFoundException("No se ha encontrado el deporte con Id: "+id)
    );

    return new DeporteDTO(deporteBD);
  }

  @Override
  @Transactional(readOnly = true)
  public List<DeporteDTO> findAllDeportes() throws Exception {
    
    List<Deporte> lstDeportesBD = this.deportesDAO.findAll();

    return lstDeportesBD.stream().map(
      deporte -> new DeporteDTO(deporte)
    ).collect(Collectors.toList());

  }

  @Override
  @Transactional
  public DeporteDTO create(DeporteDTO deporte) throws Exception {
    
    // desde el front nos llega el id nulo
    if (deporte == null || deporte.getId() != null) {
      throw new Exception("Error al intentar crear el deporte. Los atributos no son correctos");
    }

    deporte.setId(this.getNextDeporteId());

    Deporte deporteBD = this.deportesDAO.save(deporte.asDeporte().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo con id: "+deporte.getId())
    ));

    if (deporteBD == null) {
      throw new Exception("Error al crear el nuevo deporte: "+deporte.toString());
    }

    return new DeporteDTO(deporteBD);
  }

  @Override
  @Transactional
  public void update(Long id,DeporteDTO deporte) throws Exception {
    
    if ( deporte == null || deporte.getId() == null || deporte.getId() <= 0 || deporte.getId() != id ) {
      throw new Exception("Error al intentar actualizar, el estado no es correcto");
    }

    Deporte deporteBD = this.deportesDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra el deporte con ID: "+id)
    );

    deporteBD.setDescripcion(deporte.getDescripcion());

    this.deportesDAO.save(deporteBD);

  }

  @Override
  @Transactional
  public void deleteDeporteById(Long id) throws Exception {

    // TODO Por seguridad, solo permitir el borrado si no hay torneos asociados

    throw new Exception("El borrado de deportes no se encuentra disponible. Por favor, contacte con el administrador del sistema");
  }

  @Override
  @Transactional(readOnly = true)
  public TorneoDTO findTorneoById(Long id) throws Exception {
    
    Torneo torneoBD = this.torneosDAO.findById(id).orElseThrow( () -> 
      new NotFoundException("No se ha encontrado el torneo con Id: "+id)
    );

    return new TorneoDTO(torneoBD);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TorneoDTO> findAllTorneos() throws Exception {
    
    List<Torneo> lstTorneosBD = this.torneosDAO.findAll();

    return lstTorneosBD.stream().map(
      torneo -> {
        try {
          return new TorneoDTO(torneo);
        } catch (Exception e) {       
          System.err.println(e.getMessage());
          return null;
        }
      }
    ).collect(Collectors.toList());
  
  }

  @Override
  @Transactional(readOnly = true)
  public List<TorneoDTO> findAllTorneosByDeporte(DeporteDTO deporte) throws Exception {

    List<Torneo> lstTorneosBD = this.torneosDAO.findAll();

    return lstTorneosBD.stream()
    .filter(
      // filtramos los torneos cuyo deporte sea el indicado por parametro
      torneo -> torneo.getDeporte().getId() == deporte.getId()
    )
    .map(
      torneo -> {
        try {
          return new TorneoDTO(torneo);
        } catch (Exception e) {       
          System.err.println(e.getMessage());
          return null;
        }
      }
    ).collect(Collectors.toList());   

  }

  @Override
  @Transactional
  public TorneoDTO create(TorneoDTO torneo) throws Exception {
    
    // desde el front nos llega el id a nulo
    if (torneo == null || torneo.getId() != null) {
      throw new Exception("Error al intentar crear el torneo. Los atributos no son correctos");
    }

    //Se le asigna un nuevo id
    torneo.setId(this.getNextTorneoId());

    Torneo torneoBD = this.torneosDAO.save(torneo.asTorneo().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para el torneo con id: "+torneo.getId())
    ));

    if (torneoBD == null) {
      throw new Exception("Error al crear el nuevo torneo: "+torneo.toString());
    }

    return new TorneoDTO(torneoBD);
  }

  @Override
  @Transactional
  public void update(Long id,TorneoDTO torneo) throws Exception {
    
    Torneo torneoBD = null;

    if (torneo == null || torneo.getId() == null || torneo.getId() <= 0 || id != torneo.getId()) {
      throw new Exception("Error al intentar actualizar, el torneo no es correcto");
    }

    torneoBD = this.torneosDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra el torneo con ID: "+id) 
    );

    this.torneosDAO.save(torneoBD);

  }

  @Override
  @Transactional
  public void deleteTorneoById(Long id) throws Exception {
    
    // TODO Por seguridad, solo permitir el borrado si no hay APUESTAS con el torneo asociado
    
    throw new Exception("El borrado de torneos no se encuentra disponible. Por favor, contacte con el administrador del sistema");

  }


  /**
   * Devuelve el siguiente identificador para un nuevo torneo
   * @return
   */
  private Long getNextTorneoId() {

    return this.torneosDAO.getMaxId() + 1;

  }

  /**
   * Devuelve el siguiente identificador para un nuevo deporte
   * @return
   */
  private Long getNextDeporteId() {

    return this.deportesDAO.getMaxId() + 1;

  }
  
}
