package pigpio.scaladsl

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}
import rxgpio.pigpio.PigpioLibrary


class GpioPinSpec extends TestKit(ActorSystem()) with WordSpecLike with Matchers with MockFactory {

  "initial state" should {
    "ignore these events" in {
      implicit val lgpio = mock[PigpioLibrary]

      val pin = TestActorRef[GpioPin](GpioPin.props(UserGpio(0)))
      pin.receive(Levels.high)
      pin.receive(PullUp)
    }
  }

  "output state" should {
    implicit val lgpio = mock[PigpioLibrary]
    val pin = TestActorRef[GpioPin](GpioPin.props(UserGpio(0)))
    pin.receive(OutputPin)

    "set pin high" in {
      (lgpio.gpioWrite _).expects(0, Levels.high.value).returns(0)
      pin.receive(Levels.high)
    }

    "set pin low" in {
      (lgpio.gpioWrite _).expects(0, Levels.low.value).returns(0)
      pin.receive(Levels.low)
    }

    "pull pin high" in {
      (lgpio.gpioSetPullUpDown _).expects(0, PullUp.value).returns(0)
      pin.receive(PullUp)
    }

    "pull pin low" in {
      (lgpio.gpioSetPullUpDown _).expects(0, PullDown.value).returns(0)
      pin.receive(PullDown)
    }

    "handle failures" when {
      "unknown thing happened on write" in {
        (lgpio.gpioWrite _).expects(0, PullDown.value).returns(9999)
        intercept[UnknownFailure](pin.receive(Levels.high))
      }

      "unknown thing happened on pull" in {
        (lgpio.gpioSetPullUpDown _).expects(0, PullDown.value).returns(9999)
        intercept[UnknownFailure](pin.receive(PullDown))
      }
    }
  }
}
