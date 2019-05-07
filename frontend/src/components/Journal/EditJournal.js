import React, { Component } from "react";
import { inject, observer } from "mobx-react"
import { Row, Col, Tag, Divider, Button, Input } from "antd";
import moment from "moment";
import "moment/locale/da";


@inject("journalStore")
@observer
export default class EditJournal extends Component {
    constructor(props) {
        super(props);
        // Maps a method over in another metod -> getCitizens is now the same as fetchCitizens
        this.getJournal = (id) => this.props.journalStore.fetchJournal(id);
    }

    state = {
        content: this.props.journalStore.journal.content
    };

    handleSubmit = () => {
        const { journal } = this.props.journalStore;
        const updatedJournal = {
            id: journal.id,
            content: this.state.content,
            authorID: 'afda2cd5-6fd9-40ff-a0db-938af02a281c'
        }
        this.props.journalStore.putJournalChanges(updatedJournal)
        this.props.handleClick()


    }

    onChangeHandler = (evt) => {
        this.setState({ content: evt.target.value })
    }

    componentWillMount() {

    }

    render() {
        const { journalStore } = this.props;
        const { journal, isFetching, error } = journalStore;
        const isLoaded = !isFetching && error === null
        const { TextArea } = Input;
        return (<div>
            <Row>
                <Col span={8}>
                </Col>
                <Col span={16}>
                    <h3>Senest redigeret {moment(journal.dateModified).locale("dk").fromNow()}</h3>
                    <TextArea rows={10} onChange={this.onChangeHandler} defaultValue={journal.content} />
                </Col>
            </Row>
            <Row>
                <Col span={8}>

                </Col>
                <Col span={16}>
                    <Button type="primary" onClick={this.handleSubmit}>Gem</Button>

                </Col>
            </Row>
        </div>)
    }
}