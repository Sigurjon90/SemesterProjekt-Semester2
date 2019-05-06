import React, { Component } from "react";
import { Route, Redirect } from "react-router-dom";
import LazyRoute from "lazy-route";
import jwt_decode from "jwt-decode";

const userData = () => {
  const token = localStorage.getItem("authorization");
  console.log(token);
  if (token == null) {
    return null;
  }
  return jwt_decode(token);
};

const getRole = () => {
  return userData().authorities;
};

const Auth = role => {
  if (role != userData().authorities) return false;
};

export class Admin extends Component {
  constructor(props) {
    super(props);
    this.path = this.props.path;
    this.component = this.props.component;
    this.role = getRole();

    console.log(userData());
  }
  render() {
    const self = this;
    console.log(this.props.component);
    console.log(this.props.path);
    if (this.role != "ROLE_ADMIN") {
      return <Redirect to="lol" />;
    }
    return (
      <Route
        exact
        path={self.path}
        render={props => <LazyRoute {...props} component={self.component} />}
      />
    );
  }
}

export default Auth;
