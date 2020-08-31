package laboratory.laboratory.resource;

import io.swagger.annotations.ApiOperation;
import laboratory.laboratory.service.ScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "schedule")
public class ScheduleAPI {
    private final ScheduleService scheduleService;

    public ScheduleAPI (ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @ApiOperation(value = "Auto generate schedule for all laboratory")
    @GetMapping(path = "autoGenerate/allLaboratory")
    public ResponseEntity<String> autoGenarateSchedule(@RequestParam(name = "semesterId", defaultValue = "0") Long semesterId){
        return ResponseEntity.ok(this.scheduleService.autoGenerateScheduleAllLaboratory(semesterId));
    }
}
