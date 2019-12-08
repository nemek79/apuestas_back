package es.vir2al.apuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.dtos.EstadoDTO;
import es.vir2al.apuestas.models.Estado;
import es.vir2al.apuestas.repositories.EstadosDAO;
import es.vir2al.apuestas.services.EstadosService;
import javassist.NotFoundException;

/**
 * EstadosServiceImpl
 */
@Service
public class EstadosServiceImpl implements EstadosService {

  @Autowired
  private EstadosDAO estadosDAO;

  @Override
  @Transactional(readOnly = true)
  public EstadoDTO findById(Long id) throws Exception {
    
    Estado estado = this.estadosDAO.findById(id).orElseThrow(() -> 
      new NotFoundException("No se ha encontrado el estado con Id: "+id)
    );

    return new EstadoDTO(estado);
  }

  @Override
  @Transactional(readOnly = true)
  public List<EstadoDTO> findAll() throws Exception {
    
    List<Estado> lstEstadosBD = this.estadosDAO.findAll();

    return lstEstadosBD.stream().map(
      estado -> new EstadoDTO(estado)
    ).collect(Collectors.toList());

  }

  @Override
  @Transactional
  public EstadoDTO create(EstadoDTO estado) throws Exception {
    
    // el id es nulo cuando llega del front
    if (estado == null || estado.getId() != null) {
      throw new Exception("Error al intentar crear el estado. Los atributos no son correctos");
    }

    estado.setId(this.getNextEstadoId());

    Estado estadoBD = this.estadosDAO.save(estado.asEstado().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para el estado con id: "+estado.getId())
    ));

    if (estadoBD == null) {
      throw new Exception("Error al crear el nuevo estado: "+estado.toString());
    }

    return new EstadoDTO(estadoBD);
  }

  @Override
  @Transactional
  public void update(Long id,EstadoDTO estado) throws Exception {
    
    Estado estadoBD = null;

    if (estado == null || estado.getId() == null || estado.getId() <= 0 || estado.getId() != id) {
      throw new Exception("Error al intentar actualizar, el estado no es correcto");
    }

    estadoBD = this.estadosDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra el estado con id: "+id)
    );
    
    estadoBD.setDescripcion(estado.getDescripcion());

    this.estadosDAO.save(estadoBD);

  }

  @Override
  @Transactional
  public void deleteById(Long id) throws Exception {
    
    if (id == null || id <= 0) {
      throw new Exception("Error al intentar eliminar, el identificador del estado no es correcto");
    }

    this.estadosDAO.deleteById(id);
  }

  /**
   * Obtiene el siguiente identificador para un nuevo estado
   * @return
   */
  private Long getNextEstadoId() {

    return this.estadosDAO.getMaxId();
  }
  
}
