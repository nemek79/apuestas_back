package es.vir2al.apuestas.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.vir2al.apuestas.models.Apuesta;
import es.vir2al.apuestas.models.Estado;

/**
 * Acceso a base de datos para las apuestas
 */
public interface ApuestasDAO extends JpaRepository<Apuesta,Long> {

  @Query("select a from Apuesta a where a.fechaEvento = ?1")
  public List<Apuesta> findByFechaEvento(Date fecha);

  @Query("select a from Apuesta a where a.fechaEvento >= ?1 and a.fechaEvento <= ?2")
  public List<Apuesta> findBetweenFechasEvento(Date fechaIni, Date fechaFin);

  @Query("select a from Apuesta a where a.estado = ?1 and a.fechaEvento >= ?2 and a.fechaEvento <= ?3")
  public List<Apuesta> findByEstadoBetweenFechasEvento(Estado estado, Date fechaIni, Date fechaFin);

}
