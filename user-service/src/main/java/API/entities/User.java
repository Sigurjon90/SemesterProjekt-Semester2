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
    private String adress;
    private List<UUID> citizensIDList;
    private UUID careCenterId;

    public User(UUID id, String username, boolean active, String role, String email, String adress) {
        this.id = id;
        this.username = username;
        active = true;
        this.role = role;
        this.adress = adress;
        this.email = email;

    }

    public User(UUID id) {
        this.id = id;
    }

    public User(UUID id, String username, String email, boolean active, String adress, String role, String cpr) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.active = active;
        this.adress = adress;
        this.role = role;
        this.cpr = cpr;
    }

    public User(UUID id, String username, String email, String adress, String role, String cpr) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.adress = adress;
        this.role = role;
        this.cpr = cpr;
    }

}
