package laboratory.laboratory.repository;

import laboratory.laboratory.domain.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, Long> {
    @Query(nativeQuery = true, value = "select * from laboratory where capacity >= :numberOfStudents")
    List<Laboratory> laboratoryCompatibleWithClass(int numberOfStudents);
}
