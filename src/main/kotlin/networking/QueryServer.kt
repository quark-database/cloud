package quark.networking

import quark.util.logging.Logger
import quark.util.multithreading.CyclicBootableService
import java.net.ServerSocket
import java.nio.charset.Charset

class QueryServer(afterBooting: (() -> Unit)) : CyclicBootableService(afterBooting) {
    private val logger = Logger("Query server")
    private lateinit var server: ServerSocket

    override fun performBooting() {
        server = ServerSocket(10000)
    }

    override fun performCyclicAction() {
        val socket = server.accept()
        val stream = socket.getInputStream()
        val string = stream.readAllBytes().toString(Charset.defaultCharset())

        logger.information("The message from a socket: $string")
    }
}