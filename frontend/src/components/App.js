import React, { Component } from "react";
import { Route, Link } from "react-router-dom";
import { inject, observer } from "mobx-react";
import LazyRoute from "lazy-route";
import DevTools from "mobx-react-devtools";

// Styrer routerne -> Hvad der sker når vi rammer de forskellige links
@inject("routing")
@observer
export default class App extends Component {
  render() {
    return (
      <div className="wrapper">
        <Route
          exact
          path="/citizens"
          render={props => (
            <LazyRoute {...props} component={import("./CitizensList")} />
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

        <DevTools />
      </div>
    );
  }
}
