package laboratory.laboratory.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Laboratory extends BasicClass{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int capacity;
}
