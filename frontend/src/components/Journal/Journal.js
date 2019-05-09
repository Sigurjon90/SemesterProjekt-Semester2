import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Row, Col, Alert, Button, Spin, Input, Menu, Dropdown, Icon, Checkbox, Select } from "antd";
import moment from "moment";
import ReactMarkdown from "react-markdown"

@inject("journalStore")
@observer
export default class Journal extends Component {
  constructor(props) {
    super(props);
    this.getJournal = (id) => this.props.journalStore.fetchJournal(id);
  }

  state = {
    createContent: "",
    paragraph: "",
    consent: false,
    handleMunicipality: ""
  };

  componentWillMount() {
    const { citizenID } = this.props;
    const { journal } = this.getJournal(citizenID)
  }

  onChangeHandler = (evt) => {
    this.setState({ createContent: evt.target.value })
  }

  handleClick = () => {
    this.props.handleClick()
  }

  handleParagraph = (value) => {
    this.setState({ paragraph: `${value}` })
  }

  handleMunicipality = (value) => {
    this.setState({ municipality: `${value}` })
  }

  handleConsent = () => {
    this.setState({ consent: !this.state.consent })
  }

  createJournal = () => {
    const { citizenID } = this.props;
    const createdJournal = {
      citizensID: citizenID,
      paragraph: this.state.paragraph,
      municipality: this.state.municipality,
      consent: this.state.consent,
      authorID: 'afda2cd5-6fd9-40ff-a0db-938af02a281c',
      content: this.state.createContent
    }
    this.props.journalStore.createJournal(createdJournal)
  }

  render() {
    const { journalStore } = this.props;
    const { journal, isFetching, error } = journalStore;
    const isLoaded = !isFetching && error === null
    const { TextArea } = Input
    const Option = Select.Option
    return (<div>
      {isFetching && <Spin />}
      {journal == null &&
        <Row>
          <Col span={8}>
          </Col>
          <Col span={16}>

            <Select defaultValue="§ Vælg paragraf" style={{ width: 165, marginBottom: 5 }} onChange={this.handleParagraph}>
              <Option value="§18 Bosted">§18 Bosted</Option>
              <Option value="§7 Værgemål">§7 Værgemål</Option>
              <Option value="§12 Bostøtte">§12 Bostøtte</Option>

            </Select>

            <Select defaultValue="Handlekommune" style={{ width: 165, marginLeft: 10 }} onChange={this.handleMunicipality}>
              <Option value="Odense">Odense</Option>
              <Option value="København">København</Option>
              <Option value="Århus">Århus</Option>
              <Option value="Aalborg">Aalborg</Option>
            </Select>



            <Checkbox onChange={this.handleConsent} style={{ marginLeft: 10 }}>Har borgeret givet samtykke?</Checkbox>

            <h3><Alert message="Notér aftaler om det videre forløb, borgerinddragelse, særlige forhold. Husk at forsøge at afdække årsag til henvendelse kort og præcis." type="warning" /></h3>
            <TextArea rows={5} onChange={this.onChangeHandler} />
            <Button type="primary" size="default" onClick={this.createJournal} style={{ marginTop: 5 }}>Åbn sag på borger</Button>
          </Col>
        </Row>
      }
      {isLoaded && <div>
        <Row>
          <Col span={8}>
            <h3><strong>Senest redigeret:</strong></h3>
          </Col>
          <Col span={16}>
            <h3><strong>Sag:</strong></h3>
          </Col>
        </Row>
        <Row>
          <Col span={8}>
            <h3>{moment(journal.dateModified).format('DD-MM-YYYY HH:mm')}</h3>
          </Col>
          <Col span={16}>
            <Select defaultValue={`Paragraf §${journal.paragraph}`} style={{ width: 130 }} disabled />
            <Select defaultValue={`Handlekommune ${journal.municipality}`} style={{ marginLeft: 10, marginRight: 10, marginBottom: 10 }} disabled />
            <Select defaultValue={`Samtykke givet: ${journal.consent ? "Ja" : "Nej"}`} disabled />
            <ReactMarkdown source={`${journal.content}`} />
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
