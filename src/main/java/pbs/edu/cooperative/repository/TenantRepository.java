package pbs.edu.cooperative.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pbs.edu.cooperative.model.Tenant;


public interface TenantRepository extends JpaRepository<Tenant, Integer> {

}
