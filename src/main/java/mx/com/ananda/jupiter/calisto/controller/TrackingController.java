package mx.com.ananda.jupiter.calisto.controller;

import mx.com.ananda.jupiter.calisto.services.interfaces.ITrackingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
