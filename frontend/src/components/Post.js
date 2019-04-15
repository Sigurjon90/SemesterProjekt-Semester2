import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { Comment, Icon, Tooltip, Avatar, Skeleton, Form, Button, Input, Spin } from 'antd'
import moment from 'moment'

const TextArea = Input.TextArea

@inject('postsStore')
@observer
export default class PostsList extends Component {
  state = {
    likes: 0,
    dislikes: 0,
    action: null,
    replyTo: false,
    body: null,
  }

  constructor(props) {
    super(props)
    this.getPost = (id) => this.props.postsStore.fetchPost(id)
    this.createPost = (post) => this.props.postsStore.postPost(post)
  }

  like = () => {
    this.setState({
      likes: 1,
      dislikes: 0,
      action: 'liked',
    })
  }

  dislike = () => {
    this.setState({
      likes: 0,
      dislikes: 1,
      action: 'disliked',
    })
  }

  replyTo = () => {
    this.setState({
      replyTo: true
    })
  }

  componentWillMount() {
    const { match: { params } } = this.props
    console.log(params.id)
    this.getPost(params.id)
  }

  handleChange = (e) => {
    this.setState({
      body: e.target.value,
    })
  }

  handleSubmit = (e) => {
    const { body } = this.state
    const post = { body: body }
    this.createPost(post)
    this.setState({
      replyTo: false,
      body: null
    })
  }

  render() {
    const { likes, dislikes, action, replyTo, body } = this.state
    const { postsStore } = this.props
    const { post, isFetching, comments, commentsIsFetching } = postsStore
    const actions = [
      <span>
        <Tooltip title="Like">
          <Icon
            type="like"
            theme={action === 'liked' ? 'filled' : 'outlined'}
            onClick={this.like}
          />
        </Tooltip>
        <span style={{ paddingLeft: 8, cursor: 'auto' }}>
          {likes}
        </span>
      </span>,
      <span>
        <Tooltip title="Dislike">
          <Icon
            type="dislike"
            theme={action === 'disliked' ? 'filled' : 'outlined'}
            onClick={this.dislike}
          />
        </Tooltip>
        <span style={{ paddingLeft: 8, cursor: 'auto' }}>
          {dislikes}
        </span>
      </span>,
      <span onClick={this.replyTo} >Reply to</span>,
    ]

    return (
      <div>
        <Comment
          actions={actions}
          author={<a>{post.title}</a>}
          avatar={(
            <Avatar
              src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
              alt="Han Solo"
            />
          )}
          content={(
            <Skeleton loading={isFetching} active avatar>
                <p>{post.body}</p>
              </Skeleton>
          )}
          datetime={(
            <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
              <span>{moment().fromNow()}</span>
            </Tooltip>
          )}
        />
        {commentsIsFetching &&
          <Spin />
        }
        {!commentsIsFetching && comments.map((comment) => 
          <Comment
          key={Math.random()}
          author={<a>{comment.title}</a>}
          avatar={(
            <Avatar
              src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
              alt="Han Solo"
            />
          )}
          content={(
            <p>{comment.body}</p>
          )}
          datetime={(
            <Tooltip title={moment().format('YYYY-MM-DD HH:mm:ss')}>
              <span>{moment().fromNow()}</span>
            </Tooltip>
          )}
        />
        )}
        {replyTo &&
        <div>
          <Form.Item>
            <TextArea rows={4} onChange={this.handleChange} value={body} />
          </Form.Item>
          <Form.Item>
            <Button
              htmlType="submit"
              onClick={this.handleSubmit}
              type="primary"
            >
              Add Comment
            </Button>
          </Form.Item>
        </div>
        }
      </div>
    )
  }
}