package laboratory.laboratory.repository;

import laboratory.laboratory.domain.ClassOfStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassOfStudentsRepository extends JpaRepository<ClassOfStudents, Long> {
}
