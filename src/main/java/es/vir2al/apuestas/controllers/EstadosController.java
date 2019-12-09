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
import es.vir2al.apuestas.dtos.EstadoDTO;
import es.vir2al.apuestas.services.EstadosService;

/**
 * EstadosController
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/estados")
public class EstadosController {

  @Autowired
  private EstadosService estadosSRV;

  @GetMapping("")
  public ResponseEntity<?> getAll() {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    List<EstadoDTO> lstEstados = null;

    try {
      lstEstados = this.estadosSRV.findAll();

      dataResponse.setMensaje("Tipos de apuesta");
      dataResponse.setData(lstEstados);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se han podido recuperar los estados");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEstado(@PathVariable Long id) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    EstadoDTO estado = null;

    try {
      estado = this.estadosSRV.findById(id);
      dataResponse.setMensaje("Estado");
      dataResponse.setData(estado);
    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido recuperar el estado [id=" + id + "]");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> createTipo(@Valid @RequestBody EstadoDTO estadoIN, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    EstadoDTO estado = null;

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede crear el estado");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {
      estado = this.estadosSRV.create(estadoIN);

      dataResponse.setMensaje("Nuevo estado");
      dataResponse.setData(estado);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido crear el nuevo estado");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.CREATED);

  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateTipo(@PathVariable Long id, @Valid @RequestBody EstadoDTO estadoIN,
      BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede actualizar el estado porque los datos no son correctos.");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {
      
      this.estadosSRV.update(id, estadoIN);

      dataResponse.setMensaje("Estado actualizado");
      dataResponse.setData(null);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar el estado: "+id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }
}
