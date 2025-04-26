

import {create} from "bare-hyper-protocol-daemon"

console.log('Hello Android!', Bare.argv)

const [storage] = Bare.argv

const { close, port } = await create({
    storage
})

console.log(`Listening on http://localhost:${port}`)
