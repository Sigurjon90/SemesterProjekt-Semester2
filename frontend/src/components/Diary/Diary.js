import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { observable } from "mobx";
import { Row, Col, Alert, Button, Spin, Input, Tabs } from "antd";
import moment from "moment";
import ReactMarkdown from "react-markdown"

const TabPane = Tabs.TabPane

@inject("diaryStore")
@observer
export default class Diary extends Component {
    constructor(props) {
        super(props);
        this.getDiary = (id) => this.props.diaryStore.fetchDiary(id);
    }

    state = {
        createContent: "",
    };

    handleDiarySwitch = () => {
        this.props.handleDiarySwitch()
    }

    onChangeHandler = (evt) => {
        this.setState({ createContent: evt.target.value })
    }

    createDiary = () => {
        const { citizenID } = this.props;
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
        const { diaArray } = this.props.diaryStore.fetchDiaries(citizenID)
    }


    render() {
        const { diaryStore } = this.props;
        const { diary, isFetching, isFetchingSecond, error, diaArray } = diaryStore;
        const isLoaded = !isFetching && error === null
        const { TextArea } = Input
        const { citizenID } = this.props
        return (<div>
            {isFetching && <Spin />}
            {diary == null &&
                <Row>
                    <Col span={8}>
                    </Col>
                    <Col span={16}>
                        <h3><Alert message="Denne borger har ingen dagbog. Du kan oprette en nu." type="warning" /></h3>
                        <TextArea rows={5} onChange={this.onChangeHandler} />
                        <Button type="primary" size="default" onClick={this.createDiary} style={{ marginTop: 5 }}>Opret dagbog for borger</Button>
                    </Col>
                </Row>
            }
            {isLoaded && <div>
                <Tabs
                    defaultActiveKey={1}
                    tabPosition="top"
                    style={{ height: 220 }}>

                    {diaArray.slice().reverse().map((thing) =>
                        <TabPane tab={moment(thing.dateModified).format('DD-MM-YYYY')} key={thing.id}>
                            <Row>
                                <Col span={8}>
                                    <strong>Ændringstidspunkt:</strong> {moment(thing.dateModified).format('DD-MM-YYYY HH:mm')}
                                </Col>
                                <Col span={16}>
                                    <ReactMarkdown source={`${thing.content}`} />
                                </Col>
                            </Row></TabPane>)}
                </Tabs>
                <Row>
                    <Col span={24}>

                        <Button type="primary" onClick={this.handleDiarySwitch}>Opret nyt indlæg i dagbog</Button>
                    </Col>
                </Row>


            </div>}
        </div>)
    }
}
