package API.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidatedUserDTO {

    private UUID id;
    private String username;
    private String password;
    private String role;
    private List<UUID> citizensIDList;

}
