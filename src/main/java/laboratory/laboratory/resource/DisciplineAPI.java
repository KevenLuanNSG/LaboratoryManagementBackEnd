package laboratory.laboratory.resource;

import io.swagger.annotations.ApiOperation;
import laboratory.laboratory.domain.Discipline;
import laboratory.laboratory.domain.Teacher;
import laboratory.laboratory.service.DisciplineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "discipline")
public class DisciplineAPI {
    private final DisciplineService disciplineService;

    public DisciplineAPI (DisciplineService disciplineService){
        this.disciplineService = disciplineService;
    }

    @ApiOperation(value = "Register a discipline")
    @PostMapping(path = "register")
    public ResponseEntity<Discipline> registerDiscipline(@RequestBody Discipline discipline){
        return ResponseEntity.ok(this.disciplineService.registerDiscipline(discipline));
    }
}
