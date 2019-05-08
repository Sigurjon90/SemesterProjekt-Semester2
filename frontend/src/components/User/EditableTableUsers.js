import { Table, Input, InputNumber, Popconfirm, Form, Tag, Select, Divider, Spin } from 'antd'
import React, { Component } from 'react'
import { inject, observer } from 'mobx-react'
import { toJS } from 'mobx'

import concat from '../../utils/concat'

const Option = Select.Option

class EditableCell extends React.Component {
    getInput = () => {
        if (this.props.inputType === 'number') {
            return <InputNumber />
        }
        if (this.props.inputType === 'option') {
            return (<Select disabled
                style={{ width: '100%' }}
            >
                <Option key='admin'>Administrator</Option>
                <Option key='caseworker'>Sagsbehandler</Option>
                <Option key='caregiver'>Pædagog</Option>
            </Select>)
        }
        return <Input />
    }

    render() {
        const {
            editing,
            dataIndex,
            title,
            inputType,
            record,
            index,
            ...restProps
        } = this.props
        return (
            <EditableContext.Consumer>
                {(form) => {
                    const { getFieldDecorator } = form
                    return (
                        <td {...restProps}>
                            {editing ? (
                                <FormItem style={{ margin: 0 }}>
                                    {getFieldDecorator(dataIndex, {
                                        rules: [{
                                            required: true,
                                            message: `Please Input ${title}!`,
                                        }],
                                        initialValue: record[dataIndex],
                                    })(this.getInput())}
                                </FormItem>
                            ) : restProps.children}
                        </td>
                    )
                }}
            </EditableContext.Consumer>
        )
    }
}

const FormItem = Form.Item
const EditableContext = React.createContext()

@inject('usersStore')
@observer
class EditableTableUsers extends React.Component {
    constructor(props) {
        super(props)
        this.getUsers = () => this.props.usersStore.fetchUsers()
        this.deleteUser = (id) => this.props.usersStore.archiveUser(id)
        this.updateUser = (updatedUser) => this.props.usersStore.putUserChanges(updatedUser)
        this.state = { editingKey: '' }

        this.columns = [
            {
                title: 'Brugernavn',
                dataIndex: 'username',
                width: '25%',
                editable: true,
            },
            {
                title: 'Status',
                dataIndex: 'role',
                width: '25%',
                editable: true,
            },
            {
                title: 'E-mail',
                dataIndex: 'email',
                width: '25%',
                editable: true,
            },
            {
                title: 'operation',
                dataIndex: 'operation',
                render: (text, record) => {
                    const { editingKey } = this.state
                    const editable = this.isEditing(record)
                    return (
                        <div>
                            {editable ? (
                                <span>
                                    <EditableContext.Consumer>
                                        {form => (
                                            <a
                                                href='javascript:'
                                                onClick={() => this.save(form, record.id)}
                                                style={{ marginRight: 8 }}
                                            >
                                                Save
                                            </a>
                                        )}
                                    </EditableContext.Consumer>
                                    <Popconfirm
                                        title='Sure to cancel?'
                                        onConfirm={() => this.cancel(record.id)}
                                    >
                                        <a>Cancel</a>
                                    </Popconfirm>
                                </span>
                            ) : (
                                    <a disabled={editingKey !== ''} onClick={() => this.edit(record.id)}>Edit</a>
                                )}
                            <Divider type='vertical' />
                            {!editable ? (<Popconfirm title='Sure to delete?' onConfirm={() => this.handleDelete(record.id)}>
                                <a href='javascript:'>Delete</a>
                            </Popconfirm>
                            ) : null}
                        </div>
                    )
                },
            },
        ]
    }

    componentWillMount() {
        this.getUsers()
    }

    handleDelete = (id) => {
        this.deleteUser(id)
    }

    isEditing = record => record.id === this.state.editingKey

    cancel = () => {
        this.setState({ editingKey: '' })
    }

    save(form, key) {
        const { users } = this.props.usersStore
        form.validateFields((error, row) => {
            if (error) {
                return
            }
            const newData = [...users]
            const index = newData.findIndex(item => key === item.id)
            if (index > -1) {
                const item = toJS(newData[index])
                const updatedUser = {
                    ...item,
                    ...row
                }
                this.updateUser(updatedUser)
                this.setState({ editingKey: '' })
            } else {
                newData.push(row)
                this.setState({ editingKey: '' })
            }
        })
    }

    edit(key) {
        this.setState({ editingKey: key })
    }

    render() {
        const { usersStore } = this.props
        const { users, isFetching } = usersStore

        const components = {
            body: {
                cell: EditableCell,
            },
        }

        const columns = this.columns.map((col) => {
            if (!col.editable) {
                return col
            }
            return {
                ...col,
                onCell: record => ({
                    record,
                    inputType: col.dataIndex === 'role' ? 'option' : 'text',
                    dataIndex: col.dataIndex,
                    title: col.title,
                    editing: this.isEditing(record),
                }),
            }
        })

        return (
            <div>
                {isFetching && <Spin size='large' />}
                {!isFetching &&
                    <EditableContext.Provider value={this.props.form}>
                        <Table
                            components={components}
                            bordered
                            dataSource={users}
                            columns={columns}
                            rowClassName='editable-row'
                            pagination={{
                                onChange: this.cancel,
                            }}
                        />
                    </EditableContext.Provider>
                }
            </div>
        )
    }
}

const EditableFormTable = Form.create()(EditableTableUsers)
export default EditableFormTable
