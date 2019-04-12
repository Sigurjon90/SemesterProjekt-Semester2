/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.Citizen;
import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.entities.DeleteDTO;
import API.repositories.CitizensRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sigur
 */
@Service
public class CitizensService implements ICitizensService {

    @Autowired
    private CitizensRepository citizensRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create Citizen from CreateDTO
    @Override
    public CitizenDTO createCitizen(CreateDTO createDTO) {
        
        // map to citizen
        Citizen citizen = modelMapper.map(createDTO, Citizen.class);
        
        Citizen citizenCreated = citizensRepository.createCitizen(citizen);

        if (citizen != null) {
            return modelMapper.map(citizenCreated, CitizenDTO.class);
        }
        return null;
    }

    @Override
    public List<CitizenDTO> getCitizens() {
        List<Citizen> citizensList = citizensRepository.getCitizens();
        if (citizensList == null) return null;
        return citizensList.stream().map(citizen -> modelMapper.map(citizen, CitizenDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CitizenDTO findCitizen(UUID id) {

        Citizen citizen = citizensRepository.findCitizen(id);

        if (citizen != null) {
            return modelMapper.map(citizen, CitizenDTO.class);
        }

        return null;
    }

    @Override
    public List<CitizenDTO> batchUpdate(List<CitizenDTO> citizenDTOList) {

        List<Citizen> citizensList = citizenDTOList.stream().map(citizenDTO
                -> modelMapper.map(citizenDTO, Citizen.class)).collect(Collectors.toList());

        List<Citizen> citizenList = citizensRepository.batchUpdate(citizensList);

        if (citizenList != null) {
            return citizenList.stream().map(citizen
                    -> modelMapper.map(citizen, CitizenDTO.class)).collect(Collectors.toList());
        }
        return null;

    }

    @Override
    public boolean deleteCitizen(DeleteDTO deleteDTO) {
        Citizen citizen = modelMapper.map(deleteDTO, Citizen.class);
        return citizensRepository.deleteCitizen(citizen);
    }
}
