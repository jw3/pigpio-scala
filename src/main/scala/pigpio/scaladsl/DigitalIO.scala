package pigpio.scaladsl


import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

/**
 *
 */
trait DigitalIO {
  def gpioGetMode(gpio: Gpio)(implicit pigpio: PigpioLibrary): Try[PinMode]
  def gpioSetMode(gpio: Gpio, mode: PinMode)(implicit pigpio: PigpioLibrary): Try[GpioResult]

  def gpioRead(gpio: Gpio)(implicit pigpio: PigpioLibrary): Try[Level]
  def gpioWrite(gpio: Gpio, level: Level)(implicit pigpio: PigpioLibrary): Try[GpioResult]

  def gpioSetPullUpDown(gpio: Gpio, pud: GpioPull)(implicit pigpio: PigpioLibrary): Try[GpioResult]
}

object DefaultDigitalIO extends DefaultDigitalIO

trait DefaultDigitalIO extends DigitalIO {
  def gpioGetMode(gpio: Gpio)(implicit pigpio: PigpioLibrary): Try[PinMode] = {
    try Success(PinMode(pigpio.gpioGetMode(gpio.value)))
    catch {
      case NonFatal(e) => Failure(e)
    }
  }
  def gpioSetMode(gpio: Gpio, mode: PinMode)(implicit pigpio: PigpioLibrary): Try[GpioResult] =
    GpioResultOf(pigpio.gpioSetMode(gpio.value, mode.value))

  def gpioSetPullUpDown(gpio: Gpio, pud: GpioPull)(implicit pigpio: PigpioLibrary): Try[GpioResult] =
    GpioResultOf(pigpio.gpioSetPullUpDown(gpio.value, pud.value))

  def gpioRead(gpio: Gpio)(implicit pigpio: PigpioLibrary): Try[Level] = {
    try Success(Level(pigpio.gpioRead(gpio.value)))
    catch {
      case NonFatal(e) => Failure(e)
    }
  }
  def gpioWrite(gpio: Gpio, level: Level)(implicit pigpio: PigpioLibrary): Try[GpioResult] =
    GpioResultOf(pigpio.gpioWrite(gpio.value, level.value))
}
