import axios from 'axios'

import { action, computed, observable } from 'mobx'

const delay = ms => new Promise(r => setTimeout(r, ms))

class TodoStore {
  @observable error = null
  @observable isFetching = false
  @observable todos = []

  @action async fetchTodos() {
    this.isFetching = true
    this.error = null
    try {
      const response = await axios.get('https://jsonplaceholder.typicode.com/todos')
      this.todos = response.data
      await delay(5000)
      this.isFetching = false
    } catch (error) {
      this.error = error
      this.isFetching = false
    }
  }
}

const todoStore = new TodoStore()
export default todoStore