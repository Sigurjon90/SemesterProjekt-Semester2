/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.Citizen;
import API.entities.CitizenDTO;
import API.entities.CreateDTO;
import API.repositories.CitizensRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sigur
 */
@Service
public class CitizensService {

    @Autowired
    private CitizensRepository citizensRepository;

    @Autowired
    private ModelMapper modelMapper;

    
    // Create Citizen from CreateDTO
    public CitizenDTO createCitizen(CreateDTO createDTO) {
        
        Citizen citizen = citizensRepository.createCitizen(createDTO);
        
        if(citizen != null) {
            return modelMapper.map(citizen, CitizenDTO.class);
        }
        return null;
    }
    
    
    public List<CitizenDTO> getCitizens() {

        List<Citizen> citizensList = citizensRepository.getCitizens();
        List<CitizenDTO> citizensDTOList = new ArrayList();
        
        for(Citizen citizen : citizensList) {
            CitizenDTO citizenDTO = modelMapper.map(citizen, CitizenDTO.class);
                citizensDTOList.add(citizenDTO);
        }
        
 
        
        return citizensDTOList;
    }
    
    public CitizenDTO archiveCitizens(UUID id, UUID authorId){
        
        Citizen c = citizensRepository.deleteCitizen(id, authorId);
        
        if(c != null){
            return modelMapper.map(c, CitizenDTO.class);
        }
        
        return null;
    }
    
    public CitizenDTO findCitizen(UUID id){
        
        Citizen citizen = citizensRepository.findCitizen(id);
        
        if(citizen != null){
            return modelMapper.map(citizen, CitizenDTO.class);
        }
        
        return null;
    }
}
