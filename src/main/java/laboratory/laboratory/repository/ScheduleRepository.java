package laboratory.laboratory.repository;

import laboratory.laboratory.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByAvailableAndDateAndSemesterAndLaboratoryAndShiftAndScheduleTime(boolean available, LocalDateTime date, Semester semester, Laboratory laboratory, Shift shift, ScheduleTime scheduleTime);

    List<Schedule> findAllByDate(LocalDateTime date);

    List<Schedule> findAllByDateAndLaboratoryId(LocalDateTime date, Long laboratoryId);
}