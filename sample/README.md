# How to use
This is a simple gradle script to show how this plugin works and can be used.

## Before
To be able to run this script you have to execute the following command in the `gradle-arduino-plugin` folder.    
`gradlew clean build uploadArchives`

## Install Arduino IDE
```groovy
apply plugin: 'com.github.zhurlik.arduino'

dependencies {
    arduinoIde 'arduino:linux64:1.8.7@tar.xz'
}
```
Then you can use:  
`gradlew install`

After this Arduino Ide will be downloaded and extracted into `${projectDir}`