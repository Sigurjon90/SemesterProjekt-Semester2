import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Row, Col, Alert, Button, Spin, Input } from "antd";
import moment from "moment";
import ReactMarkdown from "react-markdown"

@inject("diaryStore")
@observer
export default class Diary extends Component {
    constructor(props) {
        super(props);
        this.getDiary = (id) => this.props.diaryStore.fetchDiary(id);
    }

    state = {
        createContent: ""
    };

    handleDiarySwitch = () => {
        this.props.handleDiarySwitch()
    }

    onChangeHandler = (evt) => {
        this.setState({ createContent: evt.target.value })
    }

    createDiary = () => {
        const { citizenID } = this.props;
        console.log("citizen id")
        console.log(citizenID)
        const createdDiary = {
            citizenID: citizenID,
            authorID: 'afda2cd5-6fd9-40ff-a0db-938af02a281c',
            content: this.state.createContent,
            title: "Some title"
        }
        this.props.diaryStore.createDiary(createdDiary)
    }

    componentWillMount() {
        const { citizenID } = this.props;
        const { diary } = this.getDiary(citizenID)
    }


    render() {
        const { diaryStore } = this.props;
        const { diary, isFetching, error } = diaryStore;
        const isLoaded = !isFetching && error === null
        const { TextArea } = Input
        return (<div>
            {isFetching && <Spin />}
            {diary == null &&
                <Row>
                    <Col span={8}>
                    </Col>
                    <Col span={16}>
                        <h3><Alert message="Denne borger har ingen dagbog. Du kan oprette en nu." type="warning" /></h3>
                        <TextArea rows={5} onChange={this.onChangeHandler} />
                        <Button type="primary" size="default" onClick={this.createDiary}>Opret dagbog for borger</Button>
                    </Col>
                </Row>
            }
            {isLoaded && <div>
                <Row>
                    <Col span={8}>
                        <h3><strong>Senest redigeret:</strong></h3>
                    </Col>
                    <Col span={16}>
                        <h3><strong>Dagbog:</strong></h3>
                    </Col>
                </Row>
                <Row>
                    <Col span={8}>
                        <h3>Side tekst...</h3>
                    </Col>
                    <Col span={16}>
                        <ReactMarkdown source={`${diary.content}`} />
                    </Col>
                </Row>
                <Row>
                    <Col span={8}>
                    </Col>
                    <Col span={16}>
                        <Button type="primary" onClick={this.handleDiarySwitch}>Redigér</Button>
                        <Button type="danger" style={{ marginLeft: 15, marginTop: 5 }}>Arkivér dagbog?</Button>
                    </Col>
                </Row>
            </div>}
        </div>)
    }
}
