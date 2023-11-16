package de.swtp13.creditportbackend.universities;
import de.swtp13.creditportbackend.universities.University;
import de.swtp13.creditportbackend.universities.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;

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
}
