import { createContext, useContext, useReducer } from 'react';

const LoggerContext = createContext()

function loggerReducer(state, action) {
    switch (action.type) {
        case 'log': {
            return { logs: [action.payload, ...state.logs] }
        }
        default: {
            throw new Error(`Unhandled action type: ${action.type}`)
        }
    }
}

function LoggerConsumer({ children }) {
    return (
        <LoggerContext.Consumer>
            {context => {
                if (context === undefined) {
                    throw new Error('LoggerConsumer must be used within a LoggerProvider')
                }
                return children(context)
            }}
        </LoggerContext.Consumer>
    )
}

function LoggerProvider({ children }) {
    const [state, dispatch] = useReducer(loggerReducer, { logs: [] })
    const value = { state, dispatch }

    return <LoggerContext.Provider value={value}>{children}</LoggerContext.Provider>
}

function useLogger() {
    const context = useContext(LoggerContext)

    if (context === undefined) {
        throw new Error('useLogger must be used within a LoggerProvider')
    }
    return context
}

export { LoggerProvider, LoggerConsumer, useLogger }
