# Vulnerable Broadcast Receiver Walkthrough


## What is a Broadcast Receiver in Android?
The Broadcast Receiver is a component which listens for specified Broadcast messages and then can act upon the messages. A Broadcast message is wrapped within an Intent object and you specify what the message is for with the action string of the Intent. The Android system already sends out some Broadcasts messages when certain events occur. An example is when the phone gets put into airplane mode a Broadcast message with the action string set to "android.intent.action.AIRPLANE_MODE". Anyone can create a Broadcast Receiver within their app which then can subscribe to Broadcast messages and act upon them. An app can also create and send custom Broadcast messages for other apps to receive.


One way to create a Broadcast Reveicer is to declare it within the Manifest file of the app. You then add an Intent Filter to the Reveiver which will contain what Broadcast messages the Receiver should subscribe too.


## How to explopit the vulnerable Broadcast Receiver in the "Vulnerable SMS App"!
If you wish to follow along with the walkthrough (read this guide) on how to set up the vulnerable applications and an Android emulator.


The Broadcast Receiver within the SMS app handles sending an SMS message . The reason this Broadcast Receiver is vulneralbe is that any other app can send a Broadcast message to the receiver. This will then send that message to whoever they want without the user knowing. This could then be used in malicouse ways such as going over the users SMS limit and costing them money or sending dangerous links to the users contacts and the message would look like it is from the user.


### Step 1:
First open the app, add a contact and send a message to them too see that the app is working. As you cannot send SMS messages from the emulator instead it logs a logcat to emulate that a message has been sent. You can see this log within logcat in Android Studios. An example of this message is shown below.


![image](https://user-images.githubusercontent.com/45278231/111521332-ef7cd080-8750-11eb-8ab4-864868601b60.png)


### Step 2:
Now we need to find what the action is that the Broadcast Receiver is subscribed too. To do this we will use the the tool Apktool to decrypt the Manifest file of the app. You can download the tool here (https://ibotpeaches.github.io/Apktool/install/). Then run the command "apktool d VulnerableSMSApp.apk" in the directory where the apps apk file is (AppData/Local/Android/Sdk/platform-tools). There should now be a file called VulnerableSMSApp with the decrypted Manifest file within.


### Step 3:
Open up the decrypted Manifest file. Here we can see that action that the Broadcast Receiver is subscribed too is called "sendSMSBroadcast".


![image](https://user-images.githubusercontent.com/45278231/111522377-1d164980-8752-11eb-8979-ca1e143d0da0.png)


### Step 4:
Now we know the name of the action we can start building the app to exploit the Broadcast Receiver. Create a new project in Android studios and change the activity_main.xml file to look like so.


![image](https://user-images.githubusercontent.com/45278231/111523151-d83ee280-8752-11eb-82cb-b9ac91a9f148.png)
![image](https://user-images.githubusercontent.com/45278231/111523176-e0971d80-8752-11eb-81fb-cd22e3ae4ae8.png)


### Step 5:
Then in MainActivity add the function sendMessage().


![image](https://user-images.githubusercontent.com/45278231/111523387-248a2280-8753-11eb-9c81-08f5dfa30c17.png)


What this function does is it takes the number and message from the EditText fields and adds them to an Intent. The action of the intent is set to "sendSMSBroadcast" which we got from the decrypted manifest file. Then we set the componet of the intent with the package name and class name of the SMS app which we also got from the Manifest file.


### Step 6:
Now build and run the app on the emulator. Enter a message and the number to send the message too. Then click the "send message" button.


![image](https://user-images.githubusercontent.com/45278231/111524411-4a63f700-8754-11eb-8032-1c93f0144268.png)


Then look in logcat and you should see the number and message showing that the message has been sent. You can also go back into the SMS app and see that you cannot see the message so the user wouldn't know that the message got sent.


![image](https://user-images.githubusercontent.com/45278231/111524601-8b5c0b80-8754-11eb-9308-333e1a3eb539.png)


## Fixing the vulnerability
To fix the vulnerability we want the Broadcast Receiver to check where the message is coming from. One way to do this would be to add a secret which is parsed with the Intent. The Broadcast Receiver then checks if the secret value is correct and only sends an SMS if it is. The new Broadcast Receiver would then look like so.


![image](https://user-images.githubusercontent.com/45278231/111525999-18ec2b00-8756-11eb-8814-d00f02c9c206.png)


Then in the SMS app when it sends a Broadcast message add the secret too the intent like so.

![image](https://user-images.githubusercontent.com/45278231/111526172-4e911400-8756-11eb-9ab6-ed277ad59a33.png)


## Summary
The SMS app has a Broadcast Receiver which sends an SMS message. The Broadcast Receiver is vulnerable because it does not check who sent a Broadcast message for it so a malicouse app could send SMS messages from the phone without the using knowing. When using a Broadcast Receiver you should always consider if you want anyone to be able to send a Broadcast message to the Receiver as depending on what the Receiver does this could be used in malicouse ways.
