package API.services;

import API.entities.Event;
import API.entities.EventDTO;
import API.entities.Journal;
import API.entities.JournalDTO;
import API.repositories.JournalRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author thinkbuntu
 */
// SERVICE LAYER -> Reciever of data from repository
// Manipulates the data and sends it to the controller
@Service
public class JournalService {

    @Autowired
    private JournalRepository jr;

    @Autowired
    private ModelMapper mm;
    
    public List<JournalDTO> getJournals() {
        List<Journal> journalList = jr.getJournals();
        List<JournalDTO> journalDTOList = new ArrayList();
        for(Journal journal: journalList) {
            journalDTOList.add(mm.map(journal, JournalDTO.class));
        }
        return journalDTOList;
    }
    
    // Delete Journal
    public void deleteJournal(UUID id) {
        jr.deleteJournal(id);
        /*Journal journal = jr.deleteJournal(id);
        if (journal != null) {
            return mm.map(journal, JournalDTO.class);
        }
        return null; */
    }

    // Find Journal
    public JournalDTO findJournal(UUID id) {
        Journal journal = jr.findJournal(id);
        if (journal != null) {
            return mm.map(journal, JournalDTO.class);
        }
        return null;
    }
    
    /*
        Maps Event to EventDTO
        Returns EventDTO or null
    */
    public EventDTO modifyJournal(JournalDTO journalDTO) {
        Event event = jr.modifyJournal(journalDTO);
        if(event != null) {
            return mm.map(event, EventDTO.class);
        }
        return null;
    }
    
    // CREATE JOURNAL
    
    public JournalDTO createJournal(JournalDTO journalDTO) {
        Journal journal = mm.map(journalDTO, Journal.class);
        Journal journalCreated = jr.createJournal(journal);
        if(journalCreated != null) {
            return mm.map(journalCreated, JournalDTO.class);
        }
        
        return null;
    }
    
    

}
