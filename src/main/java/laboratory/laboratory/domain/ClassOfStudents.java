package laboratory.laboratory.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class ClassOfStudents extends BasicClass{
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Period period;
    @Column(nullable = false)
    private int numberOfStudents;

    @ManyToMany
    @JoinTable(name = "class_discipline",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "discipline_id"))
    private List<Discipline> disciplineList;

}
