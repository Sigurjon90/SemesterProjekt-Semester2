import React, { Component } from "react";
import { inject, observer } from "mobx-react"
import { Row, Col, Button, Input } from "antd";


@inject("diaryStore")
@observer
export default class EditDiary extends Component {
    constructor(props) {
        super(props);
        this.getDiary = (id) => this.props.diaryStore.fetchDiary(id);
    }

    state = {
        content: this.props.diaryStore.diary.content
    };

    // citizenID skal måske ændres tilbage til id og så skal SQL være på id og ikke citizenID
    handleSubmit = () => {
        const { diary } = this.props.diaryStore;
        const updatedDiary = {
            citizenID: diary.citizenID,
            content: this.state.content,
            authorID: 'afda2cd5-6fd9-40ff-a0db-938af02a281c'
        }
        this.props.diaryStore.putDiaryChanges(updatedDiary)
        this.props.handleDiarySwitch()
    }

    onChangeHandler = (evt) => {
        this.setState({ content: evt.target.value })
    }

    componentWillMount() {

    }

    render() {
        const { diaryStore } = this.props;
        const { diary, isFetching, error } = diaryStore;
        const isLoaded = !isFetching && error === null
        const { TextArea } = Input;
        return (<div>
            <Row>
                <Col span={8}>
                </Col>
                <Col span={16}>
                    <h3>Senest redigeret...</h3>
                    <TextArea rows={10} onChange={this.onChangeHandler} defaultValue={diary.content} />
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