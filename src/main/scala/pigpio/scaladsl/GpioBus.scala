package pigpio.scaladsl

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import pigpio.scaladsl.GpioPin.{Listen, Unlisten}

import scala.collection.immutable.HashSet


object GpioBus {
  def apply()(implicit sys: ActorSystem) = sys.actorOf(props())
  def props() = Props(new GpioBus())
}


class GpioBus() extends Actor with ActorLogging {
  var listeners = HashSet[ActorRef]()

  def receive: Receive = {
    case Listen() =>
      listeners += sender()

    case Unlisten() =>
      listeners -= sender()

    case a: GpioAlert =>
      listeners.foreach(_ ! a)
  }
}
