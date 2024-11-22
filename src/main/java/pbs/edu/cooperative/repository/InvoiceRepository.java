package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbs.edu.cooperative.model.Invoice;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}
