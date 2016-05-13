package pigpio.scaladsl

object Ticks {
  private val signbit = 0x80000000L

  /**
   * convert an unsigned int to a signed long
   */
  def asUint(utick: Int): Long = {
    val signed = (signbit & utick) != 0
    val longval = if (signed) utick & 0x7FFFFFFF else utick
    if (signed) longval | signbit else longval
  }
}
