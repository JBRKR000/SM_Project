package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pbs.edu.cooperative.model.Accident;
import pbs.edu.cooperative.model.Tenant;

import java.awt.print.Pageable;
import java.util.List;


public interface TenantRepository extends JpaRepository<Tenant, Integer> {

}
