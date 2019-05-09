import React, { Component } from "react";
import { inject, observer } from "mobx-react"
import { PageHeader, Input, Row, Col, Button, Alert } from "antd";
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
    @observable isUserSaved = false
    @observable isUserDeleted = false

    onChangeHandler = (param) => (e) => {
        this[param] = e.target.value
    }

    handleDelete = () => {
        const { user } = this.props.usersStore;
        this.props.usersStore.archiveUser(user.id)
        this.isUserDeleted = true
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
        this.isUserSaved = true
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
            {this.isUserSaved &&
                <div>
                    <PageHeader
                        onBack={() => history.back()}
                        title="Redigér bruger"
                        subTitle="- Udfyld ændringer og gem" />
                    <Alert message="Ændringerne er nu blevet gemt!" type="success" />
                </div>
            }
            {this.isUserDeleted &&
                <div>
                    <PageHeader
                        onBack={() => history.back()}
                        title="Redigér bruger"
                        subTitle="- Udfyld ændringer og gem" />
                    <Alert message="Brugeren er nu arkiveret" type="error" />
                </div>
            }
            {!isFetching && !this.isUserSaved && !this.isUserDeleted &&
                <div>
                    <PageHeader
                        onBack={() => history.back()}
                        title="Redigér Bruger"
                        subTitle="- Udfyld ændringer og gem" />

                    <Row gutter={16}>
                        <Col span={8}><Input key="username " defaultValue={user.username} onChange={this.onChangeHandler('username')} /></Col>
                        <Col span={8}><Input key="role" defaultValue={user.role} onChange={this.onChangeHandler('role')} /></Col>
                        <Col span={8}><Input key="email" defaultValue={user.email} onChange={this.onChangeHandler('email')} /></Col>
                    </Row>
                    <Row gutter={16}>
                        <Col span={8}><Input key="address" defaultValue={user.address} onChange={this.onChangeHandler('address')} /></Col>
                    </Row>
                    <Button type="primary" onClick={this.handleSubmit}>Gem ændringer</Button>
                    <Button type="danger" onClick={this.handleDelete} style={{ marginLeft: 10, marginTop: 10 }}>Arkivér bruger</Button>
                </div>
            }
        </div>)
    }
}