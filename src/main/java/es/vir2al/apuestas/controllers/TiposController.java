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

import es.vir2al.apuestas.dtos.DataResponse;
import es.vir2al.apuestas.dtos.ErrorResponse;
import es.vir2al.apuestas.dtos.TipoDTO;
import es.vir2al.apuestas.services.TiposService;


/**
 * TiposController
 */
@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/tipos")
public class TiposController {

  private final Logger log = LoggerFactory.getLogger(TiposController.class);

  @Autowired
  private TiposService tiposSRV;

  @GetMapping("")
  public ResponseEntity<?> getAll() {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    try {

      List<TipoDTO> lstTipos = this.tiposSRV.findAll();
      dataResponse.setMensaje("Tipos de apuesta");
      dataResponse.setData(lstTipos);

    } catch (Exception e) {

      errorResponse.setMensaje("Error. No se han podido recuperar los tipos de apuestas");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTipo(@PathVariable Long id) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    try {

      TipoDTO tipo = this.tiposSRV.findById(id);
      dataResponse.setMensaje("Tipo apuesta");
      dataResponse.setData(tipo);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido recuperar el tipo de apuestas [id=" + id + "]");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> createTipo(@Valid @RequestBody TipoDTO tipoIN, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    TipoDTO tipo = null;

    this.log.info(tipoIN.toString());

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede crear el tipo de apuesta");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      tipo = this.tiposSRV.create(tipoIN);

      dataResponse.setMensaje("Nuevo tipo de apuesta");
      dataResponse.setData(tipo);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido crear el nuevo tipo de apuesta");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateTipo(@PathVariable Long id, @Valid @RequestBody TipoDTO tipoIN, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede actualizar el tipo de apuesta");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      this.tiposSRV.update(id,tipoIN);

      dataResponse.setMensaje("Tipo de apuesta actualizado");
      dataResponse.setData(null);
       
    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar el tipo de apuesta: "+id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }
  
}
