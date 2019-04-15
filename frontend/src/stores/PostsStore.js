import axios from 'axios'
import { action, observable } from 'mobx'

// Should totally create a comment store, but just hacking around since comments = posts shrugh
class PostsStore {
  @observable error = null
  @observable isFetching = false
  @observable posts = []
  @observable post = {}
  @observable comments = []
  @observable commentsIsFetching = false

  @action async fetchPosts() {
    this.isFetching = true
    this.error = null
    try {
      const response = await axios.get('https://jsonplaceholder.typicode.com/posts')
      this.posts = response.data
      this.isFetching = false
    } catch (error) {
      this.error = error
      this.isFetching = false
    }
  }

  @action async fetchPost(id) {
    this.isFetching = true
    this.error = null
    try {
      const response = await axios.get(`https://jsonplaceholder.typicode.com/posts/${id}`)
      this.post = response.data
      this.isFetching = false
    } catch (error) {
      this.error = error
      this.isFetching = false
    }
  }

  @action async postPost(post) {
    this.commentsIsFetching = true
    this.error = null
    try {
      const response = await axios.post('https://jsonplaceholder.typicode.com/posts', {
        title: 'foo',
        body: post.body,
        serId: 1
      })
      this.comments.push({...response.data})
      this.commentsIsFetching = false
    } catch (error) {
      this.error = error
      this.commentsIsFetching = false 
    }
  }
}

const postsStore = new PostsStore()
export default postsStore