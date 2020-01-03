package es.vir2al.apuestas.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.vir2al.apuestas.models.ApuestaVirtual;
import es.vir2al.apuestas.models.Estado;

/**
 * ApuestasVirtualDAO
 */
public interface ApuestasVirtualDAO extends JpaRepository<ApuestaVirtual,Long> {

  @Query("select a from ApuestaVirtual a where a.fechaEvento = ?1")
  public List<ApuestaVirtual> findByFechaEvento(Date fecha);

  @Query("select a from ApuestaVirtual a where a.fechaEvento >= ?1 and a.fechaEvento <= ?2")
  public List<ApuestaVirtual> findBetweenFechasEvento(Date fechaIni, Date fechaFin);

  @Query("select a from ApuestaVirtual a where a.estado = ?1 and a.fechaEvento >= ?2 and a.fechaEvento <= ?3")
  public List<ApuestaVirtual> findByEstadoBetweenFechasEvento(Estado estado, Date fechaIni, Date fechaFin);

  public ApuestaVirtual findByApuestaId(Long apuestaId);

  @Query("select a from ApuestaVirtual a where a.estado < 3  and a.fechaEvento < curdate()")
  public List<ApuestaVirtual> findPendientes();
  
}
