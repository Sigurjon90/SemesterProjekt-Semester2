import React, { Component } from "react";
import { Route, Link } from "react-router-dom";
import { inject, observer } from "mobx-react";
import LazyRoute from "lazy-route";
import DevTools from "mobx-react-devtools";
import { AdminRoute, NotLoggedInRoute } from "./login/auth";
/*
const Authorization = (WrappedComponent, allowedRoles, props) => {
  const role = "admin";
  if (allowedRoles.includes(role)) {
    return <LazyRoute {...props} component={Login} />;
  } else {
    return <h1>No page for you!</h1>;
  }
};


const User = Authorization(["user", "manager", "admin"]);
const Manager = Authorization(["manager", "admin"]);
const Admin = Authorization(["admin"]);
*/
const Login = () => import("./Login/Login");

@inject("routing")
@observer
export default class App extends Component {
  render() {
    return (
      <div className="wrapper">
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
            <LazyRoute {...props} component={import("./Login/Login")} />
          )}
        />
        <Route
          exact
          path="/citizens/:id"
          render={props => (
            <LazyRoute {...props} component={import("./SingleCitizen")} />
          )}
        />
        <Route
          exact
          path="/admin/citizens"
          render={props => (
            <LazyRoute {...props} component={import("./AdminCitizens")} />
          )}
        />
        <Route
          exact
          path="/admin/users"
          render={props => (
            <LazyRoute {...props} component={import("./AdminUsers")} />
          )}
        />
        <Route
          exact
          path="/admin/citizens/edit/:id"
          render={props => (
            <LazyRoute {...props} component={import("./EditCitizen")} />
          )}
        />
        <Route
          exact
          path="/admin/users/edit/:id"
          render={props => (
            <LazyRoute {...props} component={import("./EditUser")} />
          )}
        />
        <DevTools />
      </div>
    );
  }
}
