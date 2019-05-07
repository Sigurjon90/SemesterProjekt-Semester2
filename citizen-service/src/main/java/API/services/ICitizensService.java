/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.entities.GetCitizensDTO;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author thinkbuntu
 */
public interface ICitizensService {

    List<CitizenDTO> batchUpdate(List<CitizenDTO> citizenDTOList, UUID authorId);

    // Create Citizen from CreateDTO
    CitizenDTO createCitizen(CreateDTO createDTO, UUID authorId, UUID careCenterId);

    boolean deleteCitizen(UUID id, UUID authorId);

    CitizenDTO findCitizen(UUID id);

    List<CitizenDTO> getCitizens();

    GetCitizensDTO getMyCitizens(List<UUID> listOfCitizensIds, UUID careCenterId);
}
