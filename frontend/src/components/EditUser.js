import React, { Component } from "react";
import { inject, observer } from "mobx-react"
import { PageHeader, Input, Row, Col, Button, Alert, Table, InputNumber, Popconfirm, Form } from "antd";
import moment from "moment";
import "moment/locale/da";
import { observable } from "mobx";


@inject("usersStore")
@observer
export default class EditUser extends Component {
    constructor(props) {
        super(props);
        this.getUser = (id) => this.props.usersStore.fetchUser(id);
    }

    @observable username = ""
    @observable role = ""
    @observable email = ""
    @observable address = ""
    @observable active = ""
    @observable saved = false
    @observable deleted = false

    handleUsername = (e) => {
        this.username = e.target.value
    }

    handleRole = (e) => {
        this.role = e.target.value
    }

    handleEmail = (e) => {
        this.email = e.target.value
    }

    handleAddress = (e) => {
        this.address = e.target.value
    }

    handleDelete = () => {
        const { user } = this.props.usersStore;
        this.props.usersStore.archiveUser(user.id)
        this.deleted = true
    }

    handleSubmit = () => {
        const { user } = this.props.usersStore;
        const updatedUser = {
            id: user.id,
            username: (this.username != "") ? this.username : user.username,
            role: (this.role != "") ? this.role : user.role,
            email: (this.email != "") ? this.email : user.email,
            cpr: (this.cpr != "") ? this.cpr : user.cpr,
            address: (this.address != "") ? this.address : user.address,
            active: (this.active != "") ? this.active : user.active,
        }
        this.props.usersStore.putUserChanges(updatedUser)
        this.saved = true
    }

    componentWillMount() {
        const {
            match: { params }, usersStore
        } = this.props;
        this.getUser(params.id)
    }

    render() {
        const { usersStore } = this.props
        const { user, isFetching } = usersStore
        return (<div>
            {this.saved &&
                <div>
                    <PageHeader
                        onBack={() => history.back()}
                        title="Redigér borger"
                        subTitle="- Udfyld ændringer og gem" />
                    <Alert message="Ændringerne er nu blevet gemt!" type="success" />
                </div>
            }
            {this.deleted &&
                <div>
                    <PageHeader
                        onBack={() => history.back()}
                        title="Redigér borger"
                        subTitle="- Udfyld ændringer og gem" />
                    <Alert message="Borgeren er nu arkiveret" type="error" />
                </div>
            }
            {!isFetching && !this.saved && !this.deleted &&
                <div>
                    <PageHeader
                        onBack={() => history.back()}
                        title="Redigér borger"
                        subTitle="- Udfyld ændringer og gem" />

                    <Row gutter={16}>
                        <Col span={8}><Input key="username " defaultValue={user.username} onChange={this.handleUsername} /></Col>
                        <Col span={8}><Input key="role" defaultValue={user.role} onChange={this.handleRole} /></Col>
                        <Col span={8}><Input key="email" defaultValue={user.email} onChange={this.handleEmail} /></Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={8}><Input key="address" defaultValue={user.address} onChange={this.handleAddress} /></Col>
                    </Row>
                    <Button type="primary" onClick={this.handleSubmit}>Gem ændringer</Button>
                    <Button type="danger" onClick={this.handleDelete}>Arkivér borger</Button>
                </div>
            }
        </div>)
    }
}