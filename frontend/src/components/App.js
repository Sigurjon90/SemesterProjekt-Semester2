import React, { Component } from 'react';
import { Route, Link } from 'react-router-dom';
import { inject, observer } from 'mobx-react';
import LazyRoute from 'lazy-route';
import DevTools from 'mobx-react-devtools';

@inject('routing')
@observer
export default class App extends Component {
	render() {
		return (
			<div className='wrapper'>
				<Route
					exact
					path='/'
					render={props => (
						<LazyRoute {...props} component={import('./TodoList')} />
					)}
				/>
				<Route
					exact
					path='/posts'
					render={props => (
						<LazyRoute {...props} component={import('./PostsList')} />
					)}
				/>
				<Route
					exact
					path='/posts/:id'
					render={props => (
						<LazyRoute {...props} component={import('./Post')} />
					)}
				/>

				<DevTools />
			</div>
		)
	}
}