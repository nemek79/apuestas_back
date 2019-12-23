package es.vir2al.apuestas.controllers;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.vir2al.apuestas.dtos.DataResponse;
import es.vir2al.apuestas.dtos.ErrorResponse;
import es.vir2al.apuestas.dtos.Responses.InfoDiaResponse;
import es.vir2al.apuestas.services.InfoService;

/**
 * InfoController
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/info")
public class InfoController {

  //private static final Logger logger = LoggerFactory.getLogger(ApuestasController.class);

  @Autowired
  private InfoService infoSRV;

  @GetMapping("")
  public ResponseEntity<?> getInfo() {

    DataResponse dataResponse = new DataResponse();
    ErrorResponse errorResponse = new ErrorResponse();

    InfoDiaResponse infoResponse = null;

    try {

      infoResponse = this.infoSRV.getInfoDia();
      dataResponse.setData(infoResponse);
      dataResponse.setMensaje("Información diaria");
    
    } catch (Exception e) {

      e.printStackTrace();

      errorResponse.setMensaje("Error. No se puede obtener la información");
      errorResponse.setDescripcion("Se ha producido un error internto al intentar obtenre la información diaria.");

      return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<DataResponse>(dataResponse, HttpStatus.OK);

  }
  
}
