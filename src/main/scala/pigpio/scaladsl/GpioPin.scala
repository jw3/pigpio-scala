package pigpio.scaladsl

import akka.actor.{Props, Actor}
import pigpio.scaladsl.PigpioLibrary.{INSTANCE => pigpio}


object GpioPin {
  case class Ready()
  case class Listen()
  case class Unlisten()

  def props(gpio: UserGpio)(implicit lgpio: PigpioLibrary) = Props(new GpioPin(gpio))
}


class GpioPin(gpio: UserGpio)(implicit lgpio: PigpioLibrary) extends Actor {
  val listener = new GpioAlertFunc(self)

  def off: Receive = {
    case InputPin => context.become(input)
    case OutputPin => context.become(output)
  }

  def input: Receive = {
    pigpio.gpioSetAlertFunc(gpio.value, listener)

    {
      case ClearPin =>
        pigpio.gpioSetAlertFunc(gpio.value, GpioAlertFunc.clear)
        context.become(off)
    }
  }

  def output: Receive = {
    case ClearPin => context.become(off)

    case l: Level => DefaultDigitalIO.gpioWrite(gpio, l)
    case p: GpioPull => DefaultDigitalIO.gpioSetPullUpDown(gpio, p)
  }

  def receive: Receive = off
}
