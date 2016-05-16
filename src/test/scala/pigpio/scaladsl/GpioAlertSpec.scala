package pigpio.scaladsl

import org.scalatest.{Matchers, WordSpec}


class GpioAlertSpec extends WordSpec with Matchers {
  "tick convert" should {
    "convert 1" in {
      GpioAlert(0, 0, 1).tick shouldBe 1L
    }
    "convert Int.MaxValue" in {
      GpioAlert(0, 0, Int.MaxValue).tick shouldBe Int.MaxValue.toLong
    }
    "convert unsigned Int MaxValue" in {
      GpioAlert(0, 0, Integer.parseUnsignedInt("4294967295")).tick shouldBe 4294967295L
    }
  }
}
