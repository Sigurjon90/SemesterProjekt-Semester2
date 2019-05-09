import axios from "axios";

import { action, computed, observable, toJS } from "mobx";

class CitizensStore {
  @observable error = null;
  @observable isFetching = false;
  @observable primaryCitizens = [];
  @observable otherCitizens = [];
  @observable citizen = null;

  // TODO: make prettier
  pushCitizen = citizen => {
    const pindex = this.primaryCitizens.findIndex(item => citizen.id === item.id)
    const oindex = this.otherCitizens.findIndex(item => citizen.id === item.id)
    if (pindex > -1) {
      const item = this.primaryCitizens[pindex]
      this.primaryCitizens.splice(pindex, 1, {
        ...item,
        ...citizen,
      })
    } else {
      const item = this.otherCitizens[oindex]
      this.otherCitizens.splice(oindex, 1, {
        ...item,
        ...citizen,
      })
    }
  }

  replaceAt = (array, index, value) => {
    const item = array[index]
    return array.splice(index, 1, { ...item, ...value })
  }

  @action async deleteCitizen(id) {
    this.isFetching = true;
    this.error = null;
    try {
      await axios.delete(`http://localhost:8762/citizens/${id}`);
      this.primaryCitizens.replace(this.primaryCitizens.filter(item => item.id !== id))
      this.otherCitizens.replace(this.otherCitizens.filter(item => item.id !== id))
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

  @action async updateCitizen(updatedCitizen) {
    this.isFetching = true;
    this.error = null;
    try {
      const response = await axios.put('http://localhost:8762/citizens', updatedCitizen);
      this.pushCitizen(response.data[0])
      this.isFetching = false;
    } catch (error) {
      this.error = error
      this.isFetching = false;
    }
  }

  @action async fetchCitizen(id) {
    this.isFetching = true;
    this.error = null;
    try {
      const response = await axios.get(`http://localhost:8762/citizens/${id}`);
      this.citizen = response.data;
      this.isFetching = false;
    } catch (error) {
      this.error = error;
      this.isFetching = false;
    }
  }

  @action async createCitizen(createdCitizen) {
    this.isFetching = true;
    this.error = null;
    try {
      const response = await axios.post('http://localhost:8762/citizens/', createdCitizen);
      this.citizen = response.data;
      this.isFetching = false;
    } catch (error) {
      this.error = error;
      this.isFetching = false;
    }
  }

}

const citizensStore = new CitizensStore();
export default citizensStore;
