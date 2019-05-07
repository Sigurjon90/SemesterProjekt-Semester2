import React, { Component } from "react";
import { Route, Redirect } from "react-router-dom";
import LazyRoute from "lazy-route";
import jwt_decode from "jwt-decode";
import hasRole from "../../utils/auth";

const userData = () => {
  const token = localStorage.getItem("authorization");
  if (token == null) {
    return null;
  }
  return jwt_decode(token);
};

const getRole = () => {
  if (userData()) {
    return userData().authorities;
  }
  return null;
};

const Auth = role => {
  if (userData()) {
    if (role != userData().authorities) return false;
  }
  return null;
};

export class NotLoggedInRoute extends Component {
  constructor(props) {
    super(props);
    this.path = this.props.path;
    this.component = this.props.component;
    this.role = getRole();
  }
  render() {
    const self = this;

    return (
      <Route
        exact
        path={self.path}
        render={props => (
          <LazyRoute restrict {...props} component={self.component} />
        )}
      />
    );
  }
}

export class AdminRoute extends Component {
  constructor(props) {
    super(props);
    this.path = this.props.path;
    this.component = this.props.component;
    this.role = getRole();
  }
  render() {
    const self = this;
    console.log(hasRole("admin"));
    return (
      <Route
        exact
        path={self.path}
        render={props => {
          if (self.role != "admin") {
            return <h1>HER DU HAR DU IKKE ADNGAG</h1>;
          }
          <LazyRoute {...props} component={self.component} />;
        }}
      />
    );
  }
}

export default Auth;
