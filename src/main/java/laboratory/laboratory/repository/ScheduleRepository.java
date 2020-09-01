package laboratory.laboratory.repository;

import laboratory.laboratory.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTime(boolean available, LocalDateTime date, Semester semester, Laboratory laboratory, Shift shift, ScheduleTime scheduleTime);

    List<Schedule> findAllByDate(LocalDateTime date);

    List<Schedule> findAllByDateAndLaboratoryId(LocalDateTime date, Long laboratoryId);

    Page<Schedule> findAllByTeacherId(Long teacherId, Pageable pageable);

    Page<Schedule> findAllByTeacherIdAndLaboratoryId(Long teacherId, Long laboratoryId, Pageable pageable);

    List<Schedule> findAllByTeacherIdAndLaboratoryIdAndDate(Long teacherId, Long laboratoryId, LocalDateTime date);

    List<Schedule> findAllByTeacherIdAndDate(Long teacherId, LocalDateTime date);
}