package es.vir2al.apuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.dtos.TipoDTO;
import es.vir2al.apuestas.models.Tipo;
import es.vir2al.apuestas.repositories.TiposDAO;
import es.vir2al.apuestas.services.TiposService;
import javassist.NotFoundException;

/**
 * TiposServiceImpl
 */
@Service
public class TiposServiceImpl implements TiposService {

  @Autowired
  private TiposDAO tiposDAO;

  @Override
  @Transactional(readOnly = true)
  public TipoDTO findById(Long id) throws Exception {
    
    Tipo tipo = this.tiposDAO.findById(id).orElseThrow( () -> 
      new NotFoundException("No se ha encontrado el tipo de apuesta con Id: "+id)
    );

    return new TipoDTO(tipo);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TipoDTO> findAll() throws Exception {
    
    List<Tipo> lstTipos = this.tiposDAO.findAll();

    return lstTipos.stream()
    .map(
      tipo -> new TipoDTO(tipo)
    )
    .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public TipoDTO create(TipoDTO tipo) throws Exception {
    
    // Desde el front llega el id nulo
    if (tipo == null || tipo.getId() != null) {
      throw new Exception("Error al intentar crear el tipo de apuesta. Los atributos no son correctos");
    }

    // Cargamos el siguiente id disponible
    tipo.setId(this.getNextTipoId());


    Tipo tipoBD = this.tiposDAO.save(tipo.asTipo().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para el tipo con id: "+tipo.getId())
    ));

    if (tipoBD == null) {
      throw new Exception("Error al crear el nuevo tipo: "+tipo.toString());
    }

    return new TipoDTO(tipoBD);
  }

  @Override
  @Transactional
  public void update(Long id,TipoDTO tipo) throws Exception {
    
    Tipo tipoBD = null;

    if (tipo == null || tipo.getId() == null || tipo.getId() <= 0 || id != tipo.getId()) {
      throw new Exception("Error al intentar actualizar, el estado no es correcto");
    }

    tipoBD = this.tiposDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra el tipo de apuesta: "+id)
    );

    tipoBD.setDescripcion(tipo.getDescripcion());

    this.tiposDAO.save(tipoBD);

  }

  @Override
  @Transactional
  public void delete(TipoDTO tipo) throws Exception {
 
        // TODO Por seguridad, no se permitirá el borrado por ahora
    
        throw new Exception("El borrado de tipos de apuesta no se encuentra disponible. Por favor, contacte con el administrador del sistema");

  }

  /**
   * Obtiene el siguiente identificador para un uevo torneo
   * @return
   */
  private Long getNextTipoId() {
    return this.tiposDAO.getMaxId()+1;
  }
  
}
