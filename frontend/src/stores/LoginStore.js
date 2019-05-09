import { post } from "axios";
import jwt_decode from "jwt-decode"
import { action, computed, observable } from "mobx"

class LoginStore {
  @observable user = null;
  @observable error = false;

  @action async authUser(username, password) {
    try {
      const response = await post("http://localhost:8762/auth", {
        username,
        password
      })
      this.user = jwt_decode(response.headers.authorization)
      localStorage.setItem("authorization", response.headers.authorization)
    } catch (err) {
      this.error = err
    }
  }

  @computed get isLoggedIn() {
    return !!this.user
  }

  @action
  logout() {
    localStorage.removeItem("authorization")
    this.user = null
  }
}

const loginStore = new LoginStore();
export default loginStore;
