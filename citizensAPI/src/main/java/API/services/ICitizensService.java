/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.entities.DeleteDTO;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author thinkbuntu
 */
public interface ICitizensService {

    List<CitizenDTO> batchUpdate(List<CitizenDTO> citizenDTOList);

    // Create Citizen from CreateDTO
    CitizenDTO createCitizen(CreateDTO createDTO);

    boolean deleteCitizen(DeleteDTO deleteDTO);

    CitizenDTO findCitizen(UUID id);

    List<CitizenDTO> getCitizens();
    
}
