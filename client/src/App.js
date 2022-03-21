import 'bootstrap/dist/css/bootstrap.css'
import './App.css'

import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import Header from './components/Header'
import Boxes from './components/Boxes'
import Logger from './components/Logger'
import { LoggerProvider } from './components/Logger/Context'

/**
 * todo
 * - Adiciona no counter do box + 1 minutos quando renova
 */

function App() {
	return (
		<>
			<LoggerProvider>
				<ToastContainer theme='colored' />
				<div className="container">
					<div className="row">
						<div className="col-sm-12">
							<Header />
						</div>
						<div className="col-sm-8">
							<Boxes />
						</div>
						<div className="col-sm-4">
							<Logger />
						</div>
					</div>
				</div>
			</LoggerProvider>
		</>
	)
}

export default App
