package es.vir2al.apuestas.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import es.vir2al.apuestas.dtos.CasaDTO;
import es.vir2al.apuestas.dtos.DataResponse;
import es.vir2al.apuestas.dtos.ErrorResponse;
import es.vir2al.apuestas.services.CasasService;

/**
 * CasasController
 */
@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/casas")
public class CasasController {

  private final Logger log = LoggerFactory.getLogger(CasasController.class);

  @Autowired
  private CasasService casasSRV;

  @GetMapping("")
  public ResponseEntity<?> getAll() {

    log.debug("Devolviendo todas las casas de apuestas");

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    List<CasaDTO> lstCasas = null;

    try {

      lstCasas = this.casasSRV.findAll();
      dataResponse.setMensaje("Casas de apuesta");
      dataResponse.setData(lstCasas);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se han podido recuperar las casas de apuestas");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getCasa(@PathVariable Long id) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    CasaDTO casa = null;

    try {

      casa = this.casasSRV.findById(id);
      dataResponse.setMensaje("Casa de apuesta");
      dataResponse.setData(casa);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido recuperar la casa de apuestas [id=" + id + "]");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }

  @PostMapping("")
  public ResponseEntity<?> createTipo(@Valid @RequestBody CasaDTO casaIn, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    CasaDTO casa = null;

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede crear la casa de apuesta");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {
      casa = this.casasSRV.create(casaIn);

      dataResponse.setMensaje("Nueva casa de apuestas");
      dataResponse.setData(casa);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido crear la casa de apuestas");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.CREATED);

  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCasa(@PathVariable Long id, @Valid @RequestBody CasaDTO casaIn, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede actualizar la casa de apuestas");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      this.casasSRV.update(id,casaIn);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar el tipo de apuesta: "+id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }
  
}
