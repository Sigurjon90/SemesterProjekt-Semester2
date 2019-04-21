import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Row, Col, Tag, Button, Spin, Input } from "antd";
import moment from "moment";

@inject("journalStore")
@observer
export default class Journal extends Component {
  constructor(props) {
    super(props);
    // Maps a method over in another metod -> getCitizens is now the same as fetchCitizens
    this.getJournal = (id) => this.props.journalStore.fetchJournal(id);
  }

  state = {

  };

  // Metode der kører før FØRSTE render
  componentWillMount() {
    const { citizenID } = this.props;
    this.getJournal(citizenID);
  }

  handleClick = () => {
    this.props.handleClick()
  }

  render() {
    const { journalStore } = this.props;
    const { journal, isFetching, error } = journalStore;
    const isLoaded = !isFetching && error === null
    const { TextArea } = Input;
    return (<div>
      {isFetching && <Spin />}
      {isLoaded && <div>
        <Row>
          <Col span={8}>
            <h3><strong>Senest redigeret:</strong></h3>
          </Col>
          <Col span={16}>
            <h3><strong>Journal:</strong></h3>
          </Col>
        </Row>
        <Row>
          <Col span={8}>
            <h3>{moment(journal.dateModified).format('DD-MM-YYYY HH:mm')}</h3>
          </Col>
          <Col span={16}>
            <h3>{journal.content}</h3>
          </Col>
        </Row>
        <Row>
          <Col span={8}>
          </Col>
          <Col span={16}>
            <Button type="primary" onClick={this.handleClick}>Redigér</Button>
          </Col>
        </Row>
      </div>}
    </div>)
  }
}
