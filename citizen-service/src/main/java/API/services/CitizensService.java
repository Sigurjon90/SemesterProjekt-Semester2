/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import API.repositories.ICitizensRepository;
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
    private ICitizensRepository citizensRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create Citizen from CreateDTO
    public CitizenDTO createCitizen(CreateDTO createDTO, UUID authorId, UUID careCenterId) {
        // Map to citizen
        Citizen citizen = modelMapper.map(createDTO, Citizen.class);
        Citizen citizenCreated = citizensRepository.createCitizen(citizen, authorId, careCenterId);

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
    public GetCitizensDTO getMyCitizens(List<UUID> listOfCitizensIds, UUID careCenterId) {
        List<Citizen> myCitizens = citizensRepository.getMyCitizens(listOfCitizensIds);
        List<Citizen> careCenterCitizens = citizensRepository.getCareCenterCitizens(careCenterId, listOfCitizensIds);

        List<CitizenDTO> myCitizensDTO = myCitizens.stream().map(citizen -> modelMapper.map(citizen, CitizenDTO.class)).collect(Collectors.toList());
        List<CitizenDTO> careCenterCitizensDTO = careCenterCitizens.stream().map(citizen -> modelMapper.map(citizen, CitizenDTO.class)).collect(Collectors.toList());
        return new GetCitizensDTO(myCitizensDTO, careCenterCitizensDTO);
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
    public List<CitizenDTO> batchUpdate(List<CitizenDTO> citizenDTOList, UUID authorId) {
        List<Citizen> citizensList = citizenDTOList.stream().map(citizenDTO
                -> modelMapper.map(citizenDTO, Citizen.class)).collect(Collectors.toList());

        List<Citizen> citizenList = citizensRepository.batchUpdate(citizensList, authorId);

        if (citizenList != null) {
            return citizenList.stream().map(citizen
                    -> modelMapper.map(citizen, CitizenDTO.class)).collect(Collectors.toList());
        }
        return null;

    }

    @Override
    public boolean deleteCitizen(UUID id, UUID authorId) {
        return citizensRepository.deleteCitizen(id, authorId);
    }
}
