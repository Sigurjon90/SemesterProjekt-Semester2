import axios from "axios";

import { action, observable } from "mobx";

class DiaryStore {
    @observable error = null;
    @observable isFetching = false;
    @observable diary = null;

    @action async fetchDiary(id) {
        this.isFetching = true;
        this.error = null;
        try {
            const response = await axios.get(
                `http://localhost:8762/diaries/citizen/${id}`
            );
            this.diary = response.data;
            this.isFetching = false;
        } catch (error) {
            this.diary = null;
            this.error = error;
            this.isFetching = false;
        }
    }

    @action async putDiaryChanges(updatedDiary) {
        this.isFetching = true;
        this.error = null;
        try {
            const response = await axios.put('http://localhost:8762/diaries', updatedDiary);
            this.diary = response.data;
            this.isFetching = false;

        } catch (error) {
            this.error = error
            this.isFetching = false;
        }
    }

    @action async createDiary(createdDiary) {
        this.isFetching = true;
        this.error = null;
        try {
            const response = await axios.post('http://localhost:8762/diaries', createdDiary);
            this.diary = response.data;
            this.isFetching = false
        } catch (error) {
            this.error = error
            this.isFetching = false
        }
    }
}


const diaryStore = new DiaryStore();
export default diaryStore;
