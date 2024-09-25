package quickstart

import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.http4k.client.ApacheClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.io.StringWriter

fun main() {
    var block: HTML.() -> Unit = {
        body {
            div {
                p {
                    +"Here is "
                    a("https://kotlinlang.org") { +"Hello world" }
                }
            }
        }
    }
    var stringWriter = StringWriter()
    stringWriter.appendHTML().html(block = block)


    val app = { request: Request -> Response(OK).body(stringWriter.toString()) }

    val server = app.asServer(Undertow(9000)).start()

    val client = ApacheClient()

    val request = Request(Method.GET, "http://localhost:9000").query("name", "John Doe")

    println(client(request))

    server.stop()
}