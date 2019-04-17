import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { PageHeader } from "antd";
import axios from "axios";
import { Row, Col, Tag } from "antd";
import Journal from "./Journal";

export default class SingleCitizen extends Component {
  state = {
    citizen: {
      diagnoses: []
    }
  };

  componentWillMount() {
    const {
      match: { params }
    } = this.props;
    axios
      .get(`http://localhost:1338/citizens/${params.id}`)
      .then(({ data }) => {
        this.setState({ citizen: { ...data } });
      });
  }


  render() {
    const { citizen } = this.state;
    const theId = citizen.id
    console.log(theId)

    return (
      <div>
        <div>
          <Row>
            <Col span={24}>
              <h3>Borger information:</h3>
            </Col>
          </Row>
          <Row>
            <Col span={4}>
              <h4>Navn</h4>
            </Col>
            <Col span={4}>
              <h4>Adresse</h4>
            </Col>
            <Col span={4}>
              <h4>By</h4>
            </Col>
            <Col span={4}>
              <h4>Postnummer</h4>
            </Col>
            <Col span={4}>
              <h4>Telefon</h4>
            </Col>
            <Col span={4}>
              <h4>Diagnoser</h4>
            </Col>
          </Row>
          <Row>
            <Col className="rowcolor" span={4}>
              {citizen.name}
            </Col>
            <Col span={4}>{citizen.adress}</Col>
            <Col span={4}>{citizen.city}</Col>
            <Col span={4}>{citizen.zip}</Col>
            <Col span={4}>{citizen.phoneNumber}</Col>
            <Col span={4}>
              {citizen.diagnoses.map(diagnose => (
                <Tag color="green">{diagnose}</Tag>
              ))}
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <h3>Journal</h3>
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              {(typeof theId !== 'undefined') && <Journal citizenID={theId} />}
            </Col>
          </Row>
          <Row>
            <Col span={24}>
              <h3>Dagbog</h3>
            </Col>
          </Row>
          <Row>
            <Col span={24}>Dagbog component</Col>
          </Row>
        </div>
      </div>
    );
  }
}
