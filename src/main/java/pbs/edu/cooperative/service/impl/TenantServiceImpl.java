package pbs.edu.cooperative.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pbs.edu.cooperative.model.Tenant;
import pbs.edu.cooperative.repository.TenantRepository;
import pbs.edu.cooperative.responses.ManageTenantsResponse;
import pbs.edu.cooperative.service.TenantService;

import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;


    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Optional<Tenant> getTenantById(int id) {
        return tenantRepository.findById(id);
    }

    @Override
    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    @Override
    public Page<Tenant> getAllTenants(Pageable pageable) {
        return tenantRepository.findAll(pageable);
    }

    @Override
    public void deleteTenantById(int id) {
        tenantRepository.deleteById(id);
    }

    @Override
    public void deleteTenant(Tenant tenant) {
        tenantRepository.delete(tenant);
    }

    @Override
    public Page<Tenant> getAllBacklogTenants(Pageable pageable) {
        return tenantRepository.findAllTenantsByIsBacklog(pageable);
    }

    @Override
    public Page<ManageTenantsResponse> getAllManageTenants(Pageable pageable) {
        return tenantRepository.findAll(pageable)
                .map(tenant -> new ManageTenantsResponse(
                        tenant.getTenantId(),
                        tenant.getPesel(),
                        tenant.getName(),
                        tenant.getSurname(),
                        tenant.getPhoneNumber(),
                        tenant.getIsBacklog(),
                        tenant.getTenantsNumber(),
                        tenant.getMail(),
                        tenant.getFlat().getFlatNumber(),
                        tenant.getFlat().getApartmentStaircase().getStaircaseNumber(),
                        tenant.getFlat().getApartmentStaircase().getBlock().getBuildingNumber(),
                        tenant.getFlat().getApartmentStaircase().getBlock().getStreet(),
                        tenant.getFlat().getApartmentStaircase().getBlock().getCity()
                         // Uzyskanie bloku z Flat
                        // Uzyskanie klatki z Flat
                ));
    }

    @Override
    public Tenant getTenantByFlatId(int flatId) {
        return tenantRepository.getTenantByFlatId(flatId);
    }
}
