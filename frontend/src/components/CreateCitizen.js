import React, { Component } from "react";
import { inject, observer } from "mobx-react"
import { Form, Input, Button, Divider, Select, Cascader, Row, Col, FormComponentProps } from "antd";
import moment from "moment";
import "moment/locale/da";
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

@inject("citizensStore")
@observer
export default class CreateCitizen extends Component {
    constructor(props) {
        super(props);
        this.getCitizen = (id) => this.props.citizensStore.fetchCitizen(id);
    }

    @observable nameChecker = "error"
    @observable name = ""
    @observable addressChecker = "error"
    @observable address = ""
    @observable cityChecker = "error"
    @observable city = ""
    @observable zipChecker = "error"
    @observable zip = ""
    @observable cprChecker = "error"
    @observable cpr = ""
    @observable phoneChecker = "error"
    @observable phone = ""
    @observable careCenter = ""
    @observable diagnoses = []

    handleCreate = () => {
        const createdCitizen = {
            name: this.name,
            address: this.address,
            city: this.city,
            zip: this.zip,
            cpr: this.cpr,
            phoneNumber: this.phone,
            careCenterID: this.careCenter,
            diagnoses: this.diagnoses
        }
        this.props.citizensStore.createCitizen(createdCitizen)
    }

    handleName = (e) => {
        if ((/^[a-z_ ]{5,25}$/i.test(`${e.target.value}`))) {
            this.nameChecker = "success"
            this.name = e.target.value
        } else {
            this.nameChecker = "error"
            this.name = ""
        }
    }

    handleAddress = (e) => {
        if ((/^[a-z0-9_ ]{5,25}$/i.test(`${e.target.value}`))) {
            this.addressChecker = "success"
            this.address = e.target.value
        } else {
            this.addressChecker = "error"
            this.address = ""
        }
    }

    handleCity = (e) => {
        if ((/^[a-z_ ]{3,25}$/i.test(`${e.target.value}`))) {
            this.cityChecker = "success"
            this.city = e.target.value
        } else {
            this.cityChecker = "error"
            this.city = ""
        }
    }

    handleZip = (e) => {
        if ((/^[0-9]{4,4}$/i.test(`${e.target.value}`))) {
            this.zipChecker = "success"
            this.zip = e.target.value
        } else {
            this.zipChecker = "error"
            this.zip = ""
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

    handlePhone = (e) => {
        if ((/^[0-9]{8,8}$/i.test(`${e.target.value}`))) {
            this.phoneChecker = "success"
            this.phone = e.target.value
        } else {
            this.phoneChecker = "error"
            this.phone = ""
        }
    }

    handleCareCenter = (value) => {
        this.careCenter = value

    }

    handleDiagnoses = (value) => {
        this.diagnoses = value
    }


    render() {
        return (<div>
            <Row>
                <Col span={12}>
                    <Divider>Udfyld information om borgeren</Divider>
                    <Form {...formItemLayout}>
                        <Form.Item
                            label="Navn: "
                            validateStatus={this.nameChecker}
                            help={this.nameChecker != "success" && "Navn må kun indeholde bogstaver"}
                        >
                            <Input onChange={this.handleName} />
                        </Form.Item>
                        <Form.Item
                            label="Adresse: "
                            validateStatus={this.addressChecker}
                            help={this.addressChecker != "success" && "Udfyld adressen"}
                        >
                            <Input onChange={this.handleAddress} />
                        </Form.Item>
                        <Form.Item
                            label="By: "
                            validateStatus={this.cityChecker}
                            help={this.cityChecker != "success" && "Indtast en by"}
                        >
                            <Input onChange={this.handleCity} />
                        </Form.Item>
                        <Form.Item
                            label="Postnummer: "
                            validateStatus={this.zipChecker}
                            help={this.zipChecker != "success" && "Indtast postnummer"}
                        >
                            <Input onChange={this.handleZip} />
                        </Form.Item>
                        <Form.Item
                            label="CPR: "
                            validateStatus={this.cprChecker}
                            help={this.cprChecker != "success" && "Indtast CPR-nummer"}
                        >
                            <Input onChange={this.handleCPR} />
                        </Form.Item>
                        <Form.Item
                            label="Telefon: "
                            validateStatus={this.phoneChecker}
                            help={this.phoneChecker != "success" && "Indtast telefonnummer"}
                        >
                            <Input onChange={this.handlePhone} />
                        </Form.Item>
                    </Form>
                    <Divider>Vælg bosted</Divider>
                    <Select defaultValue="Vælg bosted" style={{ width: 180, marginLeft: 150, marginTop: 10 }} onChange={this.handleCareCenter}>
                        <Option value="bf9fc975-14b9-41f3-8bf4-a5ff04fe0e64">Bofællesskabet Å-huset</Option>
                        <Option value="Botilbud Placeholder">Botilbuddet Rydsåvej</Option>
                    </Select>
                    <Divider>Vælg diagnoser</Divider>
                    <Select
                        mode="multiple"
                        style={{ width: 220, marginLeft: 150, marginTop: 10 }}
                        placeholder="Vælg diagnoser"
                        onChange={this.handleDiagnoses}

                    >
                        <Option value="alkoholmisbrug">Alkoholmisbrug</Option>
                        <Option value="stofmisbrug">Stofmisbrug</Option>
                        <Option value="handicappet">Handicappet</Option>
                        <Option value="angst">Angst</Option>
                    </Select>
                    <Button type="primary" style={{ marginLeft: 10 }} onClick={this.handleCreate}>Opret borger</Button>

                </Col>
                <Col span={12}></Col>
            </Row>
        </div >)
    }
}