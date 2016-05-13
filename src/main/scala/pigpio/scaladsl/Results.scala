package pigpio.scaladsl


sealed trait GpioResult
sealed trait GpioFailure extends RuntimeException with GpioResult
