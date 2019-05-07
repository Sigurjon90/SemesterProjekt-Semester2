import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Link } from "react-router-dom";
import { Table, Alert, Tag } from "antd";

const columns = [
    {
        title: "Navn",
        dataIndex: "name",
        key: "name",
        render: (text, data) => <Link to={`/admin/citizens/edit/${data.id}`}>{text}</Link>
    },
    {
        title: "Addresse",
        dataIndex: "address",
        key: "adress"
    },
    {
        title: "By",
        dataIndex: "city",
        key: "city"
    },
    {
        title: "Postnummer",
        dataIndex: "zip",
        key: "zip"
    },
    {
        title: "Telefon",
        dataIndex: "phoneNumber",
        key: "phoneNumber"
    },
    {
        title: "Diagnoser",
        key: "diagnoses",
        dataIndex: "diagnoses",
        render: tags => (
            <span>
                {tags.map(tag => {
                    let color = tag.length > 2 ? "geekblue" : "green";
                    if (tag === "Psykotisk") {
                        color = "volcano";
                    }
                    return (
                        <Tag color={color} key={tag}>
                            {tag.toUpperCase()}
                        </Tag>
                    );
                })}
            </span>
        )
    }
];

const primaryCitizensConfig = {
    pagination: false
};

const otherCitizensConfig = {
    pagination: {
        defaultPageSize: 5
    }
};

@inject("citizensStore")
@observer
export default class CareCenterList extends Component {
    constructor(props) {
        super(props);
        // Maps a method over in another metod -> getCitizens is now the same as fetchCitizens
        this.getCitizens = () => this.props.citizensStore.fetchCitizens();
    }

    componentWillMount() {
        this.getCitizens();
    }

    render() {
        const { citizensStore } = this.props;
        const { otherCitizens } = citizensStore;
        return (
            <div>
                <Alert message="Vælg og redigér en borger" type="info" showIcon />
                <Table
                    {...otherCitizens}
                    {...otherCitizensConfig}
                    columns={columns}
                    dataSource={otherCitizens}
                />

            </div>
        );
    }
}
