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
import es.vir2al.apuestas.dtos.ErrorResponse;
import es.vir2al.apuestas.dtos.TipsterDTO;
import es.vir2al.apuestas.services.TipstersService;

/**
 * TipstersController
 */
@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/tipsters")
public class TipstersController {

  @Autowired
  private TipstersService tipstersSRV;

  @GetMapping("")
  public ResponseEntity<?> getAll() {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    List<TipsterDTO> lstTipsters = null;

    try {

      lstTipsters = this.tipstersSRV.findAllActive();
      dataResponse.setMensaje("Tipos de apuesta");
      dataResponse.setData(lstTipsters);

    } catch (Exception e) {

      errorResponse.setMensaje("Error. No se han podido recuperar los tipsters");
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

    TipsterDTO tipster = null;

    try {

      tipster = this.tipstersSRV.findById(id);
      dataResponse.setMensaje("Tipster");
      dataResponse.setData(tipster);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido recuperar el tipster [id=" + id + "]");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> createTipster(@Valid @RequestBody TipsterDTO tipsterIN, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    TipsterDTO tipster = null;

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede crear el tipster");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      tipster = this.tipstersSRV.create(tipsterIN);

      dataResponse.setMensaje("Nuevo tipster");
      dataResponse.setData(tipster);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido crear el nuevo tipster");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.CREATED);

  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateTipster(@PathVariable Long id, @Valid @RequestBody TipsterDTO tipsterIn,
      BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede actualizar el tipster");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {
      this.tipstersSRV.update(id, tipsterIn);

      dataResponse.setMensaje("Tipo de apuesta actualizado");
      dataResponse.setData(null);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar el tipster: "+id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

}
