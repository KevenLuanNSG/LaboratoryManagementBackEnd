package laboratory.laboratory.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Schedule extends BasicClass{
    @Enumerated(EnumType.STRING)
    private Shift shift;
    @Enumerated(EnumType.STRING)
    private ScheduleTime scheduleTime;
    private LocalDateTime date;
    private boolean available = true;

    @OneToOne
    @JoinColumn
    private Laboratory laboratory;

    @OneToOne
    @JoinColumn
    private Discipline discipline;

    @OneToOne
    @JoinColumn
    private Teacher teacher;

    @OneToOne
    @JoinColumn
    private ClassOfStudents classOfStudents;

    @OneToOne
    @JoinColumn
    private Semester semester;
}
