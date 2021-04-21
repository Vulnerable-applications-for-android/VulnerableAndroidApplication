# Vulnerable Content Provider Walkthrough


## What is a Content Provider in Android?
A Content Provider object manages the access to a centreal repository of data such as an SQLite database. Content Providers can be used so multiple apps have a consistant way of accessing the same data. An example would be havine a Content Provider for accessing photos so multiple apps can access the photos with ease. Content Providers, when used correctly, provide secruity too data which you wish to share with certain other apps or widgets.


When using Content Providers in an app you must declare the Content Provider within the manifest file for that project.


## How to explopit the vulnerable Content Provider in the "Vulnerable SMS App"!
If you wish to follow along with the walkthrough then read [this guide](https://github.com/FraserGrandfield/VulnerableAndroidApplication/blob/main/Walkthroughs/SetUp.md) on how to set up the vulnerable applications and an Android emulator.

The Content Provider within the SMS app controls the access to the messages the user has sent and received. The current issue is the Content Provider is exported. This means that any other app can access the Content Provider and  use it too query or insert data. As this is an SMS app a maliocuse application could use the Content Provider to insert a text which looks like its from the bank with a malicouse link.


### Step 1:
First open the app as you will need to set the app as your messaging app to be able to receive SMS messages. Next send an SMS message to the emulator to show that the Content Provider is working and is adding the received messaged to the database. To do this click the settings button (button with three dots) on the side of the emulator. Select the "Phone" setting and here you can enter a number and message for the emulator to receive a SMS from.


### Step 2:
Now we need to find what the vulnerability is in the app. To do this we will use the the tool Apktool to decrypt the Manifest file of the app. You can download the tool [here](https://ibotpeaches.github.io/Apktool/install/). Then run the command `apktool d VulnerableSMSApp.apk` in the directory where the apps apk file is (AppData/Local/Android/Sdk/platform-tools). There should now be a file called VulnerableSMSApp with the decrypted Manifest file within.


### Step 5:
Open up the decrypted Manifest file. Here we can find that the Content Provider is exported, therefore we can use it for other applications. Secondly we can see the authoritites string. This string is used when accessing data within the provider.


![image](https://user-images.githubusercontent.com/45278231/111556258-0556b980-8782-11eb-9c9d-3fd30b569d2e.png)


### Step 4:
Now that we know the Content Provider is exported and we have the authority string of the Provider we now need to find the table names of the database and field names so we know what data to ask for. To find out this data, open Android Studios and run the emulator with the SMS app on. Then in Android studios click on Device File explorer in the bottom right.

![image](https://user-images.githubusercontent.com/45278231/111556476-a34a8400-8782-11eb-856f-09c8ad2c5066.png)


Then go to the folder "data/data/com.example.vulnerablesmsapp/databases". Right click on the file called SMS and save it onto your computer with the file extension ".db". 


![image](https://user-images.githubusercontent.com/45278231/111556613-e99fe300-8782-11eb-825e-aac9434fab71.png)


Now we need to use the tool DB Browser for SQLite to open the database file and read it. You can download the latest version of the tool [here](https://sqlitebrowser.org/blog/version-3-12-1-released/). Once you have installed the program, open it and go to "file->Open Database" and open the SMS.db file you saved. It should then show you four tables. We are only interested in two of these which are "contacts" and "messages". You can expand these tables to then see the field names within.


![image](https://user-images.githubusercontent.com/45278231/111556891-82366300-8783-11eb-91a3-fac6b9bef641.png)


### Step 5:
Now that we have all of the information required to exploit the Content Provider we can make a malicouse app to do that. First create a new project in Android Studios. Then change the activity_main.xml to look like the following.


![image](https://user-images.githubusercontent.com/45278231/111557726-240a7f80-8785-11eb-8437-a1c41d874a9f.png)
![image](https://user-images.githubusercontent.com/45278231/111557774-384e7c80-8785-11eb-86eb-25057db68f91.png)


### Step 6:
Then in Main Activity add these two functions.

![image](https://user-images.githubusercontent.com/45278231/111558532-d4c54e80-8786-11eb-9be5-eadce6629364.png)
![image](https://user-images.githubusercontent.com/45278231/111558415-9fb8fc00-8786-11eb-9ed2-0700975f18e6.png)


The function "addSMSMessageOnClick" will check if the content provider already has the number the user wishes to add into the database. If the number already exists then just the message is added, otherwise the new contact and message is added. The second function getIdFromNumber gets the primary key id from the database which relates to the number entered by the user. As you can see on lines 25-27 we have used the authority string and the table names which we found out from the Manifest and SMS.db file.


### Step 7:
Finally we can build and run the malicouse app on the emulator. You can enter a number, name for who it looks like the message is from and then click the "Add maliciouse SMS message" button.


![image](https://user-images.githubusercontent.com/45278231/111559385-510c6180-8788-11eb-8dcc-5dd5392ac9be.png)


Then when you go into the SMS app you will have a message from that number with the message.


![image](https://user-images.githubusercontent.com/45278231/111559440-6bded600-8788-11eb-87fe-ba20d49728e2.png)
![image](https://user-images.githubusercontent.com/45278231/111559455-7305e400-8788-11eb-9623-57a96fb8faba.png)


## Fixing the vulnerability
One way to fix the vulnerability would be to set the Content Provider attribute exported to false as shown below.


![image](https://user-images.githubusercontent.com/45278231/111640611-41c2fd80-87f4-11eb-815e-3c75616ea2b3.png)


However an issue with this is if you wish certain other apps to be able to use the Content Provider and access the data they won't be able too. So a way around this is to add a signiture to the app. The way this works is if an app then requests to use the Content Provider, if it is also signed with the same signiutre then the system will allow the app to use the component. You can read more information [here.](http://blog.sqisland.com/2011/11/android-protect-contentprovider-with.html)


Finally, another way to make the Content Provider more secure would be to encrypt the data when storing it and then decrypt it when you wish to access it. An example encryption algorithm you could use is AES.

## Summary
The Content Provider component provides an easy way to access a centeral repository of data. In the case of the SMS app this stores the users contacts and messages. As the Content Provider has exported set to "true" it is vulnerable as other apps have access to it. As one of the reasons to use a Content Provider is to share the data with other apps and widgets you might wish to have exported to true but you may wish to only give access to certain apps. The way to do this would be to give your app and the apps you wish to share the data with the same signiture. As a Content Provider could store any data this walkthrough shows the dangers of one being vulernable especially if it has access to sensative data such as the users address.
