package laboratory.laboratory.service;

import laboratory.laboratory.domain.*;
import laboratory.laboratory.repository.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final SemesterRepository semesterRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final DisciplineRepository disciplineRepository;
    private final ClassOfStudentsRepository classOfStudentsRepository;
    private final TeacherRepository teacherRepository;

    public ScheduleService (ScheduleRepository scheduleRepository,
                            SemesterRepository semesterRepository,
                            LaboratoryRepository laboratoryRepository,
                            DisciplineRepository disciplineRepository,
                            ClassOfStudentsRepository classOfStudentsRepository,
                            TeacherRepository teacherRepository){
        this.scheduleRepository = scheduleRepository;
        this.semesterRepository = semesterRepository;
        this.laboratoryRepository = laboratoryRepository;
        this.disciplineRepository = disciplineRepository;
        this.classOfStudentsRepository = classOfStudentsRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public String autoGenerateScheduleAllLaboratory(Long semesterId){

        if (this.scheduleRepository.count() > 0){
            throw new IllegalArgumentException("Semester schedule already generated.");
        }

        Optional<Semester> semester = this.semesterRepository.findById(semesterId);
        if (!semester.isPresent()){
            throw new IllegalArgumentException("Semester not existent.");
        }

        List<Laboratory> laboratoryList = this.laboratoryRepository.findAll();

        for (Laboratory laboratory:laboratoryList
             ) {
            LocalDateTime date = semester.get().getDateStart();
            ArrayList<Schedule> scheduleList = new ArrayList<>();
            while (date.isBefore(semester.get().getDateEnd().plusDays(1))){
                Schedule scheduleFirstMorning = new Schedule();
                Schedule scheduleSecondMorning = new Schedule();
                Schedule scheduleFirstAfternoon = new Schedule();
                Schedule scheduleSecondAfternoon = new Schedule();
                Schedule scheduleFirstNight = new Schedule();
                Schedule scheduleSecondNight = new Schedule();

                //First time of morning
                scheduleFirstMorning.setShift(Shift.Morning);
                scheduleFirstMorning.setScheduleTime(ScheduleTime.First);
                scheduleFirstMorning.setDate(date);
                scheduleFirstMorning.setAvailable(true);
                scheduleFirstMorning.setLaboratory(laboratory);
                scheduleFirstMorning.setSemester(semester.get());
                scheduleList.add(scheduleFirstMorning);

                //Second time of morning
                scheduleSecondMorning.setShift(Shift.Morning);
                scheduleSecondMorning.setScheduleTime(ScheduleTime.Second);
                scheduleSecondMorning.setDate(date);
                scheduleSecondMorning.setAvailable(true);
                scheduleSecondMorning.setLaboratory(laboratory);
                scheduleSecondMorning.setSemester(semester.get());
                scheduleList.add(scheduleSecondMorning);

                //First time of afternoon
                scheduleFirstAfternoon.setShift(Shift.Afternoon);
                scheduleFirstAfternoon.setScheduleTime(ScheduleTime.First);
                scheduleFirstAfternoon.setDate(date);
                scheduleFirstAfternoon.setAvailable(true);
                scheduleFirstAfternoon.setLaboratory(laboratory);
                scheduleFirstAfternoon.setSemester(semester.get());
                scheduleList.add(scheduleFirstAfternoon);

                //Second time of afternoon
                scheduleSecondAfternoon.setShift(Shift.Afternoon);
                scheduleSecondAfternoon.setScheduleTime(ScheduleTime.Second);
                scheduleSecondAfternoon.setDate(date);
                scheduleSecondAfternoon.setAvailable(true);
                scheduleSecondAfternoon.setLaboratory(laboratory);
                scheduleSecondAfternoon.setSemester(semester.get());
                scheduleList.add(scheduleSecondAfternoon);

                //First time of night
                scheduleFirstNight.setShift(Shift.Night);
                scheduleFirstNight.setScheduleTime(ScheduleTime.First);
                scheduleFirstNight.setDate(date);
                scheduleFirstNight.setAvailable(true);
                scheduleFirstNight.setLaboratory(laboratory);
                scheduleFirstNight.setSemester(semester.get());
                scheduleList.add(scheduleFirstNight);

                //Second time of night
                scheduleSecondNight.setShift(Shift.Night);
                scheduleSecondNight.setScheduleTime(ScheduleTime.Second);
                scheduleSecondNight.setDate(date);
                scheduleSecondNight.setAvailable(true);
                scheduleSecondNight.setLaboratory(laboratory);
                scheduleSecondNight.setSemester(semester.get());
                scheduleList.add(scheduleSecondNight);

                date = date.plusDays(1);
            }
            this.scheduleRepository.saveAll(scheduleList);
        }
        return "Semester schedule generated with success.";
    }

    @Transactional
    public Optional<Schedule> registerSchedule (Schedule scheduleToRegister){
       Optional<Schedule> schedule = this.scheduleRepository.findById(scheduleToRegister.getId());
       if(!schedule.isPresent()){
           throw new IllegalArgumentException("Schedule not existent.");
       }
       if(!schedule.get().isAvailable()){
           throw new IllegalArgumentException("Schedule not available.");
       }

       Optional<ClassOfStudents> classOfStudents = this.classOfStudentsRepository.findById(scheduleToRegister.getClassOfStudents().getId());
       if (!classOfStudents.isPresent()){
           throw new IllegalArgumentException("Class of students not existent.");
       }
       Optional<Discipline> discipline = this.disciplineRepository.findById(scheduleToRegister.getDiscipline().getId());
       if(!discipline.isPresent()){
           throw new IllegalArgumentException("Discipline not existent.");
       }
       Optional<Laboratory> laboratory = this.laboratoryRepository.findById(scheduleToRegister.getLaboratory().getId());
       if (!laboratory.isPresent()){
           throw new IllegalArgumentException("Laboratory not existent.");
       }
       Optional<Semester> semester = this.semesterRepository.findById(scheduleToRegister.getSemester().getId());
       if(!semester.isPresent()){
           throw new IllegalArgumentException("Semester not existent.");
       }
       Optional<Teacher> teacher = this.teacherRepository.findById(scheduleToRegister.getTeacher().getId());
       if(!teacher.isPresent()){
           throw new IllegalArgumentException("Teacher not existent.");
       }
       if((semester.get().getId()) != (schedule.get().getSemester().getId())){
           throw new IllegalArgumentException("Semester is incorrect.");
       }
       if((laboratory.get().getId()) != (schedule.get().getLaboratory().getId())){
           throw new IllegalArgumentException("Laboratory is incorrect.");
       }

       if(classOfStudents.get().getNumberOfStudents() > laboratory.get().getCapacity()){
           throw new IllegalArgumentException("Laboratory does not support the number of students.");
       }

       boolean disciplineExistent = false;
        for (Discipline disciplineByClass: classOfStudents.get().getDisciplineList()
             ) {
            if (discipline.get().getId() == disciplineByClass.getId()){
                disciplineExistent = true;
            }
        }
        if (!disciplineExistent){
            throw new IllegalArgumentException("Discipline not check with discipline of class.");
        }

        if (teacher.get().getId() != discipline.get().getTeacher().getId()){
            throw new IllegalArgumentException("Teacher not check with discipline.");
        }

        schedule.get().setClassOfStudents(classOfStudents.get());
        schedule.get().setDiscipline(discipline.get());
        schedule.get().setTeacher(teacher.get());
        schedule.get().setAvailable(false);

        return schedule;
    }
}
