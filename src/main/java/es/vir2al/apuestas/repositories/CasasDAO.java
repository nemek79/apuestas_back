package es.vir2al.apuestas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.vir2al.apuestas.models.Casa;

/**
 * Acceso a base de datos para las casas de apuestas
 */
public interface CasasDAO extends JpaRepository<Casa,Long> {

  @Query("select max(c.id) from Casa c")
  public Long getMaxId();
  
}
