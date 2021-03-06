Scala [pigpio](https://github.com/joan2937/pigpio)
---
[![Build Status](https://travis-ci.org/jw3/pigpio-scala.svg?branch=master)](https://travis-ci.org/jw3/pigpio-scala)
[![Dependencies](https://app.updateimpact.com/badge/701268856357916672/pigpio-scala.svg?config=compile)](https://app.updateimpact.com/latest/701268856357916672/pigpio-scala)

Scala API to the great pigpio library
 
Using JNA bindings from [pigpio-jna](https://github.com/jw3/pigpio-jna)

See some examples https://github.com/jw3/pigpio-scala-examples

##### Tests

Tests that require the pigpio library are tagged and can be excluded with

`sbt "testOnly pigpio.scaladsl.* -- -l pigpio.scaladsl.PiIntegrationTest"`


##### Ticks

Microsecond timestamps comes across as uint32 in

* `gpioAlertFunc_t`
* `gpioAlertFuncEx_t`
* `gpioISRFunc_t`
* `gpioISRFuncEx_t`

Using `Integer.parseUnsignedInt` to convert to a Long


##### Artifacts

Add the bintray resolver to you sbt project

```resolvers += "jw3 at bintray" at "https://dl.bintray.com/jw3/maven"```

The following artifacts can be specified

```"com.github.jw3" %% "pigpio-scala" % "0.1"```

### Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/jw3/pigpio-scala/issues).

### License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<https://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
