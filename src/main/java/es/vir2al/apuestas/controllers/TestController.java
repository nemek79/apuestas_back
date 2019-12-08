package es.vir2al.apuestas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.vir2al.apuestas.dtos.DeporteDTO;
import es.vir2al.apuestas.dtos.TorneoDTO;
import es.vir2al.apuestas.services.DeportesService;

@RestController
@RequestMapping("/api/test")
public class TestController {

  private final Logger log = LoggerFactory.getLogger(TestController.class);

  @Autowired
  private DeportesService deportesSRV;

  @GetMapping("/deportes")
  public ResponseEntity<?> deportes() {

    log.debug("Inciando deportes");

    List<DeporteDTO> lstDeportes = null;
    Map<String, Object> response = new HashMap<>();

    try {

      lstDeportes = this.deportesSRV.findAllDeportes();
      response.put("data", lstDeportes);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    } catch (Exception e) {

      e.printStackTrace();
      response.put("error", e.getMessage());

    }

    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
  }

  @GetMapping("/torneos")
  public ResponseEntity<?> torneos() {

    log.debug("Inciando torneos");

    List<TorneoDTO> lstTorneos = null;
    Map<String, Object> response = new HashMap<>();

    try {

      lstTorneos = this.deportesSRV.findAllTorneos();
      response.put("data", lstTorneos);
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    } catch (Exception e) {

      e.printStackTrace();
      response.put("error", e.getMessage());

    }

    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

  }

  @PostMapping("/torneos")
  public ResponseEntity<?> createTorneo(@Valid @RequestBody TorneoDTO torneoIn, BindingResult result) {

    Map<String, Object> response = new HashMap<>();
    TorneoDTO torneoNew = null;

    if (result.hasErrors()) {
      response.put("error", "El dato de entrada no es correcto");
      response.put("description", result.getAllErrors());
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
    }

    try {
    
      torneoNew = this.deportesSRV.create(torneoIn);
      response.put("data",torneoNew);
    
    } catch (Exception e) {
      
      response.put("error", "Error al crear el torneo");
      response.put("description", e.getMessage());
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
   
    }

    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

  }
}
