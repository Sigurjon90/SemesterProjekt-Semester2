/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.services;

import API.entities.JournalDTO;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author thinkbuntu
 */
public interface IJournalService {

    // CREATE JOURNAL
    JournalDTO createJournal(JournalDTO journalDTO);

    // Delete Journal
    boolean deleteJournal(UUID id, UUID authorID);

    // Find Journal
    JournalDTO findJournal(UUID id);

    List<JournalDTO> getJournals(List<UUID> listOfCitizensIds);

    /*
    Maps Event to EventDTO
    Returns EventDTO or null
     */
    JournalDTO modifyJournal(JournalDTO journalDTO);

    JournalDTO findJournaByCitizen(UUID id);

}
