import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Link } from "react-router-dom";
import { Table, Alert, Tag } from "antd";

const columns = [
    {
        title: "Navn",
        dataIndex: "username",
        key: "username",
        render: (text, data) => <Link to={`/admin/users/edit/${data.id}`}>{text}</Link>
    },
    {
        title: "Status",
        dataIndex: "role",
        key: "role"
    },
    {
        title: "E-mail",
        dataIndex: "email",
        key: "email"
    }
];

const usersConfig = {
    pagination: {
        defaultPageSize: 5
    }
};

@inject("usersStore")
@observer
export default class UsersList extends Component {
    constructor(props) {
        super(props);
        this.getUsers = () => this.props.usersStore.fetchUsers();
    }

    componentWillMount() {
        this.getUsers();
    }

    render() {
        const { usersStore } = this.props
        const { users } = usersStore;
        return (
            <div>
                <Alert message="Vælg og redigér en bruger" type="info" showIcon />
                <Table
                    {...users}
                    {...usersConfig}
                    columns={columns}
                    dataSource={users}
                />

            </div>
        );
    }
}
