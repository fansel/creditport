package de.swtp13.creditportbackend.v1.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-endpoint")
public class demoController {

    @GetMapping("/open")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello!");
    }

    @GetMapping("/secure")
    public ResponseEntity<String> congrats() {
        return ResponseEntity.ok("congratulations for connecting!");
    }
}
