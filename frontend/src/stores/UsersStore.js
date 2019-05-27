import axios from "axios";

import { action, observable } from "mobx";

class UsersStore {
    @observable error = null;
    @observable isFetching = false;
    @observable user = null;
    @observable users = [];
    @observable archived = false;

    pushUser = user => {
        const index = this.users.findIndex(item => user.id === item.id)
        if (index > -1) {
            const item = this.users[index]
            this.users.splice(index, 1, {
                ...item,
                ...user,
            })
        }
    }

    replaceAt = (array, index, value) => {
        const item = array[index]
        return array.splice(index, 1, { ...item, ...value })
    }


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

    @action async archiveUser(id) {
        this.isFetching = true;
        this.error = null;
        try {
            const response = await axios.delete(`http://localhost:8762/users/${id}`);
            this.user = response.data
            this.isFetching = false;
        } catch (error) {
            this.error = error;
            this.isFetching = false;
        }
    }

    @action async putUserChanges(updatedUser) {
        this.isFetching = true;
        this.error = null;
        try {
            await axios.put('http://localhost:8762/users', updatedUser);
            this.isFetching = false;
        } catch (error) {
            this.error = error
            this.isFetching = false;
        }
    }

    @action async fetchUser(id) {
        this.isFetching = true;
        this.error = null;
        try {
            const response = await axios.get(`http://localhost:8762/users/${id}`);
            this.user = response.data;
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
