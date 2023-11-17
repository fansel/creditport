package de.swtp13.creditportbackend.universities;
import de.swtp13.creditportbackend.universities.University;
import de.swtp13.creditportbackend.universities.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping
    public List<University> getAllUniversities(@RequestParam(required = false) String name) {
        if (name != null && !name.isEmpty()) {
            System.out.println("Get Universities with uniName like: " + name);
            return universityRepository.findByUniNameContainingIgnoreCase(name.toLowerCase());
        } else {
            System.out.println("Get all Universities");
            return universityRepository.findAll();
        }
    }

  /*  @PostMapping
    public University createUniversity(@RequestBody University university) {
        System.out.println("Create University: " + university.getUniName());
        return universityRepository.save(university);
    }*/






}
