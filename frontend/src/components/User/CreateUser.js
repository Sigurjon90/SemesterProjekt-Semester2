import React, { Component } from "react";
import { inject, observer } from "mobx-react"
import { Form, Input, Button, Divider, Select, Alert, Row, Col } from "antd";
import { observable } from "mobx";

const { Option } = Select;

const formItemLayout = {
    labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
    },
    wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 },
    },
};

@inject("usersStore")
@observer
export default class CreateUser extends Component {
    constructor(props) {
        super(props);
        this.getUser = (id) => this.props.usersStore.fetchUser(id);
    }

    @observable usernameChecker = "error"
    @observable username = ""
    @observable passwordChecker = "error"
    @observable password = ""
    @observable roleChecker = "error"
    @observable role = ""
    @observable emailChecker = "error"
    @observable email = ""
    @observable cprChecker = "error"
    @observable cpr = ""
    @observable addressChecker = "error"
    @observable address = ""
    @observable assignedCitizens = []
    @observable isUserCreated = false

    handleCreate = () => {
        const createdUser = {
            username: this.username,
            password: this.password,
            role: this.role,
            email: this.email,
            cpr: this.cpr,
            address: this.address,
            citizensIDList: this.assignedCitizens
        }
        this.props.usersStore.createUser(createdUser)
        this.isUserCreated = true
    }

    handleUsername = (e) => {
        if ((/^[a-z0-9_ ]{5,15}$/i.test(`${e.target.value}`))) {
            this.usernameChecker = "success"
            this.username = e.target.value
        } else {
            this.usernameChecker = "error"
            this.username = ""
        }
    }

    handlePassword = (e) => {
        // Add more special characters to password regex if needed
        if ((/^[ A-Za-z0-9_@./#!%"*&+-]{5,25}$/i.test(`${e.target.value}`))) {
            this.passwordChecker = "success"
            this.password = e.target.value
        } else {
            this.passwordChecker = "error"
            this.password = ""
        }
    }

    handleEmail = (e) => {
        if ((/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i.test(`${e.target.value}`))) {
            this.emailChecker = "success"
            this.email = e.target.value
        } else {
            this.emailChecker = "error"
            this.email = ""
        }
    }

    handleCPR = (e) => {
        if ((/^[0-9]{10,10}$/i.test(`${e.target.value}`))) {
            this.cprChecker = "success"
            this.cpr = e.target.value
        } else {
            this.cprChecker = "error"
            this.cpr = ""
        }
    }

    handleAddress = (e) => {
        if ((/^[a-z0-9_ ÆØÅæøå]{5,25}$/i.test(`${e.target.value}`))) {
            this.addressChecker = "success"
            this.address = e.target.value
        } else {
            this.addressChecker = "error"
            this.address = ""
        }
    }

    handleRole = (value) => {
        this.role = value
    }

    handleCitizens = (value) => {
        this.assignedCitizens = value
    }

    render() {
        const { citizens } = this.props
        return (<div>
            {this.isUserCreated &&
                <div>
                    <Alert message="Ændringerne er nu blevet gemt!" type="success" />
                </div>
            }
            {!this.isUserCreated &&
                <div>
                    <Row>
                        <Col span={12}>
                            <Divider>Udfyld information om brugeren</Divider>
                            <Form {...formItemLayout}>
                                <Form.Item
                                    label="Brugernavn: "
                                    validateStatus={this.usernameChecker}
                                    help={this.username != "success" && "Min. 5 karakterer og ingen specialtegn"}
                                >
                                    <Input onChange={this.handleUsername} />
                                </Form.Item>
                                <Form.Item
                                    label="Kode: "
                                    validateStatus={this.passwordChecker}
                                    help={this.passwordChecker != "success" && "Kode skal være mere end 6 tegn"}
                                >
                                    <Input onChange={this.handlePassword} />
                                </Form.Item>
                                <Form.Item
                                    label="E-mail: "
                                    validateStatus={this.emailChecker}
                                    help={this.emailChecker != "success" && "Indtast en korrekt e-mail"}
                                >
                                    <Input onChange={this.handleEmail} />
                                </Form.Item>
                                <Form.Item
                                    label="CPR: "
                                    validateStatus={this.cprChecker}
                                    help={this.cprChecker != "success" && "Indtast CPR-nummer"}
                                >
                                    <Input onChange={this.handleCPR} />
                                </Form.Item>
                                <Form.Item
                                    label="Addresse: "
                                    validateStatus={this.addressChecker}
                                    help={this.addressChecker != "success" && "Indtast adresse"}
                                >
                                    <Input onChange={this.handleAddress} />
                                </Form.Item>
                            </Form>
                            <Divider>Vælg rolle</Divider>
                            <Select defaultValue="Vælg rolle" style={{ width: 180, marginLeft: 150, marginTop: 10 }} onChange={this.handleRole}>
                                <Option value="admin">Administrator</Option>
                                <Option value="caseworker">Sagsbehandler</Option>
                                <Option value="caregiver">Pædagog</Option>
                            </Select>
                            <Divider>Vælg Primære borgere</Divider>
                            <Select
                                mode="multiple"
                                placeholder="Vælg Primære borgere"
                                style={{ width: 180, marginLeft: 150, marginTop: 10 }}
                            >
                                {citizens.map(citizen =>
                                    <Option value={citizen.id} key={citizen.id}>{citizen.name}</Option>
                                )}
                            </Select>
                            <Button type="primary" style={{ marginLeft: 10 }} onClick={this.handleCreate}>Opret bruger</Button>
                        </Col>
                        <Col span={12}></Col>
                    </Row>
                </div>}
        </div >)
    }
}