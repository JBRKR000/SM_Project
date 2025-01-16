package pbs.edu.cooperative.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pbs.edu.cooperative.model.Tenant;

import java.util.List;
import java.util.Optional;


public interface TenantRepository extends JpaRepository<Tenant, Integer> {

    @Query("select t from Tenant t where t.isBacklog")
    Page<Tenant> findAllTenantsByIsBacklog(Pageable pageable);

    @Modifying
    @Query("delete from Tenant t where t.tenantId = :id")
    void deleteTenantById(@Param("id") Integer id);

    @Modifying
    @Query("delete from User u where u.tenant.tenantId = :tenantId")
    void deleteUserByTenantId(int tenantId);

    @Query("select t from Tenant t where t.flat.flatId = :flatId")
    Tenant getTenantByFlatId(int flatId);

    @Query("select t from Tenant t where t.name = :name and t.surname = :surname")
    Optional<Tenant> findTenantByNameAndSurname(String name, String surname);

    @Query("select t from Tenant t where t.isBacklog = :isBacklog")
    Page<Tenant> getTenantByIsBacklog(Boolean isBacklog, Pageable pageable);

    @Query("SELECT t FROM Tenant t LEFT JOIN User u ON t.tenantId = u.tenant.tenantId WHERE u.tenant.tenantId IS NULL")
    List<Tenant> findTenantWithoutUser();

    @Query("SELECT t FROM Tenant t INNER JOIN User u ON t.tenantId = u.tenant.tenantId")
    Page<Tenant> findTenantWithUser(Pageable pageable);
}
