package pigpio.scaladsl

import akka.actor.ActorSystem
import akka.testkit.{TestActorRef, TestKit, TestProbe}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{Matchers, WordSpecLike}
import pigpio.scaladsl.GpioPin.{Listen, Unlisten}

class GpioBusSpec extends TestKit(ActorSystem()) with WordSpecLike with Matchers with MockFactory {
  val a1 = TestProbe()
  val a2 = TestProbe()

  "bus" should {
    "manage subscribers" when {
      val bus = TestActorRef[GpioBus]

      "adding only" in {
        bus.underlyingActor.listeners shouldBe empty
        bus.receive(Listen(), a1.ref)
        bus.underlyingActor.listeners.head shouldBe a1.ref
      }

      "removing only" in {
        bus.underlyingActor.listeners.head shouldBe a1.ref
        bus.receive(Unlisten(), a1.ref)
        bus.underlyingActor.listeners shouldBe empty
      }

      "adding multiple" in {
        bus.underlyingActor.listeners shouldBe empty
        bus.receive(Listen(), a1.ref)
        bus.receive(Listen(), a2.ref)
        bus.underlyingActor.listeners should contain allOf(a1.ref, a2.ref)
      }

      "removing multiple" in {
        bus.underlyingActor.listeners should contain allOf(a1.ref, a2.ref)
        bus.receive(Unlisten(), a1.ref)
        bus.receive(Unlisten(), a2.ref)
        bus.underlyingActor.listeners shouldBe empty
      }
    }

    "notify single subscriber" in {
      val bus = TestActorRef[GpioBus]
      bus.receive(Listen(), a1.ref)

      val alert = GpioAlert(0, 0, 0)
      bus ! alert
      a1.expectMsg(alert)
    }

    "notify multiple subscribers" in {
      val bus = TestActorRef[GpioBus]
      bus.receive(Listen(), a1.ref)
      bus.receive(Listen(), a2.ref)

      val alert = GpioAlert(0, 0, 0)
      bus ! alert
      a1.expectMsg(alert)
      a2.expectMsg(alert)
    }
  }
}
