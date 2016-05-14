package pigpio.scaladsl

import akka.actor.{Actor, Props}
import pigpio.scaladsl.{PigpioLibrary => lib}


object GpioBus {
  case class ListenOn(gpio: UserGpio)

  def props()(implicit lgpio: PigpioLibrary) = Props(new GpioBus())
}


class GpioBus()(implicit lgpio: PigpioLibrary) extends Actor {
  import GpioBus._

  val rxpins = (0 to lib.PI_MAX_USER_GPIO)
               .map(UserGpio)
               .map(gpio => gpio -> context.actorOf(GpioPin.props(gpio)))
               .toMap

  def receive: Receive = {
    case ListenOn(gpio) =>
  }
}
