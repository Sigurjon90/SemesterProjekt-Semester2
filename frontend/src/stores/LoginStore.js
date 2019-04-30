import axios from "axios";

import { action, computed, observable } from "mobx";

// HVORFOR IKKE OBSERVER HER? ####
class LoginStore {
  @observable loggedIn = false;
  @observable error = false;

  @action authUser(username, lastname) {
    let promise = axios
      .post("http://localhost:8762/auth", {
        username: username,
        password: lastname
      })
      .then(function(response) {
        console.log(response.headers.authorization);
        localStorage.setItem("authorization", response.headers.authorization);
        return true;
      })
      .catch(error => {
        console.log(error);
        return false;
      });

      return promise;
  }

  @action login() {
    this.loggedIn = true;
  }
}

const loginStore = new LoginStore();
export default loginStore;
