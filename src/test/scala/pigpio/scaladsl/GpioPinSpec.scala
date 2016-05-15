package pigpio.scaladsl

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestActor, TestKit}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}


class GpioPinSpec extends TestKit(ActorSystem()) with WordSpecLike with Matchers with MockFactory {

  "levels changes" should {
    "be ignored in initial state" in {
      implicit val lgpio = mock[PigpioLibrary]

      val pin = TestActorRef[GpioPin](GpioPin.props(UserGpio(0)))
      pin.receive(Levels.high)
    }

    "be set high if output" in {
      implicit val lgpio = mock[PigpioLibrary]
      (lgpio.gpioWrite _).expects(0, Levels.high.value).returns(0)

      val pin = TestActorRef[GpioPin](GpioPin.props(UserGpio(0)))
      pin.receive(OutputPin)
      pin.receive(Levels.high)
    }
  }
}
