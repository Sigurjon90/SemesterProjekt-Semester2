import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Menu, Icon } from "antd";
import CreateCitizen from "../Citizen/CreateCitizen"
import EditableTable from "../EditableTable"


@observer
export default class AdminCitizens extends Component {
  constructor(props) {
    super(props);
  }

  state = {
    current: '',
  }

  handleClick = (e) => {
    this.setState({
      current: e.key,
    });
  }

  render() {
    const current = this.state.current
    return (
      <div>
        <Menu
          onClick={this.handleClick}
          selectedKeys={[this.state.current]}
          mode="horizontal"
        >
          <Menu.Item key="CreateCitizen">
            <Icon type="smile" theme="twoTone" twoToneColor="#52c41a" />Opret borger
        </Menu.Item>

          <Menu.Item key="EditCitizen">
            <Icon type="setting" theme="twoTone" />Se & redigér borgere
        </Menu.Item>

        </Menu>

        {current == "EditCitizen" && <EditableTable />}
        {current == "CreateCitizen" && <CreateCitizen />}

      </div>
    );
  }
}
