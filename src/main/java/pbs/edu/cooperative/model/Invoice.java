package pbs.edu.cooperative.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tenant_id", referencedColumnName = "tenant_id", nullable = false)
    private Tenant tenant ;

    @Column(name = "invoice_category", nullable = false)
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
