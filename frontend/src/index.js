import('./styles/main.scss')
import("./utils/axiosConfig.js")
import React from 'react'
import { render } from 'react-dom'
import { Router } from 'react-router-dom'
import { Provider } from 'mobx-react'
import { AppContainer } from 'react-hot-loader'

import { RouterStore, syncHistoryWithStore } from 'mobx-react-router'
import { createBrowserHistory } from 'history'

import App from './components/App'
import stores from './stores/'


const renderApp = Component => {
	const browserHistory = createBrowserHistory()
	const routeStore = new RouterStore()
	const history = syncHistoryWithStore(browserHistory, routeStore)

	render(
		<AppContainer>
			<Router key={Math.random()} history={history}>
				<Provider {...stores} routing={routeStore} >
					<App />
				</Provider>
			</Router>
		</AppContainer>,
		document.getElementById('root')
	)
}

renderApp(App)

if (module.hot) {
	module.hot.accept(() => renderApp(App))
}
