import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Menu, Icon } from "antd";
import UsersList from "./UsersList";
import CreateUser from "./CreateUser";

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
                    <Menu.Item key="EditUser">
                        <Icon type="setting" theme="twoTone" />Se & redigér brugere
                    </Menu.Item>

                </Menu>

                {current == "EditUser" && <UsersList />}
                {current == "CreateUser" && <CreateUser />}

            </div>
        );
    }
}
