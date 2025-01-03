package pbs.edu.cooperative.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pbs.edu.cooperative.model.Flat;
import pbs.edu.cooperative.model.Tenant;

import java.util.List;


public interface FlatRepository extends JpaRepository<Flat, Integer> {

    @Query("SELECT f.flatId FROM Flat f WHERE f.apartmentStaircase.apartmentStaircaseId = :entranceId AND f.tenant IS NULL")
    List<Integer> findUnoccupiedFlatsByEntranceId(int entranceId);


    @Query("SELECT f FROM Flat f WHERE f.apartmentStaircase.apartmentStaircaseId = :staircaseId")
    Page<Flat> getFlatsByApartmentStaircaseId(int staircaseId, Pageable pageable);

    @Query("SELECT f.flatId FROM Flat f WHERE f.tenant.tenantId = :tenantId")
    Integer getFlatIdByTenantId(int tenantId);

    @Query("SELECT f FROM Flat f WHERE f.tenant.tenantId = :tenantId")
    Flat getFlatByTenantId(int tenantId);
}
