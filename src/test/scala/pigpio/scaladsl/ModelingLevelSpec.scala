package pigpio.scaladsl

import org.scalatest.{Matchers, WordSpec}

/**
 *
 */
class ModelingLevelSpec extends WordSpec with Matchers {
  "digital value" should {
    "be high when true" in {Level(true) shouldBe High}
    "be low when false" in {Level(false) shouldBe Low}
    "be high when 1" in {Level(1) shouldBe High}
    "be low when 0" in {Level(0) shouldBe Low}
    "throw when not a 1 or 0" in {
      intercept[BadLevel] {Level(2)}
      intercept[BadLevel] {Level(-1)}
      intercept[BadLevel] {Level(999)}
      intercept[BadLevel] {Level(-999)}
    }
  }
}
