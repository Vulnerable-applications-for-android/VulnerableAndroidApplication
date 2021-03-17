# Vulnerable Broadcast Receiver Walkthrough


## What is a Broadcast Receiver in Android?


The Broadcast Receiver is a component which listens for specified Broadcast messages and then can act upon the messages. A Broadcast message is wrapped within an Intent object and you specify what the message is for with the action string of the Intent. The Android system already sends out some Broadcasts messages when certain events occur. An example is when the phone gets put into airplane mode a Broadcast message with the action string set to "android.intent.action.AIRPLANE_MODE". Anyone can create a Braodcast Receiver within their app which then can listen for this type of Broadcast message and act upon it. An app can also create and send custom Broadcast messages for other apps to receive.


## How to explopit the vulnerable Broadcast Receiver in the "Vulnerable SMS App"!


## Fixing the vulnerability


## Summary
