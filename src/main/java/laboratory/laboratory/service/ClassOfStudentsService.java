package laboratory.laboratory.service;

import laboratory.laboratory.domain.ClassOfStudents;
import laboratory.laboratory.domain.Discipline;
import laboratory.laboratory.domain.Teacher;
import laboratory.laboratory.repository.ClassOfStudentsRepository;
import laboratory.laboratory.repository.DisciplineRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ClassOfStudentsService {
    private final ClassOfStudentsRepository classOfStudentsRepository;
    private final DisciplineRepository disciplineRepository;

    public ClassOfStudentsService(ClassOfStudentsRepository classOfStudentsRepository,
                                  DisciplineRepository disciplineRepository){
        this.classOfStudentsRepository = classOfStudentsRepository;
        this.disciplineRepository = disciplineRepository;
    }

    @Transactional
    public ClassOfStudents registerClassOfStudents(ClassOfStudents classOfStudents){
        if (classOfStudents.getDisciplineList() != null){
            for (Discipline discipline:classOfStudents.getDisciplineList()
                 ) {
                Optional<Discipline> disciplineOptional = this.disciplineRepository.findById(discipline.getId());
                if (!disciplineOptional.isPresent()) {
                    throw new IllegalArgumentException("Discipline not existent.");
                }
                if (disciplineOptional.get().getTeacher() == null ){
                    throw new IllegalArgumentException("Discipline without a teacher.");
                }
            }
        }
        return this.classOfStudentsRepository.save(classOfStudents);
    }
}
