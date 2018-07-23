package pigpio.scaladsl

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

import scala.util.{Failure, Success}


object GpioPin {
  sealed trait ListeningMessage
  case class Listen() extends ListeningMessage
  case class Unlisten() extends ListeningMessage

  def apply(gpio: UserGpio)(implicit  sys: ActorSystem) = sys.actorOf(props(gpio))
  def props(gpio: UserGpio)  = Props(new GpioPin(gpio))
}


class GpioPin(gpio: UserGpio)  extends Actor with ActorLogging {
  log.debug("created [{}] actor", gpio)
  val alertbus = context.actorOf(GpioBus.props())
  val listener = new GpioAlertFunc(alertbus)

  def off: Receive = {
    log.debug("[{}] in off state", gpio)

    {
      case InputPin => context.become(input)
      case OutputPin => context.become(output)
      case QueryPinMode => sender() ! ClearPin
      case m: GpioPin.ListeningMessage => alertbus forward m
    }
  }

  def input: Receive = {
    log.debug("[{}] in input state", gpio)
    pigpio.gpioSetAlertFunc(gpio.value, listener)

    {
      case QueryPinMode => sender() ! InputPin
      case ClearPin =>
        pigpio.gpioSetAlertFunc(gpio.value, null)
        context.become(off)
    }
  }

  def output: Receive = {
    log.debug("[{}] in output state", gpio)

    {
      case QueryPinMode => sender() ! OutputPin
      case ClearPin => context.become(off)

      case l: Level =>
        log.debug("[{}] writing [{}]", gpio, l)
        val src = sender()
        DefaultDigitalIO.gpioWrite(gpio, l) match {
          case Success(r) => src ! r
          case Failure(e) => throw e
        }

      case p: GpioPull =>
        log.debug("[{}] pulling [{}]", gpio, p)
        val src = sender()
        DefaultDigitalIO.gpioSetPullUpDown(gpio, p) match {
          case Success(r) => src ! r
          case Failure(e) => throw e
        }
    }
  }

  def receive: Receive = off
}
