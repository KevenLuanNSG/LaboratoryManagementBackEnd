package laboratory.laboratory.resource;

import io.swagger.annotations.ApiOperation;
import laboratory.laboratory.domain.Laboratory;
import laboratory.laboratory.domain.Teacher;
import laboratory.laboratory.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "teacher")
public class TeacherAPI {
    private final TeacherService teacherService;

    public TeacherAPI (TeacherService teacherService){
        this.teacherService = teacherService;
    }

    @ApiOperation(value = "Register a teacher")
    @PostMapping(path = "register")
    public ResponseEntity<Teacher> registerTeacher(@RequestBody Teacher teacher){
        return ResponseEntity.ok(this.teacherService.registerTeacher(teacher));
    }
}
