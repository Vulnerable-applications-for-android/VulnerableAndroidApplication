# Vulnerable Activity walkthrough

## What is an Activity in Android?
The Activity component is one of the main components used when creating Android applications. An Activity is what provides a window where the apps UI is drawn. In general every screen in an app is a different Activity such as in a emailing app the screen showing all of the emails would usually be a differnt Activity to the screen displaying a specific email.


When using Activities in a app you must declare the Activity within the Manifest file for that project.


## How to explopit the vulnerable Activity in the "Vulnerable Banking App"!
If you wish to follow along with the walkthrough then read [this guide](https://github.com/FraserGrandfield/VulnerableAndroidApplication/blob/main/Walkthroughs/SetUp.md) on how to set up the vulnerable applications and an Android emulator.


The vulnerable Activity is present within the vulnerable banking app. The way the banking app works is it remembers the user who last logged on so that they can easily view their balance without having to keep entering their password. However for the user to make a transaction and send money the user must re-authorize by entering in their account email and password to stop unauthorised transactions. The exploit present in the activity is it will let an attacker bypass the re-authenticate screen and go straight to the transcation page. This is dangourse as the attacker could make a transaction on the users account without needing to know the users logging details.


### Step 1:
First open the app, create an accout and login (Do not enter any real details or passwords). Then go to the transaciton tab, you can see that you need to re-authorize to be able to make a transaction. Once you have re-authorsied you can make a transaction to a test account with the account number 315544 to see that transactions are working.


### Step 2:
Now we need to find what the vulnerability is in the app. To do this we will use the the tool Apktool to decrypt the Manifest file of the app. You can download the tool [here](https://ibotpeaches.github.io/Apktool/install/). Then run the command `apktool d VulnerableBankingApp.apk` in the directory where the apps apk file is (AppData/Local/Android/Sdk/platform-tools). There should now be a file called VulnerableBankingApp with the decrypted Manifest file within.


![image](https://user-images.githubusercontent.com/45278231/111070666-9c91e780-84ca-11eb-9a46-de7f1fdc23e9.png)


Lines 15-20 we can see there is an activity called TransactionActivity which has an intent filter. An intent filter specifies what intents the activity component should receive. As you can see the category specified is "android.intent.category.DEFAULT", this means that any implicit intent can start this activity.


### Step 3:
To exploit the vulnerability create a new project in Android Studio. Edit the activity_main.xml file to have a button like so.


![image](https://user-images.githubusercontent.com/45278231/111070954-e4653e80-84cb-11eb-9b33-1016b3366cf6.png)


Then in the MainActivity add the on click function and link the function up to the button in the xml file.


![image](https://user-images.githubusercontent.com/45278231/111071009-1bd3eb00-84cc-11eb-9c2c-50f289fa4603.png)


What this function does is it creates a new intent. The intent is to open the vulnerable TransactionActivity. As you cans see there are two things we need to open the Activity, these are the package name of the app and the class name of the Activiy we wish to start. Both of these can be found from the Manifest file we decomplied.


### Step 4:
Finally run the Malicous app you just created on the Android emulator and click the button to open the TransactionActivity. You will be taken to the TransactionActivity without having to re-authenticate.


## Fixing the vulnerability
To stop the Activity from being vulnerable it needs to not be exported. In Manifest file the Activity has an Intent Filter which makes the Activity exported.


![image](https://user-images.githubusercontent.com/45278231/111465159-a4939680-8719-11eb-9301-e0ded01a19b9.png)


Therefore to stop it from being exportedyou just need to remove the Intent Filter. The Activity in the Manifest file should then look like below.


![image](https://user-images.githubusercontent.com/45278231/111465105-96de1100-8719-11eb-84bc-2a50ff7a3491.png)


## Summary
This walkthrough shows that the vulnerability of using an intent filter without knowing what behavouir this gives the Activity is very dangoures and can give users access to Android components they shoudld not have access too. The walkthrough should show you the importance of understanding your own code so it doesn't have unwanted behaviours which could put the user at risk.
