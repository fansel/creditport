package de.swtp13.creditportbackend.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

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
            return "     data:\n" +
                    "     [\n" +
                    "     {\n" +
                    "     createdAt : "+ new Date() + ",\n" +
                    "     university : \"Universität Leipzig\",\n" +
                    "     course : \"Informatik Bachelor\",\n" +
                    "     status : 1,\n" +
                    "     requestCount : 5,\n" +
                    "     },\n" +
                    "     {\n" +
                    "     createdAt : new Date(),\n" +
                    "     university : \"Universität Leipzig\",\n" +
                    "     course : \"Physik Bachelor\",\n" +
                    "     status : 1,\n" +
                    "     requestCount : 5,\n" +
                    "     },\n" +
                    "     ],\n" +
                    "     count: 2,\n" +
                    "     };";
        }
    }
