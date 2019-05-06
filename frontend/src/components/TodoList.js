import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Skeleton, List } from 'antd'

@inject('todoStore')
@observer
export default class TodoList extends Component {
  constructor(props) {
    super(props)
    this.getTodos = () => this.props.todoStore.fetchTodos()
  }

  componentWillMount() {
    this.getTodos()
  }

  render() {
    const { todoStore } = this.props
    const { todos, isFetching } = todoStore
    return (
      <div>
        <List
          itemLayout="vertical"
          size="large"
          dataSource={todos}
          renderItem={todo => (
            <List.Item
              key={todo.title}>
              <Skeleton loading={isFetching} active>
                <List.Item.Meta
                  title={todo.title}
                  description={todo.completed}
                />
                {todo.title}
              </Skeleton>
            </List.Item>
          )}
        />
      </div>
    )
  }
}