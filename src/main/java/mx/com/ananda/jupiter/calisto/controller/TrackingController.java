package mx.com.ananda.jupiter.calisto.controller;

import mx.com.ananda.jupiter.calisto.services.interfaces.ITrackingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ananda/jupiter/calisto/tracking")
public class TrackingController {

    @Autowired
    private ITrackingDataService sTracking;

    @GetMapping("")
    public ResponseEntity<?> listarTracking(){
        return new ResponseEntity<>(sTracking.findAllTracking(), HttpStatus.OK);
    }

    @GetMapping("/numeroGuia")
    public ResponseEntity<?> obtenerPedidoPorNumeroGuia(@RequestParam String numeroGuia){
        return new ResponseEntity<>(sTracking.findByTrackingByWaybill(numeroGuia),HttpStatus.OK);
    }

    @GetMapping("/numeroRastreo")
    public ResponseEntity<?> obtenerPedidoPorNumeroRastreo(@RequestParam String numeroRastreo){
        return new ResponseEntity<>(sTracking.findByTrackingByShortWaybillId(numeroRastreo),HttpStatus.OK);
    }
}
