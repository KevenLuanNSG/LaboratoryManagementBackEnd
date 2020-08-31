package laboratory.laboratory.service;

import laboratory.laboratory.domain.Semester;
import laboratory.laboratory.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository){
        this.semesterRepository = semesterRepository;
    }

    @Transactional
    public Semester registerSemester(Semester semester){
        return this.semesterRepository.save(semester);
    }
}
