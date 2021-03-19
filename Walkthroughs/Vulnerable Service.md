# Vulnerable Service Walkthrough

## What is a Service in Android?
A Service component is used to perform background tasks, this means it has no user interface and does not need any user input. An example of a Service would be playing music, you are still able to use other apps on the phone while music is playing as it is running in the background. There are three types of Services which are as following.


Background Service:\
A background Service is not noticed by the user when it is running.


Foreground Service:\
A foreground Service will always display a notificaiton to the user so the user knows it is running.

Bound Service:\
A bound Service allows other app comopnents to bind onto it. What it means for component to bind to a service is that component can then interact with the Service and do things such as request and receive data.


When using Services in a app you must declare the Service within the Manifest file for that project.


## How to explopit the vulnerable Service in the "Vulnerable Banking App"!
If you wish to follow along with the walkthrough then read [this guide](https://github.com/FraserGrandfield/VulnerableAndroidApplication/blob/main/Walkthroughs/SetUp.md) on how to set up the vulnerable applications and an Android emulator.

The vulnerable service's purpose in the banking app is to make a transaction and confirm the transaction was completed succesfully. The reason this is a service component is that to make a transaction web requests need to be made which won't happen instantly. So as this will be running on a service the user can still use the app while a transaction is being made. The vulnerability is that the Service is exported which means that any other app can also start the service and make a transaction.


### Step 1:
First open the app, create an accout and login (Do not enter any real details or passwords). Then go to the transaciton tab, you can see that you need to re-authorize to be able to make a transaction. Once you have re-authorsied you can make a transaction to a test that it works. Send money to the test account "315544" to see that transactions are working.


### Step 2:
Now we need to find what the vulnerability is in the app. To do this we will use the the tool Apktool to decrypt the Manifest file of the app. You can download the tool [here](https://ibotpeaches.github.io/Apktool/install/). Then run the command `apktool d VulnerableBankingApp.apk` in the directory where the apps apk file is (AppData/Local/Android/Sdk/platform-tools). There should now be a file called VulnerableBankingApp with the decrypted Manifest file within.


![image](https://user-images.githubusercontent.com/45278231/111073050-15963c80-84d5-11eb-88f0-d61a811060d4.png)


On line 23 we can see a service with "exported=true". This is the vulnerability as we can then start this service from another app without needing any sort of permissions. Also, we can see the name of the service is TransactionService so you can tell it is used to make a transaction.


### Step 3:
Now lets create an app to exploit the service. First create a new project in Android Studio. Add a button to the activity_main.xml file like so.


![image](https://user-images.githubusercontent.com/45278231/111073790-04026400-84d8-11eb-96f9-bdb89206362d.png)


Next in MainActivity add the function and link the function to the button you just created.


![image](https://user-images.githubusercontent.com/45278231/111073833-357b2f80-84d8-11eb-8046-e5b963578d36.png)


This function creates an intent which will start the service. Two parameters are need which is the package name of the vulneranlbe banking app and the class name of the Service component we want to start. You get both of these from the Manifest file we decrypted. Next build and run the malicious app on the emulator. Before we try to run the vulnerable service because the service is a backgroud service the vulnerable app must be open for this to work. All you need to do is open the app and then minimize it for this to work. Now we can click the button to exploit the Service.


![image](https://user-images.githubusercontent.com/45278231/111074162-9e16dc00-84d9-11eb-8f8f-e116cc7ba52e.png)


When we click the button we get an error saying the payment cannot be made. Lets look at logcat in Android Studio for the emulator to see if there is any information why.


![image](https://user-images.githubusercontent.com/45278231/111075002-ac66f700-84dd-11eb-9720-9545922963d8.png)


Here we can see a transaction error where it says the intent vaules for accountNumber and amount are blank or null. So we probably need to parse these values with the intent when starting the service. The accountNumber and amount would just be the accountNumber to send the money too and amount is the amount to send. So lets add two edit texts in the activity_main.xml file to input these values.


![image](https://user-images.githubusercontent.com/45278231/111075038-f354ec80-84dd-11eb-9747-e1642a24deb4.png)


And then lets take these values and add them to the intent to be parsed to the service.


![image](https://user-images.githubusercontent.com/45278231/111075067-197a8c80-84de-11eb-992c-985575f714b8.png)


Above is what the new function should look like. Now build and run the malicious-- app on the emulator, input an account number (892386) and amount to send money too and click the button to start the TransactionService.


![image](https://user-images.githubusercontent.com/45278231/111075195-b63d2a00-84de-11eb-8e5c-41ef2edcf5b1.png)


Then go back into the banking app and you will see the payment would have been made.


![image](https://user-images.githubusercontent.com/45278231/111075223-d7057f80-84de-11eb-9ed7-2e24a4b09143.png)

## Fixing the vulnerability
There are two ways you could fix this vulnerability.


### First Fix:
This first is as simple as in the Manifest file set exported to false. This means no other apps will be able to start the Service.


![image](https://user-images.githubusercontent.com/45278231/111393609-f5bb7000-86b0-11eb-927c-316bd6dd6c2d.png)


### Second Fix:
The second way to fix the Service would allow only apps you choose to start the Service. To do this you would add a secret key to the intent that starts the Service. 


![image](https://user-images.githubusercontent.com/45278231/111394026-cc4f1400-86b1-11eb-8773-108544f8dd86.png)


Then when the Service starts you would check if the secret is correct, if it is not correct then terminate the Service.


![image](https://user-images.githubusercontent.com/45278231/111393902-8e51f000-86b1-11eb-80aa-3a004f626a23.png)


On line 35 you can see getting the secret from the intent and on line 36 checking if the secret is correct. For better security a much more secure secret should be used as this one could easily be brute forced.


## Summary
The exploit presented is haivng an exported Service which then allows anyone to make a transaction to an account of their choosing. This is quite an extream example as no banking app would have an exported Service such as this one. However, it is still possible for developers to create exported services which have unwanted behavours. This walkthrough shows that you need to think when using a Service if it should be exported, and if it should then should you only allow certain apps to be able to start it.
