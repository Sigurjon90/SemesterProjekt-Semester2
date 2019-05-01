import axios from "axios";

import { action, computed, observable } from "mobx";

class CitizensStore {
  @observable error = null;
  @observable isFetching = false;
  @observable isFetching = false;
  @observable primaryCitizens = [];
  @observable otherCitizens = [];
  @observable citizen = null;
  @observable archived = false;


  @action async archiveCitizen(id) {
    this.isFetching = true;
    this.error = null;
    try {
      const response = await axios.get(`http://localhost:8762/citizens/${id}`);
      this.citizens = response.data
      this.isFetching = false;
    } catch (error) {
      this.error = error;
      this.isFetching = false;
    }
  }



  @action async fetchCitizens() {
    this.isFetching = true;
    this.error = null;
    try {
      const response = await axios.get("http://localhost:8762/citizens");
      this.primaryCitizens = response.data.primaryCitizens;
      this.otherCitizens = response.data.otherCitizens;
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
      const response = await axios.get(`http://localhost:8762/citizens/${id}`);
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
