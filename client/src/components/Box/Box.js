import { useState, useEffect, useRef, useCallback } from 'react'
import moment from 'moment'
import { toast } from 'react-toastify'

import ApiService from '../../services/api'
import { useLogger } from '../Logger/Context'

import './Box.css'

const licenseTime = 60

const Box = ({ id }) => {
    const [usingLicence, setUsingLicence] = useState(false)
    const [licenseId, setLicenseId] = useState('')
    const [counter, setCounter] = useState(0)
    const licenseTimeout = useRef(null)

    const { dispatch } = useLogger()

    const logMessage = useCallback((msg) => {
        dispatch({ type: 'log', payload: msg })
    }, [dispatch])

    useEffect(() => {
        ApiService.setLogMessage(logMessage)
    }, [logMessage])

    useEffect(() => {
        if (!usingLicence) return

        if (counter === 0) {
            setUsingLicence(false)
            logMessage(`Tempo da licença ${licenseId} do dev #${id} está esgotado`)
            return
        }

        if (licenseTimeout.current)
            clearTimeout(licenseTimeout.current)

        licenseTimeout.current = setTimeout(() => setCounter(counter - 1), 1000)

        return () => {
            clearInterval(licenseTimeout.current);
        }
    }, [counter, usingLicence, id, licenseId, logMessage])

    const handleLicenceClick = (e) => {
        if (e && e.preventDefault)
            e.preventDefault();
    }

    const handleAquireClick = async (e) => {
        e.preventDefault()

        const { data, error, message } = await ApiService.aquire(id)

        if (error) {
            toast.error(error)
            return
        }

        if (message) {
            toast.warning(message)
            return
        }

        const licenseIdResponse = data.valor
        logMessage(`Licença ${licenseIdResponse} adquirida para o dev #${id}`)

        setLicenseId(licenseIdResponse)

        setCounter(licenseTime)
        setUsingLicence(true)

    }

    const handleRenewClick = async (e) => {
        e.preventDefault()

        const { error, message } = await ApiService.renew(id, licenseId)

        if (error) {
            toast.error(error)
            return
        }

        if (message) {
            toast.warning(message)
            return
        }

        if (licenseTimeout.current)
            clearTimeout(licenseTimeout.current)

        setCounter(licenseTime)
    }

    const handleReleaseClick = async (e) => {
        e.preventDefault()

        if (licenseTimeout.current)
            clearTimeout(licenseTimeout.current)

        setUsingLicence(false)

        logMessage(`Dev #${id} vai liberar a licença ${licenseId}`)
        const { error, message } = await ApiService.release(id, licenseId)

        if (error) {
            toast.error(error)
            return
        }

        if (message) {
            toast.warning(message)
            return
        }

        logMessage(`Licença ${licenseId} liberada pelo Dev #${id}`)
    }


    return (
        <div className="dev-box">
            {!usingLicence && (<div className="dev-name">Dev #{id}</div>)}

            {usingLicence && (<div className="dev-name"><a href="/" onClick={handleLicenceClick} title={`Licença: ${licenseId}`}>Dev #{id}</a></div>)}

            {!usingLicence && <button onClick={handleAquireClick} className="dev-action btn btn-primary">Adquirir</button>}

            {usingLicence && <button onClick={handleRenewClick} className="dev-action btn btn-primary">Renovar</button>}

            {usingLicence && <button onClick={handleReleaseClick} className="dev-action btn btn-info">Liberar</button>}

            {usingLicence && <div className={`time-remaining ${counter <= 10 ? 'warning' : ''}`}>Tempo: {moment('00:00', 'mm:ss').add(counter, 's').format('mm:ss')
            }</div>}
        </div>
    )
}

export default Box
