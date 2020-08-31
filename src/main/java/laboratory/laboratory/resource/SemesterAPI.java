package laboratory.laboratory.resource;

import io.swagger.annotations.ApiOperation;
import laboratory.laboratory.domain.Semester;
import laboratory.laboratory.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "semester")
public class SemesterAPI {
    private final SemesterService semesterService;

    public SemesterAPI (SemesterService semesterService){
        this.semesterService = semesterService;
    }

    @ApiOperation(value = "Register a semester")
    @PostMapping(path = "register")
    public ResponseEntity<Semester> registerSemester(@RequestBody Semester semester){
        return ResponseEntity.ok(this.semesterService.registerSemester(semester));
    }
}
