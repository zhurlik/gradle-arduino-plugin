# gradle-arduino-plugin
This plugin helps to install **[Arduino IDE](https://www.arduino.cc/en/Main/Software)**  
The **Arduino IDE** allows editing, compiling and uploading sketches (programs) for Arduino (and compatible) microcontroller boards

[![Build Status](https://travis-ci.org/zhurlik/gradle-arduino-plugin.svg?branch=master)](https://travis-ci.org/zhurlik/gradle-arduino-plugin)
[![Coverage Status](https://coveralls.io/repos/github/zhurlik/gradle-arduino-plugin/badge.svg?branch=master)](https://coveralls.io/github/zhurlik/gradle-arduino-plugin?branch=master)

# Gradle Tasks
There are a few gradle tasks using which you can specify options for executing one of the provided tools in the **Arduino IDE**
## arduino
Normally, running the arduino command starts the IDE, optionally loading any .ino files specified on the commandline
## arduino-builder
- **dump-prefs** - dumps build properties used when compiling
- **compile** - compiles the given sketch
## avrdude
AVR Downloader/UploaDEr is a utility to download/upload/manipulate the ROM and EEPROM contents of AVR microcontrollers using the in-system programming technique (ISP).

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
