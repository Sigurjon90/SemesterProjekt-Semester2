import axios from "axios";

import { action, computed, observable } from "mobx";

// HVORFOR IKKE OBSERVER HER? ####
class JournalStore {
  @observable error = null;
  @observable isFetching = false;
  @observable journal = null;

  @action async fetchJournal(id) {
    this.isFetching = true;
    this.error = null;
    try {
      // Const = Bare en variabel -> Type er ikke nødvendigt i JS = immutable
      //Let lever i begrænset scope og kan assignes new value
      //Var har højere scope
      const response = await axios.get(
        `http://localhost:1339/journals/citizen/${id}`
      );
      console.log(response.data);
      // Data bliver smidt  i array
      this.journal = response.data;
      this.isFetching = false;
    } catch (error) {
      this.error = error;
      this.isFetching = false;
    }
  }
}

const journalStore = new JournalStore();
export default journalStore;
