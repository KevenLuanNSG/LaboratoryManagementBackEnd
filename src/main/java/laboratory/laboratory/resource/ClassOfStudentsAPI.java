package laboratory.laboratory.resource;

import io.swagger.annotations.ApiOperation;
import laboratory.laboratory.domain.ClassOfStudents;
import laboratory.laboratory.domain.Teacher;
import laboratory.laboratory.service.ClassOfStudentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "class")
public class ClassOfStudentsAPI {
    private final ClassOfStudentsService classOfStudentsService;

    public ClassOfStudentsAPI (ClassOfStudentsService classOfStudentsService){
        this.classOfStudentsService = classOfStudentsService;
    }

    @ApiOperation(value = "Register a Class of Students")
    @PostMapping(path = "register")
    public ResponseEntity<ClassOfStudents> registerClassOfStudents(@RequestBody ClassOfStudents classOfStudents){
        return ResponseEntity.ok(this.classOfStudentsService.registerClassOfStudents(classOfStudents));
    }
}
