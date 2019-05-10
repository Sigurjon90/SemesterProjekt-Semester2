import React, { Component } from "react";
import Theme from "./Theme";
import { Route, Link, Redirect, withRouter } from "react-router-dom";
import { inject, observer } from "mobx-react";
import LazyRoute from "lazy-route";
import DevTools from "mobx-react-devtools";
import LoginStore from "../stores/LoginStore";
import hasAnyRole from "../utils/auth";

/*
const User = Authorization(["user", "manager", "admin"]);
const Manager = Authorization(["manager", "admin"]);
const Admin = Authorization(["admin"]);
*/

const Authorization = (allowedRoles, isLoggedIn) =>
  (component) => {
    return isLoggedIn && hasAnyRole(allowedRoles) ? (
      component
    ) : (
        <Redirect to='/' />
      )
  }

@inject("routing", "loginStore")
@observer
class App extends Component {
  render() {
    const { loginStore } = this.props
    const { isLoggedIn } = loginStore

    const CareGiver = Authorization(["admin", "caseworker", "caregiver"], isLoggedIn)
    const CaseWorker = Authorization(["admin", "caseworker"], isLoggedIn)
    const Admin = Authorization(["admin"], isLoggedIn)
    return (
      <Theme>
        <Route
          exact
          path="/"
          render={props => (
            <LazyRoute {...props} component={import("./Login/login")} />
          )}
        />
        <Route
          exact
          path="/citizens"
          render={props => (
            CareGiver(<LazyRoute {...props} component={import("./Citizen/CitizensList")} />)
          )}
        />
        <Route
          exact
          path="/citizens/:id"
          render={props => (
            CareGiver(<LazyRoute {...props} component={import("./Citizen/SingleCitizen")} />)
          )}
        />
        <Route
          exact
          path="/admin/citizens"
          render={props => (
            Admin(<LazyRoute {...props} component={import("./Admin/AdminCitizens")} />)
          )}
        />
        <Route
          exact
          path="/admin/users"
          render={props => (
            Admin(<LazyRoute {...props} component={import("./Admin/AdminUsers")} />)
          )}
        />
        <Route
          exact
          path="/admin/citizens/edit/:id"
          render={props => (
            Admin(<LazyRoute {...props} component={import("./Citizen/EditCitizen")} />)
          )}
        />
        <Route
          exact
          path="/admin/users/edit/:id"
          render={props => (
            Admin(<LazyRoute {...props} component={import("./User/EditUser")} />)
          )}
        /><Route
          exact
          path="/admin"
          render={props => (
            Admin(<LazyRoute {...props} component={import("./Admin/AdminCitizens")} />)
          )}
        />
        <DevTools />
      </Theme>
    )
  }
}

export default withRouter(observer(App))
