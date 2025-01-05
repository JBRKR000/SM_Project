package pbs.edu.cooperative.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "water_consumption_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WaterConsumptionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int logId;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    @JsonBackReference("tenant-waterConsumptionLogs")
    private Tenant tenant;

    @Column(name = "consumption_date", nullable = false)
    private LocalDate consumptionDate;

    @Column(name = "consumption", nullable = false)
    private float consumption;

    @ManyToOne
    @JoinColumn(name = "meter_reading_id", nullable = false)
    @JsonBackReference("meterReading-waterConsumptionLogs")
    private MeterReading meterReading;

}