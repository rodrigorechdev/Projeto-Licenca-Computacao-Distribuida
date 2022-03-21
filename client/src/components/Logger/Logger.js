import './Logger.css'

import { LoggerConsumer } from './Context'

const Logger = () => {
    return (
        <LoggerConsumer>
            {({ state }) => (
                <div className='logger-container'>
                    {state.logs.map((log, i) => (
                        <div className='message' key={i}>
                            {log}
                        </div>
                    ))}
                </div>
            )}
        </LoggerConsumer>
    )
}

export default Logger
