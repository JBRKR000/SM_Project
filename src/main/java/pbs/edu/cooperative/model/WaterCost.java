package pbs.edu.cooperative.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="water_cost")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WaterCost {
    @Id
    private Long cost_id;
    @Column(name = "cost", nullable = false)
    private float cost;
}
