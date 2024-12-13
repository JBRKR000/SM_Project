package pbs.edu.cooperative.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pbs.edu.cooperative.model.enums.InvoiceCategory;

import java.time.LocalDate;

@Entity
@Table(name="Invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"tenant"})
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    @JsonBackReference
    private Tenant tenant;

    @Column(name = "invoice_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceCategory invoiceCategory;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "sum", nullable = false)
    private float sum;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid;
}
