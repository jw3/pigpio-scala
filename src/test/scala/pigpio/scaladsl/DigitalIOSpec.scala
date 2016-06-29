package pigpio.scaladsl

import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpec}

import scala.util.{Failure, Success}

class DigitalIOSpec extends WordSpec with Matchers with MockFactory {

  val te = new RuntimeException("failure!")

  "default implementation" should {
    implicit val lgpio = mock[PigpioLibrary]
    val pin = UserGpio(4)
    val mode = PinModes.input
    val pud = PullUp
    val level = Levels.low

    "call gpioGetMode" can {
      "complete successfully" in {
        (lgpio.gpioGetMode _).expects(pin.value).returns(mode.value)
        DefaultDigitalIO.gpioGetMode(pin) shouldBe Success(mode)
      }

      "complete unsuccessfully" in {
        (lgpio.gpioGetMode _).expects(pin.value).throws(te)
        DefaultDigitalIO.gpioGetMode(pin) shouldBe Failure(te)
      }
    }

    "call gpioSetMode" can {
      "complete successfully" in {
        (lgpio.gpioSetMode _).expects(pin.value, mode.value).returns(0)
        DefaultDigitalIO.gpioSetMode(pin, mode) shouldBe Success(OK())
      }

      "complete unsuccessfully" in {
        (lgpio.gpioSetMode _).expects(pin.value, mode.value).throws(te)
        DefaultDigitalIO.gpioSetMode(pin, mode) shouldBe Failure(te)
      }
    }

    "call gpioSetPullUpDown" can {
      "complete successfully" in {
        (lgpio.gpioSetPullUpDown _).expects(pin.value, pud.value).returns(0)
        DefaultDigitalIO.gpioSetPullUpDown(pin, pud) shouldBe Success(OK())
      }

      "complete unsuccessfully" in {
        (lgpio.gpioSetPullUpDown _).expects(pin.value, pud.value).throws(te)
        DefaultDigitalIO.gpioSetPullUpDown(pin, pud) shouldBe Failure(te)
      }
    }

    "call gpioRead" can {
      "complete successfully" in {
        (lgpio.gpioRead _).expects(pin.value).returns(level.value)
        DefaultDigitalIO.gpioRead(pin) shouldBe Success(level)
      }

      "complete unsuccessfully" in {
        (lgpio.gpioRead _).expects(pin.value).throws(te)
        DefaultDigitalIO.gpioRead(pin) shouldBe Failure(te)
      }
    }

    "call gpioWrite" can {
      "complete successfully" in {
        (lgpio.gpioWrite _).expects(pin.value, level.value).returns(0)
        DefaultDigitalIO.gpioWrite(pin, level) shouldBe Success(OK())
      }

      "complete unsuccessfully" in {
        (lgpio.gpioWrite _).expects(pin.value, level.value).throws(te)
        DefaultDigitalIO.gpioWrite(pin, level) shouldBe Failure(te)
      }
    }
  }
}
