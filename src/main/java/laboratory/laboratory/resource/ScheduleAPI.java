package laboratory.laboratory.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import laboratory.laboratory.domain.Schedule;
import laboratory.laboratory.service.ScheduleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "schedule")
public class ScheduleAPI {
    private final ScheduleService scheduleService;

    public ScheduleAPI(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @ApiOperation(value = "Auto generate schedule for all laboratory")
    @GetMapping(path = "autoGenerate/allLaboratory")
    public ResponseEntity<String> autoGenarateSchedule(@RequestParam(name = "semesterId", defaultValue = "0") Long semesterId) {
        return ResponseEntity.ok(this.scheduleService.autoGenerateScheduleAllLaboratory(semesterId));
    }

    @ApiOperation(value = "Register a schedule")
    @PostMapping(path = "register/simple")
    public ResponseEntity<Optional<Schedule>> registerSchedule(@RequestBody Schedule scheduleToRegister) {
        return ResponseEntity.ok(this.scheduleService.registerSchedule(scheduleToRegister));
    }

    @ApiOperation(value = "Cancel a schedule")
    @GetMapping(path = "cancel/simple")
    public ResponseEntity<Optional<Schedule>> cancelSchedule(@RequestParam(name = "scheduleId", defaultValue = "0") Long scheduleId) {
        return ResponseEntity.ok(this.scheduleService.cancelSchedule(scheduleId));
    }

    @ApiOperation(value = "Register a recurrent schedule")
    @PostMapping(path = "register/recurrent")
    public ResponseEntity<Optional<Schedule>> registerScheduleRecurrent (@RequestBody Schedule scheduleToRegister){
        return ResponseEntity.ok(this.scheduleService.registerScheduleRecurrent(scheduleToRegister));
    }

    @ApiOperation(value = "Cancel a recurrent schedule")
    @GetMapping(path = "cancel/recurrent")
    public ResponseEntity<Optional<Schedule>> cancelScheduleRecurrent (@RequestParam(name = "scheduleId", defaultValue = "0") Long scheduleId){
        return ResponseEntity.ok(this.scheduleService.cancelScheduleRecurrent(scheduleId));
    }

    @ApiOperation(value = "View schedules by day")
    @GetMapping(path = "view/day")
    public ResponseEntity<List<Schedule>> scheduleListByDay (@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
        return ResponseEntity.ok(this.scheduleService.scheduleListByDay(LocalDateTime.of(date, LocalTime.MIN)));
    }

    @ApiOperation(value = "View schedules by day and laboratory")
    @GetMapping(path = "view/dayAndLaboratory")
    public ResponseEntity<List<Schedule>> scheduleListByDayAndLaboratory (@RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date,
                                                                          @RequestParam(name = "laboratoryId", defaultValue = "0") Long laboratoryId){
        return ResponseEntity.ok(this.scheduleService.scheduleListByDayAndLaboratory(LocalDateTime.of(date, LocalTime.MIN), laboratoryId));
    }

    @ApiOperation(value = "View schedules by teacher")
    @GetMapping(path = "view/teacher")
    public ResponseEntity<Page<Schedule>> scheduleByTeacher (@RequestParam(name = "teacherId", defaultValue = "0") Long teacherId,
                                                             @RequestParam (name = "page", defaultValue = "0") int page,
                                                             @RequestParam (name = "pageSize", defaultValue = "10") int pageSize,
                                                             @RequestParam (name = "sort", defaultValue = "date")String sort){
        return ResponseEntity.ok(this.scheduleService.schedulePageByTeacher(teacherId, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sort))));
    }

    @ApiOperation(value = "View schedules by teacher and laboratory")
    @GetMapping(path = "view/teacher/laboratory")
    public ResponseEntity<Page<Schedule>> schedulePageByTeacherAndLaboratory (@RequestParam (name = "teacherId", defaultValue = "0")Long teacherId,
                                                                              @RequestParam (name = "laboratoryId", defaultValue = "0") Long laboratoryId,
                                                                              @RequestParam (name = "page", defaultValue = "0") int page,
                                                                              @RequestParam (name = "pageSize", defaultValue = "10") int pageSize,
                                                                              @RequestParam (name = "sort", defaultValue = "date")String sort){
        return ResponseEntity.ok(this.scheduleService.schedulePageByTeacherAndLaboratory(teacherId, laboratoryId, PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, sort))));
    }

    @ApiOperation(value = "View schedules by teacher for date")
    @GetMapping(path = "view/teacher/date")
    public ResponseEntity<List<Schedule>> scheduleByTeacherAndDate (@RequestParam (name = "teacherId", defaultValue = "0") Long teacherId,
                                                                    @RequestParam (name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
        return ResponseEntity.ok(this.scheduleService.scheduleByTeacherAndDate(teacherId, LocalDateTime.of(date, LocalTime.MIN)));
    }

    @ApiOperation(value = "View schedules by teacher and laboratory for date")
    @GetMapping(path = "view/teacher/laboratory/date")
    public ResponseEntity<List<Schedule>> scheduleByTeacherAndLaboratoryAndDate (@RequestParam (name = "teacherId", defaultValue = "0") Long teacherId,
                                                                    @RequestParam (name = "laboratoryId", defaultValue = "0") Long laboratoryId,
                                                                    @RequestParam (name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){
        return ResponseEntity.ok(this.scheduleService.scheduleByTeacherAndLaboratoryAndDate(teacherId, laboratoryId, LocalDateTime.of(date, LocalTime.MIN)));
    }
}