package laboratory.laboratory.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Discipline extends BasicClass {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn
    private Teacher teacher;
}
