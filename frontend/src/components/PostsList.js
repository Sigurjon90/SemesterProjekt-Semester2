import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Link } from 'react-router-dom'
import { Skeleton, List } from 'antd'

@inject('postsStore')
@observer
export default class PostsList extends Component {
  constructor(props) {
    super(props)
    this.getPosts = () => this.props.postsStore.fetchPosts()
  }

  componentWillMount() {
    this.getPosts()
  }

  render() {
    const { postsStore } = this.props
    const { posts, isFetching } = postsStore
    return (
      <div>
        <List
          itemLayout="vertical"
          size="large"
          dataSource={posts}
          renderItem={post => (
            <List.Item
              key={post.id}>
              <Skeleton loading={isFetching} active>
                <List.Item.Meta
                  title={<Link to={`/posts/${post.id}`}>{post.title}</Link>}
                />
                {post.body}
              </Skeleton>
            </List.Item>
          )}
        />
      </div>
    )
  }
}