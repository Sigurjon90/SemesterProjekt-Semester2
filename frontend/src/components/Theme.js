import React, { Component } from "react";
import { Layout, Menu, Breadcrumb, Button, Icon } from "antd";
import { Link } from "react-router-dom";

const { Header, Content, Sider } = Layout;
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
  },
  {
    title: "asdasd",
    link: "/linsk",
    icon: "edit"
  },
  {
    title: "ASDSAD",
    link: "/asd",
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

  onLogout = e => {
    localStorage.removeItem("authorization");
    window.location.replace("/login");
  }

  onCollapse = (collapsed) => {
    console.log(collapsed);
  }

  render() {
    return (
    <Layout style={{ minHeight: '100vh' }}>
    <Header className="header">
      <div className="logo" />
      <Button className="login-button" onClick={e => this.onLogout()}>Log ud</Button>
      <Menu
        theme="dark"
        mode="horizontal"
        style={{ lineHeight: '64px' }}
      >
      </Menu>
    </Header>
    <Layout>
      <Sider collapsible width={200} style={{ background: '#fff' }}>
        <Menu
          mode="inline"
          defaultSelectedKeys={['1']}
          defaultOpenKeys={['sub1']}
          style={{ height: '100%', borderRight: 0 }}
        >
        {menu}
        </Menu>
      </Sider>
      <Layout style={{ padding: '0 24px 24px' }}>
        <Breadcrumb style={{ margin: '16px 0' }}>
          <Breadcrumb.Item>Hjem</Breadcrumb.Item>
          <Breadcrumb.Item>Forside</Breadcrumb.Item>
        </Breadcrumb>
        <Content style={{
          background: '#fff', padding: 24, margin: 0, minHeight: 280,
        }}
        >
          {this.props.children}
        </Content>
      </Layout>
    </Layout>
  </Layout>
    );
  }
}
