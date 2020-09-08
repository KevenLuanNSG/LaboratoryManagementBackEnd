package laboratory.laboratory.service;

import laboratory.laboratory.domain.*;
import laboratory.laboratory.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public Optional<Schedule> registerSchedule (Schedule scheduleToRegister, String password){
       Optional<Schedule> schedule = this.scheduleRepository.findById(scheduleToRegister.getId());
       if(!schedule.isPresent()){
           throw new IllegalArgumentException("Schedule not existent.");
       }

        LocalDateTime dateToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        if(dateToday.isAfter(schedule.get().getDate())){
            throw new IllegalArgumentException("Date to schedule is before than date actual.");
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
       if(!(teacher.get().getPassword().equals(password))){
           throw new IllegalArgumentException("Password incorrect.");
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

    @Transactional
    public Optional<Schedule> cancelSchedule (Long scheduleId, String cpf, String password){
        Optional<Schedule> schedule = this.scheduleRepository.findById(scheduleId);
        if (!schedule.isPresent()){
            throw new IllegalArgumentException("Schedule not existent.");
        }

        Optional<Teacher> teacher = this.teacherRepository.findByCpf(cpf);
        if(!teacher.isPresent()){
            throw new IllegalArgumentException("Teacher not existent.");
        }
        if (!(teacher.get().getPassword().equals(password))){
            throw new IllegalArgumentException("Password incorrect.");
        }

        LocalDateTime dateToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        if(dateToday.isAfter(schedule.get().getDate())){
            throw new IllegalArgumentException("Date to schedule is before than date actual.");
        }

        schedule.get().setTeacher(null);
        schedule.get().setDiscipline(null);
        schedule.get().setClassOfStudents(null);
        schedule.get().setAvailable(true);

        return schedule;
    }

    @Transactional
    public List<Schedule> registerScheduleRecurrent (Schedule scheduleToRegister, String password){
        Optional<Schedule> schedule = this.scheduleRepository.findById(scheduleToRegister.getId());
        if(!schedule.isPresent()){
            throw new IllegalArgumentException("Schedule not existent.");
        }

        LocalDateTime dateToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        if(dateToday.isAfter(schedule.get().getDate())){
            throw new IllegalArgumentException("Date to schedule is before than date actual.");
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
        if (!(teacher.get().getPassword().equals(password))){
            throw new IllegalArgumentException("Password incorrect.");
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

        ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
        LocalDateTime date = schedule.get().getDate();
        while (date.isBefore(semester.get().getDateEnd().plusDays(1))){

            Optional<Schedule> scheduleToRegisterRecurrent =  this.scheduleRepository.findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTime(true, date, schedule.get().getSemester(), schedule.get().getLaboratory(), schedule.get().getShift(), schedule.get().getScheduleTime() );
            if (scheduleToRegisterRecurrent.isPresent()){

                if ((scheduleToRegisterRecurrent.get().getTeacher() == null) &&
                        (scheduleToRegisterRecurrent.get().getDiscipline() == null) &&
                        (scheduleToRegisterRecurrent.get().getClassOfStudents() == null)) {
                    scheduleToRegisterRecurrent.get().setClassOfStudents(classOfStudents.get());
                    scheduleToRegisterRecurrent.get().setDiscipline(discipline.get());
                    scheduleToRegisterRecurrent.get().setTeacher(teacher.get());
                    scheduleToRegisterRecurrent.get().setAvailable(false);
                } else {
                    scheduleArrayList.add(scheduleToRegisterRecurrent.get());
                }
            } else {
                Optional<Schedule> scheduleToRegisterRecurrentFailed =  this.scheduleRepository.findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTime(false, date, schedule.get().getSemester(), schedule.get().getLaboratory(), schedule.get().getShift(), schedule.get().getScheduleTime() );
                if (scheduleToRegisterRecurrentFailed.isPresent()){
                    scheduleArrayList.add(scheduleToRegisterRecurrentFailed.get());
                }
            }

            date = date.plusDays(7);
        }

        return scheduleArrayList;
    }

    @Transactional
    public Optional<Schedule> cancelScheduleRecurrent (Long scheduleId, String cpf, String password){
        Optional<Schedule> schedule = this.scheduleRepository.findById(scheduleId);
        if (!schedule.isPresent()){
            throw new IllegalArgumentException("Schedule not existent.");
        }

        Optional<Teacher> teacherToCheck = this.teacherRepository.findByCpf(cpf);
        if (!teacherToCheck.isPresent()){
            throw new IllegalArgumentException("Teacher not existent.");
        }
        if (!(teacherToCheck.get().getPassword().equals(password))){
            throw new IllegalArgumentException("Password incorrect.");
        }

        LocalDateTime dateToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        if(dateToday.isAfter(schedule.get().getDate())){
            throw new IllegalArgumentException("Date to schedule is before than date actual.");
        }

        Teacher teacher = schedule.get().getTeacher();
        Discipline discipline = schedule.get().getDiscipline();
        ClassOfStudents classOfStudents = schedule.get().getClassOfStudents();
        LocalDateTime date = schedule.get().getDate();
        while (date.isBefore(schedule.get().getSemester().getDateEnd().plusDays(1))){
            Optional<Schedule> scheduleToCancelRecurrent =  this.scheduleRepository.findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTimeAndTeacherAndDisciplineAndClassOfStudents(false, date, schedule.get().getSemester(), schedule.get().getLaboratory(), schedule.get().getShift(), schedule.get().getScheduleTime(), teacher, discipline, classOfStudents);

            if (scheduleToCancelRecurrent.isPresent()){
                    scheduleToCancelRecurrent.get().setClassOfStudents(null);
                    scheduleToCancelRecurrent.get().setDiscipline(null);
                    scheduleToCancelRecurrent.get().setTeacher(null);
                    scheduleToCancelRecurrent.get().setAvailable(true);
            }

            date = date.plusDays(7);
        }

        return schedule;
    }

    //Schedule
    @Transactional
    public List<Schedule> scheduleByDay (LocalDateTime date){
        return this.scheduleRepository.findAllByDate(date);
    }

    @Transactional
    public List<Schedule> scheduleByDayAndLaboratory (LocalDateTime date, Long laboratoryId){
        return this.scheduleRepository.findAllByDateAndLaboratoryId(date, laboratoryId);
    }

    @Transactional
    public List<Schedule> scheduleByDateAndShift (LocalDateTime date, String shift){
        return this.scheduleRepository.findAllByDateAndShift(date, shift);
    }

    @Transactional
    public List<Schedule> scheduleByDateAndShiftAndScheduleTime (LocalDateTime date, String shift, String scheduleTime){
        return this.scheduleRepository.findAllByDateAndShiftAndScheduleTime(date, shift, scheduleTime);
    }

    @Transactional
    public Page<Schedule> findAllByShift (String shift, Pageable pageable){
        return this.scheduleRepository.findAllByShift(shift, pageable);
    }

    @Transactional
    public Page<Schedule> findAllByShiftAndScheduleTime (String shift, String scheduleTime, Pageable pageable){
        return this.scheduleRepository.findAllByShiftAndScheduleTime(shift, scheduleTime, pageable);
    }

    //Teacher
    @Transactional
    public Page<Schedule> scheduleByTeacher (Long teacherId, Pageable pageable){
        return this.scheduleRepository.findAllByTeacherId(teacherId, pageable);
    }

    @Transactional
    public Page<Schedule> scheduleByTeacherAndLaboratory(Long teacherId, Long laboratoryId, Pageable pageable){
        return this.scheduleRepository.findAllByTeacherIdAndLaboratoryId(teacherId, laboratoryId, pageable);
    }

    @Transactional
    public List<Schedule> scheduleByTeacherAndDate (Long teacherId, LocalDateTime date){
        return this.scheduleRepository.findAllByTeacherIdAndDate(teacherId, date);
    }

    @Transactional
    public List<Schedule> scheduleByTeacherAndLaboratoryAndDate (Long teacherId, Long laboratoryId, LocalDateTime date){
        return this.scheduleRepository.findAllByTeacherIdAndLaboratoryIdAndDate(teacherId, laboratoryId, date);
    }

    @Transactional
    public List<Schedule> findAllByTeacherIdAndClassOfStudentsId (Long teacherId, Long classId){
        return this.scheduleRepository.findAllByTeacherIdAndClassOfStudentsId(teacherId, classId);
    }

    @Transactional
    public List<Schedule> findAllByTeacherIdAndDisciplineId (Long teacherId, Long disciplineId){
        return this.scheduleRepository.findAllByTeacherIdAndDisciplineId(teacherId, disciplineId);
    }

    @Transactional
    public List<Schedule> findAllByTeacherIdAndDateAndShift (Long teacherId, LocalDateTime date, String shift){
        return this.scheduleRepository.findAllByTeacherIdAndDateAndShift(teacherId, date, shift);
    }

    @Transactional
    public List<Schedule> findAllByTeacherIdAndDateAndShiftAndScheduleTime (Long teacherId, LocalDateTime date, String shift, String scheduleTime){
        return this.scheduleRepository.findAllByTeacherIdAndDateAndShiftAndScheduleTime(teacherId, date, shift, scheduleTime);
    }

    @Transactional
    public Page<Schedule> findAllByTeacherIdAndShift (Long teacherId, String shift, Pageable pageable){
        return this.scheduleRepository.findAllByTeacherIdAndShift(teacherId, shift, pageable);
    }

    @Transactional
    public Page<Schedule> findAllByTeacherIdAndShiftAndScheduleTime (Long teacherId, String shift, String scheduleTime, Pageable pageable){
        return this.scheduleRepository.findAllByTeacherIdAndShiftAndScheduleTime(teacherId, shift, scheduleTime, pageable);
    }

    //ClassOfStudents
    @Transactional
    public List<Schedule> scheduleCompatibleByDateAndClass (LocalDateTime date, Long classId){
        Optional<ClassOfStudents> classOfStudents = this.classOfStudentsRepository.findById(classId);
        if (!classOfStudents.isPresent()){
            throw new IllegalArgumentException("Class of students not existent");
        }
        List<Laboratory> laboratoryCompatibleWithClass = this.laboratoryRepository.laboratoryCompatibleWithClass(classOfStudents.get().getNumberOfStudents());

        ArrayList<Schedule> scheduleOfLaboratoryCompatible = new ArrayList<>();
        if(laboratoryCompatibleWithClass.size() > 0){
            for (Laboratory laboratory:laboratoryCompatibleWithClass
                 ) {
                List<Schedule> scheduleList = this.scheduleRepository.findAllByDateAndLaboratoryId(date, laboratory.getId());
                for (Schedule schedule:scheduleList
                     ) {
                    scheduleOfLaboratoryCompatible.add(schedule);
                }
                }
        }
        return scheduleOfLaboratoryCompatible;
    }

    @Transactional
    public List<Schedule> scheduleCompatibleByDateAndClassTime (LocalDateTime date, Long classId){
        Optional<ClassOfStudents> classOfStudents = this.classOfStudentsRepository.findById(classId);
        if (!classOfStudents.isPresent()){
            throw new IllegalArgumentException("Class of students not existent");
        }
        List<Laboratory> laboratoryCompatibleWithClass = this.laboratoryRepository.laboratoryCompatibleWithClass(classOfStudents.get().getNumberOfStudents());

        ArrayList<Schedule> scheduleOfLaboratoryCompatible = new ArrayList<>();
        if(laboratoryCompatibleWithClass.size() > 0){
            for (Laboratory laboratory:laboratoryCompatibleWithClass
            ) {
                LocalDateTime dateStart = date.minusDays(3);
                while (dateStart.isBefore(date.plusDays(4))){
                    if(!(dateStart.isBefore(LocalDateTime.now().minusDays(1)))){
                        List<Schedule> scheduleList = this.scheduleRepository.findAllByDateAndLaboratoryId(dateStart, laboratory.getId());
                        for (Schedule schedule:scheduleList
                        ) {
                            scheduleOfLaboratoryCompatible.add(schedule);
                        }
                    }

                    dateStart = dateStart.plusDays(1);
                }
            }
        }
        return scheduleOfLaboratoryCompatible;
    }

    @Transactional
    public List<Schedule> scheduleCompatibleWithClassByDateAndShiftAndScheduleTime (Long classId, LocalDateTime date, String shift, String scheduleTime){
        Optional<ClassOfStudents> classOfStudents = this.classOfStudentsRepository.findById(classId);
        if(!classOfStudents.isPresent()){
            throw new IllegalArgumentException("Class of students not existent.");
        }

        List<Laboratory> laboratoryCompatibleWithClass = this.laboratoryRepository.laboratoryCompatibleWithClass(classOfStudents.get().getNumberOfStudents());

        ArrayList<Schedule> scheduleOfLaboratoryCompatible =  new ArrayList<>();

        if (laboratoryCompatibleWithClass.size() > 0){
            for (Laboratory laboratory:laboratoryCompatibleWithClass
                 ) {
                List<Schedule> scheduleList = this.scheduleRepository.findAllByDateAndShiftAndScheduleTimeAndLaboratory(date, shift, scheduleTime, laboratory.getId());
                for (Schedule schedule:scheduleList
                     ) {
                    scheduleOfLaboratoryCompatible.add(schedule);
                }
            }
        }
        return scheduleOfLaboratoryCompatible;
    }

    @Transactional
    public List<Schedule> scheduleCompatibleWithClassByDateAndShift (Long classId, LocalDateTime date, String shift){
        Optional<ClassOfStudents> classOfStudents = this.classOfStudentsRepository.findById(classId);
        if(!classOfStudents.isPresent()){
            throw new IllegalArgumentException("Class of students not existent.");
        }

        List<Laboratory> laboratoryCompatibleWithClass = this.laboratoryRepository.laboratoryCompatibleWithClass(classOfStudents.get().getNumberOfStudents());

        ArrayList<Schedule> scheduleOfLaboratoryCompatible =  new ArrayList<>();

        if (laboratoryCompatibleWithClass.size() > 0){
            for (Laboratory laboratory:laboratoryCompatibleWithClass
            ) {
                List<Schedule> scheduleList = this.scheduleRepository.findAllByDateAndShiftAndLaboratory(date, shift, laboratory.getId());
                for (Schedule schedule:scheduleList
                ) {
                    scheduleOfLaboratoryCompatible.add(schedule);
                }
            }
        }
        return scheduleOfLaboratoryCompatible;
    }

    @Transactional
    public Page<Schedule> findAllByClassOfStudentsId(Long classId, Pageable pageable){
        return this.scheduleRepository.findAllByClassOfStudentsId(classId, pageable);
    }

    @Transactional
    public Page<Schedule> findAllByClassOfStudentsIdAndLaboratoryId(Long classId, Long laboratoryId, Pageable pageable){
        return this.scheduleRepository.findAllByClassOfStudentsIdAndLaboratoryId(classId, laboratoryId, pageable);
    }

    @Transactional
    public List<Schedule> findAllByClassOfStudentsIdAndDate(Long classId, LocalDateTime date){
        return this.scheduleRepository.findAllByClassOfStudentsIdAndDate(classId, date);
    }

    @Transactional
    public List<Schedule> findAllByClassOfStudentsIdAndLaboratoryIdAndDate(Long classId, Long laboratoryId, LocalDateTime date){
        return this.scheduleRepository.findAllByClassOfStudentsIdAndLaboratoryIdAndDate(classId, laboratoryId, date);
    }

    @Transactional
    public List<Schedule> findAllByClassOfStudentsIdAndTeacherId(Long classId, Long teacherId){
        return this.scheduleRepository.findAllByClassOfStudentsIdAndTeacherId(classId, teacherId);
    }

    @Transactional
    public List<Schedule> findAllByClassOfStudentsIdAndDisciplineId(Long classId, Long disciplineId){
        return this.scheduleRepository.findAllByClassOfStudentsIdAndDisciplineId(classId, disciplineId);
    }

    @Transactional
    public List<Schedule> findAllByClassOfStudentsIdAndDateAndScheduleTime(Long classId, LocalDateTime date, String scheduleTime){
        return this.scheduleRepository.findAllByClassOfStudentsIdAndDateAndScheduleTime(classId, date, scheduleTime);
    }

    //Laboratory
    @Transactional
    public Page<Schedule> findAllByLaboratoryId (Long laboratoryId, Pageable pageable){
        return this.scheduleRepository.findAllByLaboratoryId(laboratoryId, pageable);
    }

    @Transactional
    public List<Schedule> findAllByLaboratoryIdAndDate (Long laboratoryId, LocalDateTime date){
        return this.scheduleRepository.findAllByLaboratoryIdAndDate(laboratoryId, date);
    }
}
