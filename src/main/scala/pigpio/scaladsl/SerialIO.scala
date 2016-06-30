package pigpio.scaladsl

import com.sun.jna
import com.sun.jna.Memory
import com.ochafik.lang.jnaerator.runtime.NativeSize
import scala.concurrent.Future
import scala.util.Try


/**
 *
 */
trait SerialIO {
  def gpioSerialReadOpen(user_gpio: UserGpio, baud: BaudRate, data_bits: DataBits)(implicit pigpio: PigpioLibrary): Try[GpioResult]
  def gpioSerialReadInvert(user_gpio: UserGpio, invert: InvertSerial)(implicit pigpio: PigpioLibrary): Try[GpioResult]
  def gpioSerialReadClose(user_gpio: UserGpio)(implicit pigpio: PigpioLibrary): Try[GpioResult]

  // buf and bufSize should be hidden, and a data stream is made available
  def gpioSerialRead(user_gpio: UserGpio, sz: Int = 1024)(fn: String => Unit)(implicit pigpio: PigpioLibrary): Future[SerialReadResult]
}

object DefaultSerialIO extends DefaultSerialIO

trait DefaultSerialIO extends SerialIO {

  def gpioSerialReadOpen(user_gpio: UserGpio, baud: BaudRate, data_bits: DataBits)(implicit pigpio: PigpioLibrary): Try[GpioResult] =
    GpioResultOf(pigpio.gpioSerialReadOpen(user_gpio.value, baud.value, data_bits.value))

  def gpioSerialReadInvert(user_gpio: UserGpio, invert: InvertSerial)(implicit pigpio: PigpioLibrary): Try[GpioResult] =
    GpioResultOf(pigpio.gpioSerialReadInvert(user_gpio.value, invert.value))

  def gpioSerialReadClose(user_gpio: UserGpio)(implicit pigpio: PigpioLibrary): Try[GpioResult] =
    GpioResultOf(pigpio.gpioSerialReadClose(user_gpio.value))

  def gpioSerialRead(user_gpio: UserGpio, bufsz: Int = 1024)(fn: String => Unit)(implicit pigpio: PigpioLibrary): Future[SerialReadResult] = {
    require(bufsz > 0, "read size must be gt zero")
    val size = new NativeSize(bufsz)
    val buffer = new jna.Memory(size.intValue)
    Future {
      // revisit;; not sure if this native call is blocking
      pigpio.gpioSerialRead(user_gpio.value, buffer, size) match {
        case sz if sz >= 0 =>
          fn(buffer.getString(0))
          ReadOK(sz)
        case PigpioLibrary.PI_BAD_USER_GPIO => throw BadUserGpio()
        case PigpioLibrary.PI_NOT_SERIAL_GPIO => throw NotSerialGpio()
        case ec => throw UnknownFailure()
      }
    }
  }
}


sealed trait BaudRate {
  def value: Int
}
private case class DefaultBaudRate(value: Int) extends BaudRate

object BaudRate {
  val range = Range(50, 250000)

  def apply(rate: Int): BaudRate = {
    if (!range.contains(rate)) throw BadBaudRate()
    DefaultBaudRate(rate)
  }
}

object BaudRates {
  val `9600` = BaudRate(9600)
  val `115200` = BaudRate(115200)
}

sealed trait DataBits {
  def value: Int
}
private case class DefaultDataBits(value: Int) extends DataBits

object DataBits {
  val `8`: DataBits = DefaultDataBits(8)
  val `7`: DataBits = DefaultDataBits(7)
}

sealed trait StopBits {
  def value: Int
}
private case class DefaultStopBits(value: Int) extends StopBits

object StopBits {
  val `2`: StopBits = DefaultStopBits(2)
}

sealed trait InvertSerial {
  def value: Int
}
case object InvertedSerial extends InvertSerial {val value = PigpioLibrary.PI_BB_SER_INVERT}
case object NormalSerial extends InvertSerial {val value = PigpioLibrary.PI_BB_SER_NORMAL}
