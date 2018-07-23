package pigpio.scaladsl

sealed trait PinMode {
  def value: Int
}

case object ClearPin extends PinMode {val value = pigpio.PI_CLEAR}
case object InputPin extends PinMode {val value = pigpio.PI_INPUT}
case object OutputPin extends PinMode {val value = pigpio.PI_OUTPUT}
case object QueryPinMode

object PinMode {
  def apply(value: Int) = value match {
    case pigpio.PI_INPUT => InputPin
    case pigpio.PI_OUTPUT => OutputPin
    case _ => throw BadMode()
  }
}

object PinModes {
  val input = InputPin
  val output = OutputPin
}
