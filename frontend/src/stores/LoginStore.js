import { post } from "axios";
import jwt_decode from "jwt-decode";
import { action, computed, observable } from "mobx";
import { async } from "q";

class LoginStore {
  @observable user = null;
  @observable error = false;
  @observable isLoading = false;

  @action async authUser(username, password) {
    try {
      this.isLoading = true;
      const response = await post("http://localhost:8762/auth", {
        username,
        password
      });
      this.user = jwt_decode(response.headers.authorization);
      localStorage.setItem("authorization", response.headers.authorization);

      this.isLoading = false;
    } catch (err) {
      this.error = err;
      this.isLoading = false;
    }
  }

  @computed get isLoggedIn() {
    return !!this.user;
  }

  @action setError(value) {
    this.error = value;
  }

  @action
  logout() {
    localStorage.removeItem("authorization");
    this.user = null;
  }
}

const loginStore = new LoginStore();
export default loginStore;
