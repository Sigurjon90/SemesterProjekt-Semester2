import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Row, Col, Alert, Button, Spin, Input } from "antd";
import moment from "moment";

@inject("journalStore")
@observer
export default class Journal extends Component {
  constructor(props) {
    super(props);
    this.getJournal = (id) => this.props.journalStore.fetchJournal(id);
  }

  state = {
    createContent: ""
  };

  componentWillMount() {
    const { citizenID } = this.props;
    const { journal } = this.getJournal(citizenID)
    console.log("WILL MOUNT CITI ID")
    console.log(citizenID)
    console.log(this.getJournal(citizenID))
  }

  onChangeHandler = (evt) => {
    this.setState({ createContent: evt.target.value })
  }

  handleClick = () => {
    this.props.handleClick()
  }

  createJournal = () => {
    const { citizenID } = this.props;
    console.log("CREATEJOURNAL CITI ID")
    console.log(citizenID)
    const createdJournal = {
      citizensID: citizenID,
      authorID: 'afda2cd5-6fd9-40ff-a0db-938af02a281c',
      content: this.state.createContent
    }

    console.log(createdJournal)
    this.props.journalStore.createJournal(createdJournal)
    //this.props.handleClick()


  }

  render() {
    const { journalStore } = this.props;
    const { journal, isFetching, error } = journalStore;
    const isLoaded = !isFetching && error === null
    const { TextArea } = Input
    return (<div>
      {isFetching && <Spin />}
      {journal == null &&
        <Row>
          <Col span={8}>
          </Col>
          <Col span={16}>
            <h3><Alert message="Der eksisterer endnu ingen journal på denne borger. Du kan oprette en nu." type="warning" /></h3>
            <TextArea rows={4} onChange={this.onChangeHandler} />
            <Button type="primary" size="default" onClick={this.createJournal}>Opret journal på borger</Button>
          </Col>
        </Row>
      }
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
