# Vulnerable Content Provider Walkthrough


## What is a Content Provider in Android?
A Content Provider object manages the access to a centreal repository of data such as an SQLite database. Content Providers can be used so multiple apps have a consistant way of accessing the same data. An example would be havine a Content Provider for accessing photos so multiple apps can access the photos with ease. Content Providers, when used correctly, provide secruity too data which you wish to share with certain other apps or widgets.


When using Content Providers in an app you must declare the Content Provider within the manifest file for that project.


## How to explopit the vulnerable Content Provider in the "Vulnerable SMS App"!
If you wish to follow along with the walkthrough (read this guide) on how to set up the vulnerable applications and an Android emulator.


The Content Provider within the SMS app controls the access to the messages the user has sent and received. The current issue is the Content Provider is exported. This means that any other app can access the Content Provider and  use it too query or insert data. As this is an SMS app a maliocuse application could use the Content Provider to insert a text which looks like its from the bank with a malicouse link.


### Step 1:
First open the app as you will need to set the app as your messaging app to be able to receive SMS messages. Next send an SMS message to the emulator to show that the Content Provider is working and is adding the received messaged to the database. To do this click the settings button (button with three dots) on the side of the emulator. Select the "Phone" setting and here you can enter a number and message for the emulator to receive a SMS from.


### Step 2:
Now we need to find what the vulnerability is in the app. To do this we will use the the tool Apktool to decrypt the Manifest file of the app. You can download the tool here (https://ibotpeaches.github.io/Apktool/install/). Then run the command "apktool d VulnerableSMSApp.apk" in the directory where the apps apk file is (AppData/Local/Android/Sdk/platform-tools). There should now be a file called VulnerableSMSApp with the decrypted Manifest file within.


### Step 5:
Open up the decrypted Manifest file. Here we can find that the Content Provider is exported, therefore we can use it for other applications. Secondly we can see the authoritites string. This string is used when accessing data within the provider.


![image](https://user-images.githubusercontent.com/45278231/111556258-0556b980-8782-11eb-9c9d-3fd30b569d2e.png)


### Step 4:
Now that we know the Content Provider is exported and we have the authority string of the Provider we now need to find the table names of the database and field names so we know what data to ask for. To find out this data, open Android Studios and run the emulator with the SMS app on. Then in Android studios click on Device File explorer in the bottom right.

![image](https://user-images.githubusercontent.com/45278231/111556476-a34a8400-8782-11eb-856f-09c8ad2c5066.png)


Then go to the folder "data/data/com.example.vulnerablesmsapp/databases". Right click on the file called SMS and save it onto your computer with the file extension ".db". 


![image](https://user-images.githubusercontent.com/45278231/111556613-e99fe300-8782-11eb-825e-aac9434fab71.png)


Now we need to use the tool DB Browser for SQLite to open the database file and read it. You can download the latest version of the tool here https://sqlitebrowser.org/blog/version-3-12-1-released/. Once you have installed the program, open it and go to "file->Open Database" and open the SMS.db file you saved. It should then show you four tables. We are only interested in two of these which are "contacts" and "messages". You can expand these tables to then see the field names within.


![image](https://user-images.githubusercontent.com/45278231/111556891-82366300-8783-11eb-91a3-fac6b9bef641.png)


### Step 5:
Now that we have all of the information required to exploit the Content Provider we can make a malicouse app to do that.

## Fixing the vulnerability
(Fix change exported to false. Talk about other fixes could be siging it so only your other apps can use the Content Provider)

## Summary


(One reason for using a content provider like this is if you wish wigits to access the data and display to the user.)
