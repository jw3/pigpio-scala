package pigpio.scaladsl

import pigpio.scaladsl.{PigpioLibrary => pigpio}

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

sealed trait GpioResult
sealed trait GpioFailure extends RuntimeException with GpioResult

sealed trait GpioOk extends GpioResult
case class OK() extends GpioOk

case class UnknownFailure() extends GpioFailure

/**
 * [[Gpio]] failures
 */
sealed trait BadGpio extends GpioFailure
case class BadUserGpio() extends BadGpio
case class BadExGpio() extends BadGpio

case class BadMode() extends GpioFailure
case class BadPull() extends GpioFailure
case class BadLevel() extends GpioFailure

object GpioResult {
  def apply(code: Int): GpioResult = code match {
    case 0 => OK()
    case pigpio.PI_BAD_USER_GPIO => throw BadUserGpio()
    case pigpio.PI_BAD_GPIO => throw BadExGpio()
    case pigpio.PI_BAD_MODE => throw BadMode()
    case pigpio.PI_BAD_PUD => throw BadPull()
    case _ => throw UnknownFailure()
  }
}

object GpioResultOf {
  def apply(f: => Int): Try[GpioResult] = {
    try Success(GpioResult(f))
    catch {
      case NonFatal(e) => Failure(e)
    }
  }
}
