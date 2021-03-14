# Vulnerable Service Walkthrough

## What is a Service in Android?
A Service component is used to perform background tasks, this means it has no user interface and does not need any user input. An example of a Service would be playing music, you are still able to use other apps on the phone while music is playing as it is running in the background. There are three types of Services which are as following.


Background Service:\
A background Service is not noticed by the user when it is running.


Foreground Service:\
A foreground Service will always display a notificaiton to the user so the user knows it is running.

Bound Service:\
A bound Service allows other app comopnents to bind onto it. What it means for component to bind to a service is that component can then interact with the Service and do things such as request and receive data.

## How to explopit the vulnerable Service in the "Vulnerable Banking App"!
The vulnerable service's purpose in the banking app is to make a transaction and confirm the transaction was completed succesfully. The reason this is a service component is that to make a transaction web requests need to be made which won't happen instantly. So as this will be running on a service the user can still use the app while a transaction is being made. The vulnerability is that the Service is exported which means that any other app can also start the service and make a transaction.


### Step 1:
First open the app, create an accout and login (Do not enter any real details or passwords). Then go to the transaciton tab, you can see that you need to re-authorize to be able to make a transaction. Once you have re-authorsied you can make a transaction to a test account with the account number 315544 to see that transactions are working.


### Step 2:
Now we need to find what the vulnerability is in the app. To do this we will use the the tool Apktool to decrypt the Manifest file of the app. You can download the tool here (https://ibotpeaches.github.io/Apktool/install/). Then run the command "apktool d BankingApp.apk" in the directory where the apps apk file is (AppData/Local/Android/Sdk/platform-tools). There should now be a file called BankingApp with the decrypted Manifest file within.


![image](https://user-images.githubusercontent.com/45278231/111073050-15963c80-84d5-11eb-88f0-d61a811060d4.png)


On line 23 we can see a service with "exported=true". This is the vulnerability as we can then start this service from another app wihtout needing any sort of permissions. Also, we can see the name of the service is TransactionService so you can tell it is used to make a transaction.


### Step 3:
Next we need to find out if we need to parse any data into the service.


## Fixing the vulnerability

## Summary
