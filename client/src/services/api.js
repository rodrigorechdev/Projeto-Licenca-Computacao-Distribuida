const apiurl = {
    dev: 'http://localhost:3005',
    production: 'https://api-licenca-uffs.herokuapp.com'
}

const API_URL = apiurl.production

const URL_PATHS = {
    AQUIRE: '/licenca',
    RENEW: '/licenca/renovar',
    RELEASE: '/licenca/devolver'
}

class ApiServicer {
    constructor(){
        this.logMessage = () => {}
    }

    setLogMessage(fn) {
        this.logMessage = fn
    }
    
    generateURL(path, params) {
        params = typeof params == 'object' ? params : {}
        const url = new URL(API_URL)

        Object.keys(params).forEach(key => url.searchParams.append(key, params[key]))

        url.pathname += `${path.startsWith('/') ? path.substr(1) : path}`

        return url
    }

    async _executeRequest(path, params, method) {
        const url = this.generateURL(path, params)
        this.logMessage(`${method}: ${url}`)

        try {
            const response = await fetch(url, {
                method: method
            })

            if (response.status == 204) {
                this.logMessage(`RESPONSE: null`)
                return {
                    data: {},
                    _response: {}
                }
            }

            const json = (await response.json())

            if (response.status == 412) {
                this.logMessage(`RESPONSE: ${JSON.stringify(json, null, 2)}`)
                return {
                    message: json.message
                }
            }

            this.logMessage(`RESPONSE: ${JSON.stringify(json, null, 2)}`)
            return {
                data: json
            }
        } catch (error) {
            console.error(error);
            this.logMessage(`UNEXPECTED ERROR: ${JSON.stringify(error, null, 2)}`)
            return {
                error: 'Falha ao verificar retorno do web service',
            }
        }
    }

    _httpGet(path, params) {
        return this._executeRequest(path, params, 'GET')
    }

    _httpPatch(path, params) {
        return this._executeRequest(path, params, 'PATCH')
    }

    _httpDelete(path, params) {
        return this._executeRequest(path, params, 'DELETE')
    }

    async aquire(userId) {
        const data = await this._httpGet(URL_PATHS.AQUIRE, {
            usuarioId: userId
        })

        return data
    }

    async renew(userId, licenseId) {
        const data = await this._httpPatch(URL_PATHS.RENEW, {
            usuarioId: userId,
            licencaCodigo: licenseId
        })

        return data
    }

    async release(userId, licenseId) {
        const data = await this._httpDelete(URL_PATHS.RELEASE, {
            usuarioId: userId,
            licencaCodigo: licenseId
        })

        return data
    }
}

export default new ApiServicer;

export { URL_PATHS }