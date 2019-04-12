package API.services;

import API.entities.Journal;
import API.entities.JournalDTO;
import API.repositories.IJournalRepository;
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
public class JournalService implements IJournalService {

    @Autowired
    private IJournalRepository journalRepository;

    @Autowired
    private ModelMapper mm;

    @Override
    public List<JournalDTO> getJournals() {
        List<Journal> journalList = journalRepository.getJournals();
        List<JournalDTO> journalDTOList = new ArrayList();
        for (Journal journal : journalList) {
            journalDTOList.add(mm.map(journal, JournalDTO.class));
        }
        return journalDTOList;
    }

    // Delete Journal
    @Override
    public JournalDTO deleteJournal(UUID id, UUID authorID) {
        Journal journal = journalRepository.deleteJournal(id, authorID);

        if (journal != null) {
            return mm.map(journal, JournalDTO.class);
        }

        return null;
    }

    // Find Journal
    @Override
    public JournalDTO findJournal(UUID id) {
        Journal journal = journalRepository.findJournal(id);
        if (journal != null) {
            return mm.map(journal, JournalDTO.class);
        }
        return null;
    }

    public JournalDTO findJournaByCitizen(UUID id) {
        Journal journal = journalRepository.findJournalByCitizen(id);
        if (journal != null) {
            return mm.map(journal, JournalDTO.class);
        }
        return null;
    }

    /*
        Maps Event to EventDTO
        Returns EventDTO or null
     */
    @Override
    public JournalDTO modifyJournal(JournalDTO journalDTO) {
        Journal journal = journalRepository.modifyJournal(journalDTO);

        if (journal != null) {
            return mm.map(journal, JournalDTO.class);
        }
        return null;
    }

    // CREATE JOURNAL
    @Override
    public JournalDTO createJournal(JournalDTO journalDTO) {
        Journal journal = mm.map(journalDTO, Journal.class);
        Journal journalCreated = journalRepository.createJournal(journal);
        if (journalCreated != null) {
            return mm.map(journalCreated, JournalDTO.class);
        }

        return null;
    }

}
