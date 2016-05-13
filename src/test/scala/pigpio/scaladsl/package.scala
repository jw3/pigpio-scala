package pigpio

import org.scalatest.Tag


package object scaladsl {
  /**
   * a pitest will only be run once deployed to a pi
   */
  val pitest = Tag("pigpio.scaladsl.PiIntegrationTest")
}
