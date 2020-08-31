package laboratory.laboratory.resource;

import io.swagger.annotations.ApiOperation;
import laboratory.laboratory.domain.Laboratory;
import laboratory.laboratory.service.LaboratoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "laboratory")
public class LaboratoryAPI {
    private final LaboratoryService laboratoryService;

    public LaboratoryAPI(LaboratoryService laboratoryService){
        this.laboratoryService = laboratoryService;
    }

    @ApiOperation(value = "Register a laboratory")
    @PostMapping(path = "register")
    public ResponseEntity<Laboratory> registerLaboratory(@RequestBody Laboratory laboratory){
        return ResponseEntity.ok(this.laboratoryService.registerLaboratory(laboratory));
    }
}
