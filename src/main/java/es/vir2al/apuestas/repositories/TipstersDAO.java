package es.vir2al.apuestas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.vir2al.apuestas.models.Tipster;

/**
 * Acceso a base de datos para los tipos de apuestas
 */
public interface TipstersDAO extends JpaRepository<Tipster,Long> {

  @Query("select max(t.id) from Tipster t")
  public Long getMaxId();
  

}
