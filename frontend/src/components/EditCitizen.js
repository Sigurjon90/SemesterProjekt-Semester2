import React, { Component } from "react";
import { inject, observer } from "mobx-react"
import { Divider } from "antd";
import moment from "moment";
import "moment/locale/da";
import CitizensList from "./CitizensList"


@inject("citizensStore")
@observer
export default class EditCitizen extends Component {
    constructor(props) {
        super(props);
    }

    state = {
    };


    componentWillMount() {

    }

    render() {
        return (<div>
            <CitizensList />
            <Divider orientation="left">Redigér borger-view</Divider>
            <h3>Tekst felter med borger info der kan redigeres</h3>

        </div>)
    }
}