import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Row, Col, Tag, Divider, Input, Button, Spin } from "antd";
import Journal from "../Journal/Journal";
import EditJournal from "../Journal/EditJournal";
import Diary from "../Diary/Diary";
import EditDiary from "../Diary/EditDiary";
import hasAnyRole from "../../utils/auth"

@inject("citizensStore")
@observer
export default class SingleCitizen extends Component {
  constructor(props) {
    super(props);
    this.getCitizen = (id) => this.props.citizensStore.fetchCitizen(id);
  }

  state = {
    citizen: {
      diagnoses: []
    },
    isClicked: false,
    diarySwitch: false
  };

  componentWillMount() {
    const {
      match: { params }
    } = this.props;
    this.getCitizen(params.id)
  }

  handleClick = () => {
    const { isClicked } = this.state;
    this.setState({ isClicked: !isClicked })
  }

  handleDiarySwitch = () => {
    const { diarySwitch } = this.state;
    this.setState({ diarySwitch: !diarySwitch })
  }

  submitChanges = () => {
    this.props.editJournal.putJournalChanges();
  }

  render() {
    const { citizensStore } = this.props
    const { isClicked, diarySwitch } = this.state;
    const { citizen, isFetching } = citizensStore
    const theId = citizen && citizen.id
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
              <Col span={4}>{citizen.address}</Col>
              <Col span={4}>{citizen.city}</Col>
              <Col span={4}>{citizen.zip}</Col>
              <Col span={4}>{citizen.phoneNumber}</Col>
              <Col span={4}>
                {citizen.diagnoses.map(diagnose => (
                  <Tag color="green" key={diagnose}>{diagnose}</Tag>
                ))}
              </Col>
            </Row>
            {hasAnyRole(["admin", "caseworker"]) &&
              <div>
                <Divider><strong>Sag</strong></Divider>
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
              </div>
            }
            <Divider><strong>Dagbog</strong></Divider>
            <Row>
              <Col span={24}>
              </Col>
            </Row>
            <Row>
              <Col span={24}>
                {!diarySwitch && (typeof theId !== 'undefined') && <Diary citizenID={theId} handleDiarySwitch={this.handleDiarySwitch} />}
                {diarySwitch && <EditDiary handleDiarySwitch={this.handleDiarySwitch} citizenID={theId} />}
              </Col>
            </Row>
          </div>
        } </div>
    );
  }
}
