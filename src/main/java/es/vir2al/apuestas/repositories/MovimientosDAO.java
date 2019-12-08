package es.vir2al.apuestas.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.vir2al.apuestas.models.Casa;
import es.vir2al.apuestas.models.Movimiento;

/**
 * Acceso a base de datos para los deportes
 */
public interface MovimientosDAO extends JpaRepository<Movimiento,Long> {

  @Query("select m from Movimiento m where m.fecha >= ?1 and m.fecha <= ?2")
  public List<Movimiento> findByFechas(Date fechaIni, Date fechaFin);

  public List<Movimiento> findByCasa(Casa casa);

  @Query("select m from Movimiento m where m.casa = ?1 and m.fecha >= ?2 and m.fecha <= ?3")
  public List<Movimiento> findByCasaBetweenFechas(Casa casa, Date fechaIni, Date fechaFin);
}
