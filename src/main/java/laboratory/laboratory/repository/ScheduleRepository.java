package laboratory.laboratory.repository;

import laboratory.laboratory.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //Schedule
    Optional<Schedule> findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTime(boolean available, LocalDateTime date, Semester semester, Laboratory laboratory, Shift shift, ScheduleTime scheduleTime);

    Optional<Schedule> findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTimeAndTeacherAndDisciplineAndClassOfStudents(boolean available, LocalDateTime date, Semester semester, Laboratory laboratory, Shift shift, ScheduleTime scheduleTime, Teacher teacher, Discipline discipline, ClassOfStudents classOfStudents);

    List<Schedule> findAllByDate(LocalDateTime date);

    @Query(nativeQuery = true, value = "select * from schedule where date = :date and shift = :shift")
    List<Schedule> findAllByDateAndShift(LocalDateTime date, String shift);

    @Query(nativeQuery = true, value = "select * from schedule where date = :date and shift = :shift and schedule_time = :scheduleTime")
    List <Schedule> findAllByDateAndShiftAndScheduleTime(LocalDateTime date, String shift, String scheduleTime);

    @Query(nativeQuery = true, value = "select * from schedule where shift = :shift")
    Page <Schedule> findAllByShift(String shift, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from schedule where shift = :shift and schedule_time = :scheduleTime")
    Page<Schedule> findAllByShiftAndScheduleTime(String shift, String scheduleTime, Pageable pageable);

    //Laboratory
    List<Schedule> findAllByDateAndLaboratoryId(LocalDateTime date, Long laboratoryId);

    @Query(nativeQuery = true, value = "select * from schedule where date = :date and shift = :shift and schedule_time = :scheduleTime and laboratory_id = :laboratoryId")
    List <Schedule> findAllByDateAndShiftAndScheduleTimeAndLaboratory(LocalDateTime date, String shift, String scheduleTime, Long laboratoryId);

    @Query(nativeQuery = true, value = "select * from schedule where date = :date and shift = :shift and laboratory_id = :laboratoryId")
    List<Schedule> findAllByDateAndShiftAndLaboratory(LocalDateTime date, String shift, Long laboratoryId);

    Page<Schedule> findAllByLaboratoryId(Long laboratoryId, Pageable pageable);

    List<Schedule> findAllByLaboratoryIdAndDate(Long laboratoryId, LocalDateTime date);

    //Teacher
    Page<Schedule> findAllByTeacherId(Long teacherId, Pageable pageable);

    Page<Schedule> findAllByTeacherIdAndLaboratoryId(Long teacherId, Long laboratoryId, Pageable pageable);

    List<Schedule> findAllByTeacherIdAndLaboratoryIdAndDate(Long teacherId, Long laboratoryId, LocalDateTime date);

    List<Schedule> findAllByTeacherIdAndDate(Long teacherId, LocalDateTime date);

    List<Schedule> findAllByTeacherIdAndClassOfStudentsIdOrderByDate(Long teacherId, Long classId);

    List<Schedule> findAllByTeacherIdAndDisciplineId(Long teacherId, Long disciplineId);

    @Query(nativeQuery = true, value = "select * from schedule where teacher_id = :teacherId and date = :date and shift = :shift")
    List<Schedule> findAllByTeacherIdAndDateAndShift(Long teacherId, LocalDateTime date, String shift);

    @Query(nativeQuery = true, value = "select * from schedule where teacher_id = :teacherId and date = :date and shift = :shift and schedule_time = :scheduleTime")
    List<Schedule> findAllByTeacherIdAndDateAndShiftAndScheduleTime(Long teacherId, LocalDateTime date, String shift, String scheduleTime);

    @Query(nativeQuery = true, value = "select * from schedule where teacher_id = :teacherId and shift = :shift")
    Page<Schedule> findAllByTeacherIdAndShift(Long teacherId, String shift, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from schedule where teacher_id = :teacherId and shift = :shift and schedule_time = :scheduleTime")
    Page<Schedule> findAllByTeacherIdAndShiftAndScheduleTime(Long teacherId, String shift, String scheduleTime, Pageable pageable);

    //ClassOfStudents
    Page<Schedule> findAllByClassOfStudentsId(Long classId, Pageable pageable);

    Page<Schedule> findAllByClassOfStudentsIdAndLaboratoryId(Long classId, Long laboratoryId, Pageable pageable);

    List<Schedule> findAllByClassOfStudentsIdAndDate(Long classId, LocalDateTime date);

    List<Schedule> findAllByClassOfStudentsIdAndLaboratoryIdAndDate(Long classId, Long laboratoryId, LocalDateTime date);

    List<Schedule> findAllByClassOfStudentsIdAndTeacherId(Long classId, Long teacherId);

    List<Schedule> findAllByClassOfStudentsIdAndDisciplineId(Long classId, Long disciplineId);

    @Query(nativeQuery = true, value = "select * from schedule where class_of_students_id = :classId and date = :date and schedule_time = :scheduleTime")
    List<Schedule> findAllByClassOfStudentsIdAndDateAndScheduleTime(Long classId, LocalDateTime date, String scheduleTime);

    @Query(nativeQuery = true, value = "select * from schedule where class_of_students_id = :classOfStudentsId and date = :date and shift = :shift and schedule_time = :scheduleTime")
    List<Schedule> findAllByClassOfStudentsIdAndDateAndShiftAndScheduleTime(Long classOfStudentsId, LocalDateTime date, String shift, String scheduleTime);

}