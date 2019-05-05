import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Link } from "react-router-dom";
import { Menu, Icon } from "antd";
import EditableTable from "./EditableTable"
import CreateCitizen from "./CreateCitizen"
import UsersList from "./UsersList";
import CreateUser from "./CreateUser";


const SubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;

@observer
export default class AdminUsers extends Component {
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
                    mode="horizontal">

                    <Menu.Item key="CreateCitizen">
                        <Icon type="smile" theme="twoTone" twoToneColor="#52c41a" />Opret bruger
                    </Menu.Item>
                    <Menu.Item key="EditCitizen">
                        <Icon type="setting" theme="twoTone" />Se & redigér brugere
                    </Menu.Item>

                </Menu>

                {current == "EditCitizen" && <UsersList />}
                {current == "CreateCitizen" && <CreateUser />}

            </div>
        );
    }
}
