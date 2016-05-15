package pigpio.scaladsl

import akka.actor.{Actor, ActorLogging, Props}
import pigpio.scaladsl.GpioPin.ListeningMessage
import pigpio.scaladsl.PigpioLibrary.{INSTANCE => pigpio}

import scala.util.{Success, Failure}


object GpioPin {
  sealed trait ListeningMessage
  case class Listen() extends ListeningMessage
  case class Unlisten() extends ListeningMessage

  def props(gpio: UserGpio)(implicit lgpio: PigpioLibrary) = Props(new GpioPin(gpio))
}


class GpioPin(gpio: UserGpio)(implicit lgpio: PigpioLibrary) extends Actor with ActorLogging {
  log.debug("created actor for gpio[{}]", gpio.value.toString)
  val alertbus = context.actorOf(GpioBus.props())
  val listener = new GpioAlertFunc(alertbus)

  def off: Receive = {
    log.debug("gpio[{}] in off state")

    {
      case InputPin => context.become(input)
      case OutputPin => context.become(output)
      case m: ListeningMessage => alertbus forward m
    }
  }

  def input: Receive = {
    log.debug("gpio[{}] in input state")
    pigpio.gpioSetAlertFunc(gpio.value, listener)

    {
      case ClearPin =>
        pigpio.gpioSetAlertFunc(gpio.value, GpioAlertFunc.clear)
        context.become(off)
    }
  }

  def output: Receive = {
    log.debug("gpio[{}] in output state")

    {
      case ClearPin => context.become(off)

      case l: Level =>
        val src = sender()
        DefaultDigitalIO.gpioWrite(gpio, l) match {
          case Success(r) => src ! r
          case Failure(e) => throw e
        }

      case p: GpioPull =>
        val src = sender()
        DefaultDigitalIO.gpioSetPullUpDown(gpio, p) match {
          case Success(r) => src ! r
          case Failure(e) => throw e
        }
    }
  }

  def receive: Receive = off
}
