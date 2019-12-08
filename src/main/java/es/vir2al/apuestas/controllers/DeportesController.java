package es.vir2al.apuestas.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import es.vir2al.apuestas.dtos.DataResponse;
import es.vir2al.apuestas.dtos.DeporteDTO;
import es.vir2al.apuestas.dtos.ErrorResponse;
import es.vir2al.apuestas.dtos.TorneoDTO;
import es.vir2al.apuestas.services.DeportesService;

/**
 * DeportesController
 */
@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/deportes")
public class DeportesController {

  @Autowired
  private DeportesService deportesSRV;

  @GetMapping("")
  public ResponseEntity<?> getAllDeportes() {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    List<DeporteDTO> lstDeportes = null;

    try {

      lstDeportes = this.deportesSRV.findAllDeportes();
      dataResponse.setMensaje("Tipos de apuesta");
      dataResponse.setData(lstDeportes);

    } catch (Exception e) {

      errorResponse.setMensaje("Error. No se han podido recuperar los deportes");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getDeporte(@PathVariable Long id) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    DeporteDTO deporte = null;

    try {

      deporte = this.deportesSRV.findDeporteById(id);

      dataResponse.setMensaje("Deprote");
      dataResponse.setData(deporte);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido recuperar el deporte [id=" + id + "]");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> createDeporte(@Valid @RequestBody DeporteDTO deporteIN, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    DeporteDTO deporte = null;

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede crear el deporte");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      deporte = this.deportesSRV.create(deporteIN);

      dataResponse.setMensaje("Nuevo deporte");
      dataResponse.setData(deporte);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido crear el deporte");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.CREATED);

  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateDeporte(@PathVariable Long id, @Valid @RequestBody DeporteDTO deporteIN,
      BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede actualizar el deporte");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      this.deportesSRV.update(id, deporteIN);
      dataResponse.setMensaje("Deporte actualizado");
      dataResponse.setData(null);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar el deporte: " + id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  // =============================================================
  // TORNEOS
  // =============================================================

  @GetMapping("/torneos")
  public ResponseEntity<?> getAllTorneos() {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    List<TorneoDTO> lstTorneos = null;

    try {

      lstTorneos = this.deportesSRV.findAllTorneos();
      dataResponse.setMensaje("Torneos ALL");
      dataResponse.setData(lstTorneos);

    } catch (Exception e) {

      errorResponse.setMensaje("Error. No se han podido recuperar los torneos");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @GetMapping("/{id_deporte}/torneos")
  public ResponseEntity<?> getAllTorneosByDeporte(@PathVariable Long id_deporte) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    List<TorneoDTO> lstTorneos = null;
    DeporteDTO deporte = null;

    try {

      deporte = this.deportesSRV.findDeporteById(id_deporte);
      lstTorneos = this.deportesSRV.findAllTorneosByDeporte(deporte);
      dataResponse.setMensaje("Torneos DEP[" + deporte.getDescripcion() + "]");
      dataResponse.setData(lstTorneos);

    } catch (Exception e) {

      errorResponse.setMensaje("Error. No se han podido recuperar los torneos");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }

  @GetMapping("/torneos/{id}")
  public ResponseEntity<?> getTorneo(@PathVariable Long id) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    TorneoDTO torneo = null;

    try {

      torneo = this.deportesSRV.findTorneoById(id);
      dataResponse.setMensaje("Torneo");
      dataResponse.setData(torneo);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido recuperar el torneo [id=" + id + "]");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }

  @PostMapping("/torneos")
  public ResponseEntity<?> createTorneo(@Valid @RequestBody TorneoDTO torneoIN, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    TorneoDTO torneo = null;

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede crear el torneo");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      torneo = this.deportesSRV.create(torneoIN);

      dataResponse.setMensaje("Nuevo torneo");
      dataResponse.setData(torneo);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido crear el torneo");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.CREATED);
  }

  @PutMapping("/torneos")
  public ResponseEntity<?> updateTorneo(@PathVariable Long id, @Valid @RequestBody TorneoDTO torneoIN,
      BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede actualizar el tipo de apuesta");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      this.deportesSRV.update(id, torneoIN);

      dataResponse.setMensaje("Torneo actualizado");
      dataResponse.setData(null);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar el torneo: "+id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

}
