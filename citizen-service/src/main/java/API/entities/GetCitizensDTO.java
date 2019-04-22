package API.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCitizensDTO {

    private List<CitizenDTO> primaryCitizens;
    private List<CitizenDTO> otherCitizens;

}
