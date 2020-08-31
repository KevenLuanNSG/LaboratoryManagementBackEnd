package laboratory.laboratory.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Semester extends BasicClass{
    @Column(nullable = false)
    private LocalDateTime dateStart = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDateTime dateEnd = LocalDateTime.now();
}
