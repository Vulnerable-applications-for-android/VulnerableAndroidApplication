# Vulnerable Broadcast Receiver Walkthrough


## What is a Broadcast Receiver in Android?


The Broadcast Receiver is a component which listens for specified Broadcast messages and then can act upon the messages. A Broadcast message is wrapped within an Intent object and you specify what the message is for with the action string of the Intent. The Android system already sends out some Broadcasts messages when certain events occur. An example is when the phone gets put into airplane mode a Broadcast message with the action string set to "android.intent.action.AIRPLANE_MODE". Anyone can create a Broadcast Receiver within their app which then can subscribe to Broadcast messages and act upon them. An app can also create and send custom Broadcast messages for other apps to receive.


One way to create a Broadcast Reveicer is to declare it within the Manifest file of the app. You then add an Intent Filter to the Reveiver which will contain what Broadcast messages the Receiver should subscribe too.


## How to explopit the vulnerable Broadcast Receiver in the "Vulnerable SMS App"!


## Fixing the vulnerability


## Summary
