import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Skeleton, List } from "antd";

export default class CitizenName extends Component {
  render() {
    const { name, adress, city } = this.props;

    return (
      <div>
        {name} - {adress} - {city} -{" "}
        <a href="http://erdetfredag.dk">Pronhubs</a>
      </div>
    );
  }
}
