package pbs.edu.cooperative.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pbs.edu.cooperative.model.Tenant;

import java.util.Optional;


public interface TenantRepository extends JpaRepository<Tenant, Integer> {

    @Query("select t from Tenant t where t.isBacklog")
    Page<Tenant> findAllTenantsByIsBacklog(Pageable pageable);

    @Query("select t from Tenant t where t.flat.flatId = :flatId")
    Tenant getTenantByFlatId(int flatId);

    @Query("select t from Tenant t where t.name = :name and t.surname = :surname")
    Optional<Tenant> findTenantByNameAndSurname(String name, String surname);

    @Query("select t from Tenant t where t.isBacklog = :isBacklog")
    Page<Tenant> getTenantByIsBacklog(Boolean isBacklog, Pageable pageable);
}
