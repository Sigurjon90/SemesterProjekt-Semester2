import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Menu, Icon } from "antd";
import UsersList from "../User/UsersList";
import CreateUser from "../User/CreateUser";
import EditableTableUsers from "../User/EditableTableUsers";
import concat from "../../utils/concat"

@inject("citizensStore")
@observer
export default class AdminUsers extends Component {
    constructor(props) {
        super(props);
        this.getCitizens = () => this.props.citizensStore.fetchCitizens()
    }

    componentWillMount() {
        this.getCitizens()
    }

    state = {
        current: 'EditableUser',
    }

    handleClick = (e) => {
        this.setState({
            current: e.key,
        });
    }

    render() {
        const { citizensStore } = this.props;
        const { primaryCitizens, otherCitizens } = citizensStore
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

                {current == "CreateUser" && <CreateUser citizens={concat(primaryCitizens, otherCitizens)} />}
                {current == "EditableUser" && <EditableTableUsers citizens={concat(primaryCitizens, otherCitizens)} />}
            </div>
        );
    }
}
