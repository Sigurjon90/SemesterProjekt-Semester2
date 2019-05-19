import React, { Component } from "react";
import { inject, observer } from "mobx-react"
import { Form, Input, Button, Divider, Select, Alert, Row, Col, Spin } from "antd";
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

@inject("citizensStore", "fakeCPRStore")
@observer
class CreateCitizen extends Component {
    constructor(props) {
        super(props);
        this.getCitizen = (id) => this.props.citizensStore.fetchCitizen(id);
        this.getCPRCitzen = (ssn) => this.props.fakeCPRStore.fetchCPRregister(ssn);
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
    @observable isCitizenCreated = false

    componentWillMount() {
        this.props.fakeCPRStore.reset()
    }

    getCitizenFromCPR() {
        this.getCPRCitzen(this.cpr)
    }

    handleCreate = () => {
        this.props.form.validateFields((err, values) => {
            if (!err) {
                const createdCitizen = {
                    name: this.name || values.name,
                    address: this.address || values.address,
                    city: this.city || values.city,
                    zip: this.zip || values.zip,
                    cpr: this.cpr,
                    phoneNumber: this.phone || values.phoneNumber,
                    careCenterID: this.careCenter,
                    diagnoses: this.diagnoses
                }
                this.props.citizensStore.createCitizen(createdCitizen)
                this.isCitizenCreated = true
            }
        })
    }

    handleName = (e) => {
        if ((/^[a-z_ ÆØÅæøå]{5,25}$/i.test(`${e.target.value}`))) {
            this.nameChecker = "success"
            this.name = e.target.value
        } else {
            this.nameChecker = "error"
            this.name = ""
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

    handleCity = (e) => {
        if ((/^[a-z_ ÆØÅæøå]{3,25}$/i.test(`${e.target.value}`))) {
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
        const { fakeCPRStore, form } = this.props
        const { isFetching, citizen } = fakeCPRStore
        const { getFieldDecorator } = form
        const hasCitizen = citizen !== null
        return (<div>
            {this.isCitizenCreated &&
                <div>
                    <Alert message="Brugeren er nu oprettet" type="success" />
                </div>
            }
            {!this.isCitizenCreated &&
                <div>
                    <Row>
                        <Col span={12}>
                            <Divider>Udfyld information om borgeren</Divider>
                            <Form {...formItemLayout}>
                                <Form.Item
                                    label="CPR: "
                                    validateStatus={this.cprChecker}
                                    help={this.cprChecker != "success" && "Indtast CPR-nummer"}
                                >
                                <Row gutter={12}>
                                    <Col span={20}>
                                        <Input onChange={this.handleCPR} />
                                    </Col>
                                    <Col span={2}>
                                    <Button type="primary" shape="circle" disabled={!(this.cprChecker === "success")}
                                        icon="search" onClick={() => this.getCitizenFromCPR()} />
                                    </Col>
                                </Row>
                                </Form.Item>
                                <div className="spinner">
                                {isFetching && 
                                    <Spin size="large" tip="Henter borger fra CPR-register" />
                                }
                                {!isFetching &&
                                <div>
                                <Form.Item
                                    label="Navn"
                                    validateStatus={hasCitizen ? "success" : this.nameChecker}
                                    help={(hasCitizen) ? "" : this.nameChecker != "success" && "Navn må kun indeholde bogstaver"}
                                >
                                {getFieldDecorator('name', { initialValue: hasCitizen ? citizen.name : '' })(
                                    <Input onChange={this.handleName} />
                                )}
                                </Form.Item>
                                <Form.Item
                                    label="Adresse"
                                    validateStatus={hasCitizen ? "success" : this.addressChecker}
                                    help={hasCitizen ? "" : this.addressChecker != "success" && "Udfyld adressen"}
                                >
                                {getFieldDecorator('address', { initialValue: hasCitizen ? citizen.address : '' })(
                                    <Input onChange={this.handleAddress} />
                                )}
                                </Form.Item>
                                <Form.Item
                                    label="By"
                                    validateStatus={hasCitizen ? "success" : this.cityChecker}
                                    help={hasCitizen ? "" : this.cityChecker != "success" && "Indtast en by"}
                                >
                                {getFieldDecorator('city', { initialValue: hasCitizen ? citizen.city : '' })(
                                    <Input onChange={this.handleCity} />
                                )}
                                </Form.Item>
                                <Form.Item
                                    label="Postnummer"
                                    validateStatus={hasCitizen ? "success" : this.zipChecker}
                                    help={hasCitizen ? "" : this.zipChecker != "success" && "Indtast postnummer"}
                                >
                                {getFieldDecorator('zip', { initialValue: hasCitizen ? citizen.zip : '' })(
                                    <Input onChange={this.handleZip} />
                                )}
                                </Form.Item>
                                <Form.Item
                                    label="Telefon"
                                    validateStatus={hasCitizen ? "success" : this.phoneChecker}
                                    help={hasCitizen ? "" : this.phoneChecker != "success" && "Indtast telefonnummer"}
                                >
                                {getFieldDecorator('phoneNumber', { initialValue: hasCitizen ? citizen.phoneNumber : '' })(
                                    <Input onChange={this.handlePhone} />
                                )}
                                </Form.Item>
                                </div>
                                }
                                </div>
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
                </div>}
        </div >)
    }
}

const Wrapped = Form.create({ name: 'create_citizen' })(CreateCitizen)
export default Wrapped