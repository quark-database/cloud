package quark.util.multithreading

import java.util.concurrent.CompletableFuture

abstract class CyclicBootableService(private val afterBooting: (() -> Unit) = {}) {
    private val bootingFuture = CompletableFuture.supplyAsync(::performBooting)

    protected abstract fun performBooting()
    protected abstract fun performCyclicAction()

    internal fun launch(): Nothing {
        bootingFuture.thenRunAsync(afterBooting)

        while(true) {
            performCyclicAction()
        }
    }
}