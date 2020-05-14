//package pigpio.scaladsl
//
//import org.scalatest.{WordSpec, Matchers}
//
//class LazyInitSpec extends WordSpec with Matchers {
//  "library" should {
//    "load lazily" in {
//      PigpioLibrary.PI_OFF shouldBe 0
//    }
//    "load explicitly" in {
//      intercept[UnsatisfiedLinkError] {
//        PigpioLibrary.INSTANCE.gpioWaveTxStop
//      }
//    }
//  }
//}
