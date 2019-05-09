import React, { Component } from "react";
import Theme from "./Theme";
import { Route, Link, Redirect } from "react-router-dom";
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

const Authorization = (allowedRoles) =>
  (component) => {
    return LoginStore.isLoggedIn && hasAnyRole(allowedRoles) ? (
      component
    ) : (
      <Redirect to='/' />
    )
}

const CareGiver = Authorization(["admin", "caseworker", "caregiver"])
const CaseWorker = Authorization(["admin", "caseworker"])
const Admin = Authorization(["admin"])

@inject("routing")
@observer
export default class App extends Component {
  render() {
    return (
      <Theme>
      <Route
          exact
          path="/"
          render={props => (
            <LazyRoute {...props} component={import("./Login/Login")} />
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
        />
        <DevTools />
        <DevTools />
      </Theme>
    )
  }
}
