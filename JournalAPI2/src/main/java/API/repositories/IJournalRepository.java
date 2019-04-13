/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package API.repositories;

import API.entities.Journal;
import API.entities.JournalDTO;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author thinkbuntu
 */
public interface IJournalRepository {

    Journal createJournal(Journal journal);

    Journal deleteJournal(UUID id, UUID authorID);

    Journal findJournal(UUID id);

    List<Journal> getJournals(List<UUID> listOfId);

    Journal findJournalByCitizen(UUID id);

        /*
    Param: journalDTO
    Purpose: createsEvent from journalDTO
    Returns: Event object
         */
        Journal modifyJournal
        (JournalDTO journalDTO
    

);
    
}
