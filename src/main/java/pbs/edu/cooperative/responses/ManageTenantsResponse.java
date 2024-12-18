package pbs.edu.cooperative.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManageTenantsResponse {
    private int tenantId;
    private String pesel;
    private String name;
    private String surname;
    private String phoneNumber;
    private Boolean isBacklog;
    private int tenantsNumber;
    private String mail;
    private int flatNumber;
    private int staircaseNumber;
    private int blockNumber;
    private String street;
    private String city;

}

