import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Alert } from 'antd'

export default class CitizenExtra extends Component {

  render() {
    const { name, zip, city } = this.props

    return (
      <div>
          <Alert message={name} type="success" />
      </div>
    )
  }
}