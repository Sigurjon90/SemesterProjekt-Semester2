import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Link } from "react-router-dom";
import { Menu, Icon } from "antd";
import EditCitizen from "./EditCitizen"
import CareCenterList from "./CareCenterList";


const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

@observer
export default class AdminCitizens extends Component {
  constructor(props) {
    super(props);
  }

  state = {
    current: '',
  }

  componentWillMount() {
  }

  handleClick = (e) => {
    console.log('click ', e);
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

          <Menu.Item key="DeleteCitizen">
            <Icon type="meh" theme="twoTone" twoToneColor="#eb2f96" />Slet borger
        </Menu.Item>

          <Menu.Item key="EditCitizen">
            <Icon type="setting" theme="twoTone" />Redigér borger
        </Menu.Item>

          <Menu.Item key="CitizensList">
            <Icon type="book" theme="twoTone" />Se borgere
          </Menu.Item>
        </Menu>




        {current == "EditCitizen" && <CareCenterList />}
        {current == "CreateCitizen" && "Create Citizen"}
        {current == "DeleteCitizen" && "Delete Citizen"}


      </div>
    );
  }
}
