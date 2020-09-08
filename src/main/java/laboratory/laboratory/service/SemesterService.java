package laboratory.laboratory.service;

import laboratory.laboratory.domain.Semester;
import laboratory.laboratory.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository){
        this.semesterRepository = semesterRepository;
    }

    @Transactional
    public Semester registerSemester(Semester semester){
        int dayStart = semester.getDateStart().getDayOfMonth();
        int monthStart = semester.getDateStart().getMonthValue();
        int yearStart = semester.getDateStart().getYear();
        LocalDateTime dateStart = LocalDateTime.of(yearStart, monthStart, dayStart, 0,0,0,0);

        int dayEnd = semester.getDateEnd().getDayOfMonth();
        int monthEnd = semester.getDateEnd().getMonthValue();
        int yearEnd = semester.getDateEnd().getYear();
        LocalDateTime dateEnd = LocalDateTime.of(yearEnd, monthEnd, dayEnd, 0,0,0,0);

        semester.setDateStart(dateStart);
        semester.setDateEnd(dateEnd);
        return this.semesterRepository.save(semester);
    }
}
