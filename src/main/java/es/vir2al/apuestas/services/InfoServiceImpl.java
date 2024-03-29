package es.vir2al.apuestas.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.vir2al.apuestas.constantes.ConstApp;
import es.vir2al.apuestas.dtos.Responses.AvisoResponse;
import es.vir2al.apuestas.dtos.Responses.InfoDiaResponse;
import es.vir2al.apuestas.models.Apuesta;
import es.vir2al.apuestas.models.ApuestaVirtual;
import es.vir2al.apuestas.repositories.ApuestasDAO;
import es.vir2al.apuestas.repositories.ApuestasVirtualDAO;

/**
 * InfoServiceImpl
 */
@Service
public class InfoServiceImpl implements InfoService {

  private SimpleDateFormat formateador = new SimpleDateFormat(ConstApp.INPUT_DATE_FORMAT);

  @Autowired
  private ApuestasDAO apuestasDAO;

  @Autowired
  private ApuestasVirtualDAO apuestasVirtualDAO;

  @Override
  @Transactional(readOnly = true)
  public InfoDiaResponse getInfoDia() throws Exception {

    InfoDiaResponse infoDia = new InfoDiaResponse();
    List<Apuesta> lstApuestasDia = null;

    // Obtener todas las apuestas del día en curso
    infoDia.setFecha(this.formateador.format(new Date()));
    lstApuestasDia = this.apuestasDAO.findByFechaEvento(new Date());

    // Obtener los datos desde la lista de apuestas del día
    for (Apuesta apuesta : lstApuestasDia) {

      infoDia.setNumApuestasTotal(infoDia.getNumApuestasTotal() + 1);
      infoDia.setImporteTotal(infoDia.getImporteTotal() + apuesta.getImporte());

      if (apuesta.getEstado().getId() == (long) ConstApp.ESTADO_GANADA) {
        infoDia.setNumApuestasGanadas(infoDia.getNumApuestasGanadas() + 1);
        infoDia.setImporteGanado(infoDia.getImporteGanado() + apuesta.getGanancia());
      } else if (apuesta.getEstado().getId() == (long) ConstApp.ESTADO_PERDIDA) {
        infoDia.setNumApuestasPerdidas(infoDia.getNumApuestasPerdidas() + 1);
        infoDia.setImportePerdido(infoDia.getImportePerdido() + apuesta.getGanancia());
      } else if (apuesta.getEstado().getId() <= (long) ConstApp.ESTADO_ENCURSO) {
        infoDia.setNumApuestasPendientes(infoDia.getNumApuestasPendientes() + 1);
      } else if (apuesta.getEstado().getId() <= (long) ConstApp.ESTADO_PARCIAL) {
        infoDia.setNumApuestasOtras(infoDia.getNumApuestasOtras() + 1);
        if (apuesta.getGanancia() > 0) {
          infoDia.setImporteGanado(infoDia.getImporteGanado() + apuesta.getGanancia());
        } else {
          infoDia.setImportePerdido(infoDia.getImportePerdido() + apuesta.getGanancia());
        }
      } else {
        infoDia.setNumApuestasOtras(infoDia.getNumApuestasOtras() + 1);
      }

    }

    // calcular ganancia -> ganado - perdido
    infoDia.setImporteGanancia(infoDia.getImporteGanado() + infoDia.getImportePerdido());

    return infoDia;
  }

  @Override
  @Transactional(readOnly = true)
  public List<AvisoResponse> getAvisos() throws Exception {

    List<Apuesta> lstApuestas = null;
    List<ApuestaVirtual> lstApuestasVirtuales = null;
    List<AvisoResponse> lstAvisos = new ArrayList<AvisoResponse>();

    // buscar apuestas pendientes
    lstApuestas = this.apuestasDAO.findPendientes();

    for (Apuesta apuesta : lstApuestas) {
      
      lstAvisos.add(new AvisoResponse("APUESTA PENDIENTE", apuesta.getId()+" - "+apuesta.getFechaEvento()+" - "+apuesta.getDescripcion()));

    }

    // buscar apuestas virtuales pendientes
    lstApuestasVirtuales = this.apuestasVirtualDAO.findPendientes();

    for (ApuestaVirtual apuesta : lstApuestasVirtuales) {
      
      lstAvisos.add(new AvisoResponse("APUESTA VIRTUAL PENDIENTE", apuesta.getId()+" - "+apuesta.getFechaEvento()+" - "+apuesta.getDescripcion()));

    }
    return lstAvisos;
  }

  
}
