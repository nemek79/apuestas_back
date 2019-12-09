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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.vir2al.apuestas.constantes.ConstApp;
import es.vir2al.apuestas.dtos.DataResponse;
import es.vir2al.apuestas.dtos.ErrorResponse;
import es.vir2al.apuestas.dtos.MovimientoDTO;
import es.vir2al.apuestas.dtos.Request.MovimientoFilterRequest;
import es.vir2al.apuestas.services.MovimientosService;

/**
 * MovimientosController
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/movimientos")
public class MovimientosController {

  @Autowired
  private MovimientosService movientosSRV;

  @GetMapping("")
  public ResponseEntity<?> getAll(@RequestParam(required = false) String filter,
      @Valid @RequestBody(required = false) MovimientoFilterRequest movimientoFilter) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    List<MovimientoDTO> lstMovimientos = null;

    if (filter == null) {
      filter = ConstApp.FILTER_ALL_MOVIMIENTOS;
    }

    try {

      switch (filter.toLowerCase()) {

      case ConstApp.FILTER_BY_CASA:

        if (movimientoFilter == null || movimientoFilter.getCasa() == null) {

          errorResponse.setMensaje("Los parámetros no son correctos.");
          errorResponse.setDescripcion("La casa de apuesta debe existir y ser correcta.");

          return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        lstMovimientos = this.movientosSRV.findByCasa(movimientoFilter.getCasa());

        break;

      case ConstApp.FILTER_BY_FECHAS:

        if (movimientoFilter == null || movimientoFilter.getFechaFin() == null
            || movimientoFilter.getFechaIni() == null) {

          errorResponse.setMensaje("Los parámetros no son correctos.");
          errorResponse.setDescripcion("La fechas deben ser correctas.");

          return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        lstMovimientos = this.movientosSRV.findByFechas(movimientoFilter.getFechaIni(), movimientoFilter.getFechaFin());

        break;

      case ConstApp.FILTER_BY_BOTH:

        if (movimientoFilter == null || movimientoFilter.getFechaFin() == null || movimientoFilter.getFechaIni() == null
            || movimientoFilter.getCasa() == null) {

          errorResponse.setMensaje("Los parámetros no son correctos.");
          errorResponse.setDescripcion("La casa y las fechas deben ser correctas.");

          return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        lstMovimientos = this.movientosSRV.findByCasaAndFechas(movimientoFilter.getCasa(),
            movimientoFilter.getFechaIni(), movimientoFilter.getFechaFin());

        break;

      case ConstApp.FILTER_ALL_MOVIMIENTOS:

        lstMovimientos = this.movientosSRV.findAll();

        break;

      default:

        errorResponse.setMensaje("Los parámetros no son correctos.");
        errorResponse.setDescripcion("El parámetro de filtro [" + filter + "] no es correcto.");

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

      }

      dataResponse.setMensaje("Movimientos");
      dataResponse.setData(lstMovimientos);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se han podido recuperar los movimientos");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getMovimiento(@PathVariable Long id) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    MovimientoDTO movimiento = null;

    try {
      movimiento = this.movientosSRV.findById(id);

      dataResponse.setMensaje("Movimiento");
      dataResponse.setData(movimiento);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido recuperar el movimiento [id=" + id + "]");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<?> createMovimiento(@Valid @RequestBody MovimientoDTO movimientoIN, BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    MovimientoDTO movimiento = null;

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede crear el movimiento");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      movimiento = this.movientosSRV.create(movimientoIN);

      dataResponse.setMensaje("Nuevo movimiento");
      dataResponse.setData(movimiento);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido crear el movimiento");
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateMovimiento(@PathVariable Long id, @Valid @RequestBody MovimientoDTO movimientoIN,
      BindingResult result) {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    if (result.hasErrors()) {

      errorResponse.setMensaje("Error. No se puede actualizar el movimiento");
      errorResponse.setDescripcion(result.getAllErrors().toString());

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    try {

      this.movientosSRV.update(id, movimientoIN);
      dataResponse.setMensaje("Movimiento actualizado");
      dataResponse.setData(null);

    } catch (Exception e) {
      errorResponse.setMensaje("Error. No se ha podido actualizar el movimiento: "+id);
      errorResponse.setDescripcion(e.getMessage());
      e.printStackTrace();
      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);
  }

}
