import React, { Component } from "react";
import { Route, Link } from "react-router-dom";
import { inject, observer } from "mobx-react";
import LazyRoute from "lazy-route";
import DevTools from "mobx-react-devtools";
import { Admin } from "./login/auth";

@inject("routing")
@observer
export default class App extends Component {
  render() {
    return (
      <div className="wrapper">
        <Admin path="/" component={import("./Login/Login")} />
        <Route
          exact
          path="/citizens"
          render={props => (
            <LazyRoute {...props} component={import("./CitizensList")} />
          )}
        />

        <DevTools />
      </div>
    );
  }
}
