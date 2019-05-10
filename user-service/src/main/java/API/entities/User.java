package API.entities;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID id;
    private String username;
    private String password;
    private String role;
    private boolean active = true;
    private String email;
    private String cpr;
    private String address;
    private List<UUID> citizensIDList;
    private UUID careCenterId;

    public User(UUID id, String username, boolean active, String role, String email, String address) {
        this.id = id;
        this.username = username;
        active = true;
        this.role = role;
        this.address = address;
        this.email = email;
    }

    public User(UUID id) {
        this.id = id;
    }

    public User(UUID id, String username, String email, boolean active, String address, String role, String cpr) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.active = active;
        this.address = address;
        this.role = role;
        this.cpr = cpr;
    }

    public User(UUID id, String username, String email, String address, String role, String cpr, List<UUID> citizensIDList) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.role = role;
        this.cpr = cpr;
        this.citizensIDList = citizensIDList;
    }

}
