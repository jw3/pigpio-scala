package pigpio.scaladsl

import org.scalatest.{WordSpec, Matchers}

class LazyInitSpec extends WordSpec with Matchers {
  "library" should {
    "load lazily" taggedAs pitest in {
      PigpioLibrary.PI_OFF shouldBe 0
    }
    "load explicitly" taggedAs pitest in {
      PigpioLibrary.INSTANCE.gpioWaveTxStop
    }
  }
}
