import axios from "axios";

import { action, observable } from "mobx";

class UsersStore {
    @observable error = null;
    @observable isFetching = false;
    @observable user = null;
    @observable users = null;
    @observable archived = false;

    @action async fetchUsers() {
        this.isFetching = true;
        this.error = null;
        try {
            const response = await axios.get("http://localhost:8762/users");
            this.users = response.data;
            this.isFetching = false;
        } catch (error) {
            this.error = error;
            this.isFetching = false;
        }
    }

    @action async putCitizenChanges(updatedCitizen) {
        this.isFetching = true;
        this.error = null;
        try {
            const response = await axios.put('http://localhost:8762/citizens', updatedCitizen);
            this.citizen = response.data;
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
            console.log(this.citizen)
            this.isFetching = false;
        } catch (error) {
            this.error = error;
            this.isFetching = false;
        }
    }

    @action async createUser(createdUser) {
        this.isFetching = true;
        this.error = null;
        try {
            const response = await axios.post('http://localhost:8762/users/', createdUser);
            this.user = response.data;
            this.isFetching = false;
        } catch (error) {
            this.error = error;
            this.isFetching = false;
        }
    }

}

const usersStore = new UsersStore();
export default usersStore;
