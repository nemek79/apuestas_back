package es.vir2al.apuestas.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.vir2al.apuestas.constantes.ConstApp;
import es.vir2al.apuestas.dtos.ApuestaDTO;
import es.vir2al.apuestas.dtos.ApuestaVirtualDTO;
import es.vir2al.apuestas.dtos.DataResponse;
import es.vir2al.apuestas.dtos.ErrorResponse;
import es.vir2al.apuestas.dtos.Request.ApuestaFilterRequest;
import es.vir2al.apuestas.services.ApuestasService;
import es.vir2al.apuestas.services.ApuestasVirtualesService;

/**
 * ApuestasController
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/apuestas")
public class ApuestasController {

  @Autowired
  private ApuestasService apuestasSRV;

  @Autowired
  private ApuestasVirtualesService apuestasVirtualesSRV;

  private SimpleDateFormat formateador = new SimpleDateFormat(ConstApp.INPUT_DATE_FORMAT);

  @GetMapping("")
  public ResponseEntity<?> getAll(@RequestParam(required = false) String filter,
      @RequestParam(required = true) String fechaIni, @RequestParam(required = false) String fechaFin) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    ApuestaFilterRequest apuestaFilter = new ApuestaFilterRequest();
    List<ApuestaDTO> lstApuestas = null;

    try {

      apuestaFilter.setFechaIni(formateador.parse(fechaIni));

      if (fechaFin != null) {
        apuestaFilter.setFechaFin(formateador.parse(fechaFin));
      }

    } catch (ParseException e1) {
      errorResponse.setMensaje("Los parámetros no son correctos.");
      errorResponse.setDescripcion("La fecha de evento debe existir y ser correcta");

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    if (filter == null) {

      filter = ConstApp.FILTER_BY_FECHAEVENTO;

    }

    try {

      switch (filter.toLowerCase()) {

      case ConstApp.FILTER_BY_FECHAEVENTO:

        if (apuestaFilter == null || apuestaFilter.getFechaIni() == null) {
          errorResponse.setMensaje("Los parámetros no son correctos.");
          errorResponse.setDescripcion("La fecha de evento debe existir y ser correcta");

          return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        lstApuestas = this.apuestasSRV.findByFechaEvento(apuestaFilter.getFechaIni());

        break;

      case ConstApp.FILTER_BY_FECHAS:

        if (apuestaFilter == null || apuestaFilter.getFechaIni() == null || apuestaFilter.getFechaFin() == null) {
          errorResponse.setMensaje("Los parámetros no son correctos.");
          errorResponse.setDescripcion("Las fechas de evento deben existir y ser correctas");

          return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        lstApuestas = this.apuestasSRV.findByFechaEvento(apuestaFilter.getFechaIni());

        break;

      default:

        errorResponse.setMensaje("Los parámetros no son correctos.");
        errorResponse.setDescripcion("El parámetro de filtro [" + filter + "] no es correcto.");

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

      }

      dataResponse.setMensaje("Apuestas");
      dataResponse.setData(lstApuestas);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se han podido recuperar las apuestas");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getApuesta(@PathVariable Long id) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    ApuestaDTO apuesta = null;

    try {
      apuesta = this.apuestasSRV.findById(id);

      dataResponse.setMensaje("Apuesta");
      dataResponse.setData(apuesta);
    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido recuperar la apuesta [id=" + id + "]");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }

  @PostMapping("")
  public ResponseEntity<?> createApuesta(@Valid @RequestBody ApuestaDTO apuestaIN, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    ApuestaDTO apuesta = null;

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede crear la apuesta");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      apuesta = this.apuestasSRV.create(apuestaIN);
      dataResponse.setMensaje("Nueva apuesta");
      dataResponse.setData(apuesta);

      // se crea una apuesta virtual asociada a la apuesta realizada
      ApuestaVirtualDTO apuestaVirtual = new ApuestaVirtualDTO(apuesta);
      this.apuestasVirtualesSRV.create(apuestaVirtual);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido crear la apuesta");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateApuesta(@PathVariable Long id, @Valid @RequestBody ApuestaDTO apuestaIN,
      BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede actualizar la apuesta");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      this.apuestasSRV.update(id, apuestaIN);
      dataResponse.setMensaje("Apuesta actualizada");
      dataResponse.setData(null);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar la apuesta: " + id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // se actualiza la apuesta virtual
    try {

      ApuestaVirtualDTO apuestaVirtual = this.apuestasVirtualesSRV.findByApuestaAsociada(id);
      apuestaVirtual.setFromApuesta(apuestaIN);
      this.apuestasVirtualesSRV.update(apuestaVirtual.getId(), apuestaVirtual);

    } catch (Exception e) {
      // TODO DEJAR UN REGISTRO EN EL LOG DE ERRORES
      e.printStackTrace();
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @PutMapping("/{id}/estado")
  public ResponseEntity<?> updateEstadoApuesta(@PathVariable Long id, @RequestParam(required = true) Long estadoId) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (estadoId < 2 || estadoId > 7) {
      errorResponse.setMensaje("Error. No se puede actualizar la apuesta");
      errorResponse.setDescripcion("El estado al que se intenta actualizar no es correcto");

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    try {

      this.apuestasSRV.updateEstado(id, estadoId);
      dataResponse.setMensaje("Estado actualizado");
      dataResponse.setData(null);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar el estado: " + estadoId + " de la apuesta: " + id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // se actualiza la apuesta virtual
    try {

      ApuestaVirtualDTO apuestaVirtual = this.apuestasVirtualesSRV.findByApuestaAsociada(id);
      this.apuestasVirtualesSRV.updateEstado(apuestaVirtual.getId(), estadoId);

    } catch (Exception e) {
      // TODO DEJAR UN REGISTRO EN EL LOG DE ERRORES
      e.printStackTrace();
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

}
