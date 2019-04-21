import axios from "axios";

import { action, computed, observable } from "mobx";

// HVORFOR IKKE OBSERVER HER? ####
class CitizensStore {
  @observable error = null;
  @observable isFetching = false;
  @observable isFetching = false;
  @observable citizens = [];
  @observable citizen = null;

  @action async fetchCitizens() {
    this.isFetching = true;
    this.error = null;
    try {
      const response = await axios.get("http://localhost:1338/citizens");
      this.citizens = response.data;
      this.isFetching = false;
    } catch (error) {
      this.error = error;
      this.isFetching = false;
    }
  }

  @action async fetchCitizen(id) {
    this.isFetching = true;
    this.error = null;
    try {
      const response = await axios.get(`http://localhost:1338/citizens/${id}`);
      this.citizen = response.data;
      console.log(this.citizen)
      this.isFetching = false;
    } catch (error) {
      this.error = error;
      this.isFetching = false;
    }
  }

}

const citizensStore = new CitizensStore();
export default citizensStore;
