package de.swtp13.creditportbackend.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class ProcedureController {

    /**
     *
     *
     * @return a list of all procedures
     */
        @GetMapping("/procedures")
        public String result() {
            return "[]";
        }
    }