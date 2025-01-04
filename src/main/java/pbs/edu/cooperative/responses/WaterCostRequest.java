package pbs.edu.cooperative.responses;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WaterCostRequest {
    private Long costId;
    private float cost;

}