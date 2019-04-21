import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Row, Col, Tag, Divider, Input, Button, Spin } from "antd";
import Journal from "./Journal";
import EditJournal from "./EditJournal";

@inject("citizensStore")
@observer
export default class SingleCitizen extends Component {
  constructor(props) {
    super(props);
    // Maps a method over in another metod -> getCitizens is now the same as fetchCitizens
    this.getCitizen = (id) => this.props.citizensStore.fetchCitizen(id);
  }
  state = {
    citizen: {
      diagnoses: []
    },
    isClicked: false
  };

  // Get from store
  componentWillMount() {
    const {
      match: { params }, citizensStore
    } = this.props;
    console.log(params.id)
    this.getCitizen(params.id)
  }

  handleClick = () => {
    const { isClicked } = this.state;
    this.setState({ isClicked: !isClicked })
  }

  submitChanges = () => {
    this.props.editJournal.putJournalChanges();
  }

  render() {
    const { citizensStore } = this.props
    const { isClicked } = this.state;
    const { citizen, isFetching } = citizensStore
    const theId = citizen && citizen.id
    const { TextArea } = Input;
    console.log(isFetching)
    return (
      <div>
        {!isFetching &&
          <div>
            <Divider><strong>Borger</strong></Divider>
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
                  <Tag color="green" key={diagnose}>{diagnose}</Tag>
                ))}
              </Col>
            </Row>
            <Divider><strong>Journal</strong></Divider>
            <Row>
              <Col span={24}>

                {!isClicked && (typeof theId !== 'undefined') && <Journal citizenID={theId} handleClick={this.handleClick} />}
                {isClicked && <EditJournal handleClick={this.handleClick} />}

                <Row>
                  <Col span={8}>
                  </Col>
                  <Col span={16}>
                  </Col>
                </Row>
              </Col>
            </Row>
            <Divider><strong>Dagbog</strong></Divider>
            <Row>
              <Col span={24}>
                <h3>Dagbog</h3>
              </Col>
            </Row>
            <Row>
              <Col span={24}>Dagbog component</Col>
            </Row>
          </div>
        } </div>
    );
  }
}
