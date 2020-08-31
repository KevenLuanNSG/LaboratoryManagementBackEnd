package laboratory.laboratory.service;

import laboratory.laboratory.domain.Teacher;
import laboratory.laboratory.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    @Transactional
    public Teacher registerTeacher(Teacher teacher){
        return this.teacherRepository.save(teacher);
    }
}
