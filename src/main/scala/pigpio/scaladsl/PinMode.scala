package pigpio.scaladsl

import pigpio.scaladsl.{PigpioLibrary => lib}

sealed trait PinMode {
  def value: Int
}

case object ClearPin extends PinMode {val value = lib.PI_CLEAR}
case object InputPin extends PinMode {val value = lib.PI_INPUT}
case object OutputPin extends PinMode {val value = lib.PI_OUTPUT}
case object QueryPinMode

object PinMode {
  def apply(value: Int) = value match {
    case lib.PI_INPUT => InputPin
    case lib.PI_OUTPUT => OutputPin
    case _ => throw BadMode()
  }
}

object PinModes {
  val input = InputPin
  val output = OutputPin
}
