import React, { Component } from "react";
import { Layout, Menu, Breadcrumb, Button, Icon } from "antd";
import { Link } from "react-router-dom";
import { inject, observer } from "mobx-react";
const { Header, Content, Sider } = Layout;
import { hasRole } from "../utils/auth"
import logo from "../images/logo.svg"

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
    title: "Borgere",
    link: "/citizens",
    icon: "user"
  },
  {
    title: "Admin Borgere",
    link: "/admin/citizens",
    icon: "user-add"
  },
  {
    title: "Admin Brugere",
    link: "/admin/users",
    icon: "usergroup-add"
  }
]

const breadcrumbNameMap = {
  '/admin/citizens': 'Borgere',
  '/admin/users': 'Brugere',
  '/admin': 'Administere'
}

@inject("loginStore", "routing")
@observer
export default class Theme extends Component {
  constructor(props) {
    super(props);
  }

  onLogout = e => {
    localStorage.removeItem("authorization");
    window.location.replace("/");
  }

  render() {
    const { loginStore, routing } = this.props

    const menu = menuItems.map((item, index) => {
      if (item.title.includes("Admin") && !hasRole("admin")) {
        console.log("Re-render")
        return (null)
      } else {
        return (<Menu.Item key={index}>
          <Link to={item.link}>
            <Icon type={item.icon} />
            <span>{item.title}</span>
          </Link>
        </Menu.Item>)
      }
    })

    const pathSnippets = routing.history.location.pathname.split('/').filter(i => i);
    const extraBreadcrumbItems = pathSnippets.map((_, index) => {
      const url = `/${pathSnippets.slice(0, index + 1).join('/')}`;
      let name = breadcrumbNameMap[url]
      if (url === '/citizens') return null
      if (name === undefined && url.includes('/citizens/')) name = 'Borger'
      return (
        <Breadcrumb.Item key={url}>
          <Link to={url}>
            {name}
          </Link>
        </Breadcrumb.Item>
      )
    })

    const breadcrumbItems = [(
      <Breadcrumb.Item key="home">
        <Link to="/citizens">Forside</Link>
      </Breadcrumb.Item>
    )].concat(extraBreadcrumbItems)

    return (
      <div style={{ height: '100%' }}>
        {!loginStore.isLoggedIn &&
          <div style={{ height: '100%' }}>
            {this.props.children}
          </div>
        }
        {loginStore.isLoggedIn &&
          <Layout style={{ minHeight: '100vh' }}>
            <Header className="header">
              <img src={logo} className="logo" alt="logo" onClick={e => routing.push("/citizens")} />
              <Button className="login-button" onClick={e => this.onLogout()}>Log ud</Button>
              <Menu
                theme="dark"
                mode="horizontal"
                style={{ lineHeight: '64px' }}
              >
              </Menu>
            </Header>
            <Layout>
              <Sider collapsible width={200} >
                <Menu
                  mode="inline"
                  defaultSelectedKeys={['0']}
                  style={{ height: '100%', borderRight: 0 }}
                >
                  {menu}
                </Menu>
              </Sider>
              <Layout style={{ padding: '0 24px 24px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                  {breadcrumbItems}
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
        }
      </div>
    )
  }
}
