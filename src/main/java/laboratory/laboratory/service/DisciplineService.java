package laboratory.laboratory.service;

import laboratory.laboratory.domain.Discipline;
import laboratory.laboratory.domain.Teacher;
import laboratory.laboratory.repository.DisciplineRepository;
import laboratory.laboratory.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DisciplineService {
    private final DisciplineRepository disciplineRepository;
    private final TeacherRepository teacherRepository;

    public DisciplineService(DisciplineRepository disciplineRepository,
                             TeacherRepository teacherRepository){
        this.disciplineRepository = disciplineRepository;
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public Discipline registerDiscipline(Discipline discipline){
        if(discipline.getTeacher() != null){
            Optional<Teacher> teacher = this.teacherRepository.findById(discipline.getTeacher().getId());
            if (!teacher.isPresent()){
                throw new IllegalArgumentException("Teacher not existent.");
            }
        }
        return this.disciplineRepository.save(discipline);
    }
}
