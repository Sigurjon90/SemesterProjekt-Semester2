import axios from "axios";

import { action, computed, observable } from "mobx";

// HVORFOR IKKE OBSERVER HER? ####
class LoginStore {
  @observable username = null;
  @observable password = false;

  @action async authUser() {
    axios
      .post("http://localhost:8762/auth", {
        username: "admin",
        password: "12345"
      })
      .then(function(response) {
        console.log(response.headers.authorization);
        localStorage.setItem("authorization", response.headers.authorization);
      });
  }
}

const loginStore = new LoginStore();
export default loginStore;
