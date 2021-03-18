# Vulnerable Content Provider Walkthrough


## What is a Content Provider in Android?
A Content Provider object manages the access to a centreal repository of data. Content Providers can be used so multiple apps have a consistant way of accessing the same data. An example would be havine a Content Provider for accessing photos so multiple apps can access the photos with ease. Content Providers, when used correctly, provide secruity too data which you wish to share with certain other apps or widgets.


When using Content Providers in an app you must declare the Content Provider within the manifest file for that project.


## How to explopit the vulnerable Content Provider in the "Vulnerable SMS App"!
If you wish to follow along with the walkthrough (read this guide) on how to set up the vulnerable applications and an Android emulator.


The Content Provider within the SMS app controls the access to the messages the user has sent and received. The current issue is the Content Provider is exported. This means that any other app can access the Content Provider and  use it too query or insert data. As this is an SMS app a maliocuse application could use the Content Provider to insert a text which looks like its from the bank with a malicouse link.


### Step 1:
Run app, send message to self to see you can receive messages.



### Step 2:
Use android studio and DB Browser for SQLite to find database tables and fields.



## Fixing the vulnerability
(Fix change exported to false. Talk about other fixes could be siging it so only your other apps can use the Content Provider)

## Summary


(One reason for using a content provider like this is if you wish wigits to access the data and display to the user.)
