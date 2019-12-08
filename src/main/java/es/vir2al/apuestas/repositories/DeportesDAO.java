package es.vir2al.apuestas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.vir2al.apuestas.models.Deporte;

/**
 * Acceso a base de datos para los deportes
 */
public interface DeportesDAO extends JpaRepository<Deporte,Long> {

  @Query("select max(d.id) from Deporte d")
  public Long getMaxId();
  
}
