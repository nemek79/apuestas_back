package es.vir2al.apuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.dtos.CasaDTO;
import es.vir2al.apuestas.models.Casa;
import es.vir2al.apuestas.repositories.CasasDAO;
import es.vir2al.apuestas.services.CasasService;
import javassist.NotFoundException;

/**
 * CasasServiceImpl
 */
@Service
public class CasasServiceImpl implements CasasService {

  @Autowired
  private CasasDAO casasDAO;

  @Override
  @Transactional(readOnly = true)
  public CasaDTO findById(Long id) throws Exception {
    
    Casa casaBD = this.casasDAO.findById(id).orElseThrow( () -> 
      new NotFoundException("No se ha encontrado la casa de apuestas con Id: "+id)
    );

    return new CasaDTO(casaBD);

  }

  @Override
  @Transactional(readOnly = true)
  public List<CasaDTO> findAll() throws Exception {
    
    List<Casa> lstCasasBD = this.casasDAO.findAll();

    return lstCasasBD.stream().map(
      casa -> new CasaDTO(casa)
    ).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CasaDTO create(CasaDTO casa) throws Exception {
    
    // del front nos llega el id nulo
    if (casa == null || casa.getId() != null) {
      throw new Exception("Error al intentar crear la casa de apuestas. Los atributos no son correctos");
    }

    // actualizar el id al siguiente
    casa.setId(this.getNextCasaId());

    Casa casaBD = this.casasDAO.save(casa.asCasa().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para la casa de apuestas con id: "+casa.getId())
    ));

    if (casaBD == null) {
      throw new Exception("Error al crear la casa de apuestas: "+casa.toString());
    }

    return new CasaDTO(casaBD);
  }

  @Override
  @Transactional
  public void update(Long id,CasaDTO casa) throws Exception {
   
    Casa casaBD = null;

    if (casa == null || casa.getId() == null || casa.getId() <= 0 || id != casa.getId() ) {
      throw new Exception("Error al intentar actualizar la casa de apuestas. Los atributos no son correctos");
    }

    casaBD = this.casasDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra la casa de apuesta con ID: "+id)
    );

    // Actualizar los campos del objeto de la base de datos
    casaBD.setDescripcion(casa.getDescripcion());
    casaBD.setCantidad(casa.getCantidad());
    casaBD.setUrl(casa.getUrl());    

    this.casasDAO.save(casaBD);

  }

  @Override
  @Transactional
  public void deleteById(Long id) throws Exception {
    
    if (id == null || id <= 0) {
      throw new Exception("Error al intentar eliminar, el identificador de la casa de apuestas no es correcto");
    }

    this.casasDAO.deleteById(id);
  }

  /**
   * Obtiene el siguiente identificador para una nueva casa de apuestas
   * @return
   */
  private Long getNextCasaId() {

    return this.casasDAO.getMaxId() + 1;

  }

  @Override
  public void actualizarImporte(Long idCasa, Float importe) throws Exception {
    
    Casa casa = this.casasDAO.findById(idCasa).orElseThrow(
      () -> new Exception("No se encuentra la casa a modificar")
    );

    casa.setCantidad(casa.getCantidad() + importe);

    this.casasDAO.save(casa);

    return;
  }
}
