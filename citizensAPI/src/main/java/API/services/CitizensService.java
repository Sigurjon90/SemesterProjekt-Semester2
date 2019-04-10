/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.Citizens;
import API.entities.CitizensDTO;
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
    private CitizensRepository cp;

    @Autowired
    private ModelMapper mm;

    public List<CitizensDTO> getCitizens() {

        List<Citizens> citizensList = cp.getCitizens();
        List<CitizensDTO> citizensDTOList = new ArrayList();

        citizensList.forEach((c) -> {
            citizensDTOList.add(mm.map(c, CitizensDTO.class));
        });

        return citizensDTOList;
    }
    
    public CitizensDTO archiveCitizens(UUID id, UUID authorId){
        
        Citizens c = cp.deleteCitizen(id, authorId);
        
        if(c != null){
            return mm.map(c, CitizensDTO.class);
        }
        
        return null;
    }
    
    public CitizensDTO findCitizen(UUID id){
        
        Citizens c = cp.findCitizen(id);
        
        if(c != null){
            return mm.map(c, CitizensDTO.class);
        }
        
        return null;
    }
    
    public CitizensDTO createCitizen(CitizensDTO cDTO){
        
        Citizens c = mm.map(cDTO, Citizens.class);
        Citizens cCreated = cp.createCitizen(c);
        
        if(cCreated != null){
            return mm.map(cCreated, CitizensDTO.class);
        }
        
        return null;
    }

}
