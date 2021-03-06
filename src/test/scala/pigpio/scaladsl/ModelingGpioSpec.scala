package pigpio.scaladsl

import org.scalatest.{Matchers, WordSpec}

/**
 *
 */
class ModelingGpioSpec extends WordSpec with Matchers {
  "gpios" should {
    "be equal when same in" in {Gpio(0) shouldEqual Gpio(0)}
    "throw when lt zero" in {intercept[IllegalArgumentException](Gpio(-1))}
    "throw when gt max" in {intercept[IllegalArgumentException](Gpio(Gpio.userPins.max + 1))}
  }

  "gpio implicits" should {
    "convert an in-range int to gpio" in {
      import GpioImplicits._
      def fn(gpio: Gpio) = gpio
      fn(1) shouldBe Gpio(1)
    }
  }
}
