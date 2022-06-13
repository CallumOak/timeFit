package com.callumezmoney.timefit.controller;

import com.callumezmoney.timefit.bootstrapper.Bootstrapper;
import com.callumezmoney.timefit.payload.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("${callumezmoney.app.webapiprefix.test}")
@AllArgsConstructor
public class TestController {
    private Bootstrapper bootstrapper;

    @GetMapping("/setupdb")
    public ResponseEntity<?> setupDB(){
        bootstrapper.clearDB();
        ResponseEntity retVal = ResponseEntity.ok().build();
        try {
            bootstrapper.run();
        } catch (Exception e) {
            retVal = ResponseEntity.badRequest().body(new MessageResponse("Data bootstrapper failed to run\n"
                    + e.getMessage()));
        }
        return retVal;
    }
}
