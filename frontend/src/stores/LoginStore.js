import { post } from "axios";
import jwt_decode from "jwt-decode"
import { action, computed, observable } from "mobx"
import moment from "moment"

class LoginStore {
  @observable user = null;
  @observable error = false;
  @observable isLoading = false;

  @action async authUser(username, password) {
    this.isLoading = true
    this.error = null
    try {
      
      const response = await post("http://localhost:8762/auth", {
        username,
        password
      })
      this.user = jwt_decode(response.headers.authorization)
      localStorage.setItem("authorization", response.headers.authorization)
      this.isLoading = false
    } catch (err) {
      this.error = err
      this.isLoading = false
    }
  }

  @computed get isLoggedIn() {
    return !!this.user
  }

  isLoggedInPersist() {
    const token = localStorage.getItem("authorization");
    if (token == null) return false;
    return jwt_decode(token).exp > moment().unix()
  }

  @action
  logout() {
    localStorage.removeItem("authorization")
    this.user = null
  }
}

const loginStore = new LoginStore();
export default loginStore;
