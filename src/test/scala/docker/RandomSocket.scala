package docker

import java.net.{InetSocketAddress, Socket}

object RandomSocket {
  private[this] def localSocketOnPort(port: Int) =
    new InetSocketAddress(port)

  private[this] val ephemeralSocketAddress = localSocketOnPort(0)

  def apply(): InetSocketAddress = nextAddress()

  def nextAddress(): InetSocketAddress =
    localSocketOnPort(nextPort())

  def nextPort(): Int = {
    val s = new Socket
    s.setReuseAddress(true)
    try {
      s.bind(ephemeralSocketAddress)
      s.getLocalPort
    } catch {
      case e: Throwable =>
        throw new Exception("Couldn't find an open port: %s".format(e.getMessage))
    } finally {
      s.close()
    }
  }
}
