import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Menu, Icon } from "antd";
import UsersList from "../User/UsersList";
import CreateUser from "../User/CreateUser";
import EditableTableUsers from "../User/EditableTableUsers";

@observer
export default class AdminUsers extends Component {
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
                    mode="horizontal">

                    <Menu.Item key="CreateUser">
                        <Icon type="smile" theme="twoTone" twoToneColor="#52c41a" />Opret bruger
                    </Menu.Item>

                    <Menu.Item key="EditableUser">
                        <Icon type="setting" theme="twoTone" />Se og redigér brugere
                    </Menu.Item>

                </Menu>

                {current == "CreateUser" && <CreateUser />}
                {current == "EditableUser" && <EditableTableUsers />}

            </div>
        );
    }
}
