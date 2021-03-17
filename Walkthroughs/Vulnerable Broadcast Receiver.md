# Vulnerable Broadcast Receiver Walkthrough


## What is a Broadcast Receiver in Android?
The Broadcast Receiver is a component which listens for specified Broadcast messages and then can act upon the messages. A Broadcast message is wrapped within an Intent object and you specify what the message is for with the action string of the Intent. The Android system already sends out some Broadcasts messages when certain events occur. An example is when the phone gets put into airplane mode a Broadcast message with the action string set to "android.intent.action.AIRPLANE_MODE". Anyone can create a Broadcast Receiver within their app which then can subscribe to Broadcast messages and act upon them. An app can also create and send custom Broadcast messages for other apps to receive.


One way to create a Broadcast Reveicer is to declare it within the Manifest file of the app. You then add an Intent Filter to the Reveiver which will contain what Broadcast messages the Receiver should subscribe too.


## How to explopit the vulnerable Broadcast Receiver in the "Vulnerable SMS App"!
If you wish to follow along with the walkthrough (read this guide) on how to set up the vulnerable applications and an Android emulator.


The Broadcast Receiver within the SMS app handles sending an SMS message . The reason this Broadcast Receiver is vulneralbe is that any other app can send a Broadcast message to the receiver. This will then send that message to whoever they want without the user knowing. This could then be used in malicouse ways such as going over the users SMS limit and costing them money or sending dangerous links to the users contacts and the message would look like it is from the user.


## Fixing the vulnerability
Add secret to extras string


## Summary
