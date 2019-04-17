import React, { Component } from "react";
import { inject, observer } from "mobx-react";
import { Link } from "react-router-dom";
import { List, Skeleton, Avatar, Table, Divider, Tag } from "antd";

const columns = [
  {
    title: "Name",
    dataIndex: "name",
    key: "name",
    render: (text, data) => <Link to={`/citizens/${data.id}`}>{text}</Link>
  },
  {
    title: "Address",
    dataIndex: "adress",
    key: "adress"
  },
  {
    title: "City",
    dataIndex: "city",
    key: "city"
  },
  {
    title: "ZIP Code",
    dataIndex: "zip",
    key: "zip"
  },
  {
    title: "Phone",
    dataIndex: "phoneNumber",
    key: "phoneNumber"
  },
  {
    title: "Diagnoses",
    key: "diagnoses",
    dataIndex: "diagnoses",
    render: tags => (
      <span>
        {tags.map(tag => {
          let color = tag.length > 5 ? "geekblue" : "green";
          if (tag === "PHP-programmør") {
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

const config = {
  pagination: {
    defaultPageSize: 8
  }
};

@inject("citizensStore")
@observer
export default class CitizensList extends Component {
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
    const { citizens } = citizensStore;
    return (
      <div>
        <Table
          {...citizens}
          {...config}
          columns={columns}
          dataSource={citizens}
        />
      </div>
    );
  }
}
