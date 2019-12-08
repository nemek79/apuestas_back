package es.vir2al.apuestas.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.dtos.TipsterDTO;
import es.vir2al.apuestas.models.Tipster;
import es.vir2al.apuestas.repositories.TipstersDAO;
import es.vir2al.apuestas.services.TipstersService;
import javassist.NotFoundException;

/**
 * TipstersServiceImpl
 */
@Service
public class TipstersServiceImpl implements TipstersService {

  @Autowired
  private TipstersDAO tipsterDAO;

  @Override
  @Transactional(readOnly = true)
  public TipsterDTO findById(Long id) throws Exception {
    
    Tipster tipsterBD = this.tipsterDAO.findById(id).orElseThrow(
      () -> new NotFoundException("No se ha encontrado el tipster con Id: "+id)
    );

    return new TipsterDTO(tipsterBD);
  }

  @Override
  @Transactional(readOnly = true)
  public List<TipsterDTO> findAll() throws Exception {
    
    List<Tipster> lstTipstersBD = this.tipsterDAO.findAll();

    return lstTipstersBD.stream()
    .map(
      tipster -> new TipsterDTO(tipster)
    )
    .collect(Collectors.toList());

  }

  @Override
  @Transactional(readOnly = true)
  public List<TipsterDTO> findAllActive() throws Exception {

    List<Tipster> lstTipstersBD = this.tipsterDAO.findAll();

    return lstTipstersBD.stream()
    .filter(
      tipster -> tipster.getActivo()
    )
    .map(
      tipster -> new TipsterDTO(tipster)
    )
    .collect(Collectors.toList());

  }

  @Override
  @Transactional
  public TipsterDTO create(TipsterDTO tipster) throws Exception {
    
    // El id es nulo cuando llega desde el front
    if (tipster == null || tipster.getId() != null) {
      throw new Exception("Error al intentar crear el tipster. Los atributos no son correctos");
    }

    tipster.setId(this.getNextTipsterId());

    Tipster tipsterBD = this.tipsterDAO.save(tipster.asTipster().orElseThrow(
      () -> new Exception("No se ha podido realizar la conversión del modelo para el tipster con id: "+tipster.getId())
    ));

    if (tipsterBD == null) {
      throw new Exception("Error al crear el nuevo tipster: "+tipster.toString());
    }

    return new TipsterDTO(tipsterBD);
  }

  @Override
  @Transactional
  public void update(Long id,TipsterDTO tipster) throws Exception {
    
    Tipster tipsterBD = null;

    if (tipster == null || tipster.getId() == null || tipster.getId() <= 0 || id != tipster.getId()) {
      throw new Exception("Error al intentar actualizar, el estado no es correcto");
    }

    tipsterBD = this.tipsterDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra el tipster con id: "+id)
    );

    tipsterBD.setDescripcion(tipster.getDescripcion());
    tipsterBD.setTelegram(tipster.getTelegram());
    tipsterBD.setUrl(tipster.getUrl());

    // el campo de activo se actualiza especialmente con una función.

    this.tipsterDAO.save(tipsterBD);

  }

  @Override
  @Transactional
  public void inactivar(Long id) throws Exception {

    Tipster tipsterBD = this.tipsterDAO.findById(id).orElseThrow(
      () -> new Exception("No se encuentra el tipster con id: "+id)
    );

    tipsterBD.setActivo(false);

    this.tipsterDAO.save(tipsterBD);

  }

  /**
   * Obtiene el siguiente identificador para un nuevo tipster
   * @return
   */
  private Long getNextTipsterId() {

    return this.tipsterDAO.getMaxId() + 1;

  }
  
}
