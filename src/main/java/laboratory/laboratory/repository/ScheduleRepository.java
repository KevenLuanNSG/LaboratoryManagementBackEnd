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
    Optional<Schedule> findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTime(boolean available, LocalDateTime date, Semester semester, Laboratory laboratory, Shift shift, ScheduleTime scheduleTime);

    Optional<Schedule> findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTimeAndTeacherAndDisciplineAndClassOfStudents(boolean available, LocalDateTime date, Semester semester, Laboratory laboratory, Shift shift, ScheduleTime scheduleTime, Teacher teacher, Discipline discipline, ClassOfStudents classOfStudents);

    List<Schedule> findAllByDate(LocalDateTime date);

    List<Schedule> findAllByDateAndLaboratoryId(LocalDateTime date, Long laboratoryId);

    Page<Schedule> findAllByTeacherId(Long teacherId, Pageable pageable);

    Page<Schedule> findAllByTeacherIdAndLaboratoryId(Long teacherId, Long laboratoryId, Pageable pageable);

    List<Schedule> findAllByTeacherIdAndLaboratoryIdAndDate(Long teacherId, Long laboratoryId, LocalDateTime date);

    List<Schedule> findAllByTeacherIdAndDate(Long teacherId, LocalDateTime date);

    @Query(nativeQuery = true, value = "select * from schedule where date = :date and shift = :shift")
    List<Schedule> findAllByDateAndShift(LocalDateTime date, String shift);

    @Query(nativeQuery = true, value = "select * from schedule where date = :date and shift = :shift and laboratory_id = :laboratoryId")
    List<Schedule> findAllByDateAndShiftAndLaboratory(LocalDateTime date, String shift, Long laboratoryId);

    @Query(nativeQuery = true, value = "select * from schedule where date = :date and shift = :shift and schedule_time = :scheduleTime")
    List <Schedule> findAllByDateAndShiftAndScheduleTime(LocalDateTime date, String shift, String scheduleTime);

    @Query(nativeQuery = true, value = "select * from schedule where date = :date and shift = :shift and schedule_time = :scheduleTime and laboratory_id = :laboratoryId")
    List <Schedule> findAllByDateAndShiftAndScheduleTimeAndLaboratory(LocalDateTime date, String shift, String scheduleTime, Long laboratoryId);
}