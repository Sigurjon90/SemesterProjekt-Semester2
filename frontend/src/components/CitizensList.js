import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import CitizenName from './Citizen'
import CitizenExtra from './CitizenExtra'
import {
  List, Skeleton, Avatar, Spin,
} from 'antd';

@inject('citizensStore')
@observer
export default class CitizensList extends Component {
  constructor(props) {
    super(props)
    // Maps a method over in another metod -> getCitizens is now the same as fetchCitizens
    this.getCitizens = () => this.props.citizensStore.fetchCitizens()
  }

  componentWillMount() {
    this.getCitizens()
  }

  render() {
    const { citizensStore } = this.props
    const { citizens, isFetching } = citizensStore
    return (
      <div>
        <List
            dataSource={citizens}
            renderItem={citizen => (
              <List.Item key={citizen.name}>
                <Skeleton loading={isFetching} active>
                  <List.Item.Meta
                    avatar={<Avatar src="https://webiconspng.com/wp-content/uploads/2017/09/Donald-Trump-PNG-Image-27422.png" />}
                    title={<a href="https://ant.design">{citizen.name.last}</a>}
                    description={citizen.adress}
                  />
                  <div>{citizen.zip}</div>
                </Skeleton>
              </List.Item>
            )}
          >
        </List>
        {citizens.map((citizen) => <CitizenName {...citizen}/>)}
        {citizens.map((citizenExtra) => <CitizenExtra {...citizenExtra}/>)}
        {"Penis"}
      </div>
    )
  }
}