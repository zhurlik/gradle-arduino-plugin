# gradle-arduino-plugin
This plugin helps to install **[Arduino IDE](https://www.arduino.cc/en/Main/Software)** and build your sketches

[![Build Status](https://travis-ci.org/zhurlik/gradle-arduino-plugin.svg?branch=master)](https://travis-ci.org/zhurlik/gradle-arduino-plugin)
[![Coverage Status](https://coveralls.io/repos/github/zhurlik/gradle-arduino-plugin/badge.svg?branch=master)](https://coveralls.io/github/zhurlik/gradle-arduino-plugin?branch=master)

# Gradle Tasks
This plugin provides:
1. **dump-prefs**
2. **compile**
3. **avrdude**

# TODO
The following tasks will be implemented:
* Download and Install **[Arduino IDE](https://www.arduino.cc/en/Main/Software)**
* Build Arduino sketches
* Load to device
* Burning the Bootloader (via **avrdude**)
* http://packs.download.atmel.com/
* http://distribute.atmel.no/tools/opensource/Atmel-AVR-GNU-Toolchain/3.5.4/
* https://www.microchip.com/mplab/avr-support/avr-and-arm-toolchains-c-compilers


# How to use
* See [a simple project](sample)
* See [smart-home](https://github.com/zhurlik/smart-home/blob/master/arduino/studio/build.gradle)
