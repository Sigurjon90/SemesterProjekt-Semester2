import React, { Component } from "react";
import { Layout, Menu, Icon } from "antd";
import { Link } from "react-router-dom";

const { Header, Content, Footer, Sider } = Layout;
const SubMenu = Menu.SubMenu;

/*
Link to icons: https://ant.design/components/icon/
to add new menu and this in the bottom ( Or in the place you menu should apear):
{
    title: "Menu name",
    link: "/link",
    icon: "insert icon here"
  }
*/

const menuItems = [
  {
    title: "HEJSA",
    link: "/link",
    icon: "edit"
  }
];

const menu = menuItems.map((item, index) => (
  <Menu.Item key={index}>
    <Link to={item.link}>
      <Icon type={item.icon} />
      <span>{item.title}</span>
    </Link>
  </Menu.Item>
));

export default class Theme extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Layout style={{ minHeight: "100vh" }}>
        <Sider>
          <div className="logo" />
          <Menu theme="dark" defaultSelectedKeys={["1"]} mode="inline">
            {menu}
          </Menu>
        </Sider>
        <Layout>
          <Header style={{ background: "#fff", padding: 0 }} />
          <Content style={{ margin: "0 16px" }}>{this.props.children}</Content>
          <Footer style={{ textAlign: "center" }}>
            Ant Design ©2018 Created by Ant UED
          </Footer>
        </Layout>
      </Layout>
    );
  }
}
