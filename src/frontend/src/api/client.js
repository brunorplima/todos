
const getOptions = (method, body) => ({
    method,
    body,
    headers: {
        'Content-Type': 'application/json'
    }
})

export default {
    get: async (url) => {
        const options = getOptions('GET')
        const res = await fetch(url, options)
        return await res.json()
    },
    post: async (url, body) => {
        const options = getOptions('POST', JSON.stringify(body))
        const res = await fetch(url, options)
        return await res.json()
    },
    put: async (url, body) => {
        const options = body ? getOptions('PUT', JSON.stringify(body)) : getOptions('PUT')
        const res = await fetch(url, options)
        return await res.json()
    },
    delete: async (url) => {
        const options = getOptions('DELETE')
        await fetch(url, options)
    }
}
