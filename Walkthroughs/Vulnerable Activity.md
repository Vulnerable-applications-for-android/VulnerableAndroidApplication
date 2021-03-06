# Vulnerable Activity

## What is an Activity in Android?
The Activity component is one of the main components used when creating Android applications. An Activity is what provides a window where the apps UI is drawn. In general every screen in an app is a different Activity such as in a emailing app the screen showing all of the emails would usually be a differnt Activity to the screen displaying a specific email.


When using Activities in a app you must declare the Activity within the manifest file for that project such as in the image bellow.


## How to explopit the vulnerable Activity in the "Vulnerable Banking App"!


If you wish to follow allong with the walkthrough (read this guide) on how to set up the vulnerable applications and an Android emulator.


The vulnerable Activity is present within the vulnerable banking app. The way the banking app works is it remembers the user who last logged on so that they can esily view there balance without having to keep entering their password. However for the user to make a transaction and send money the user must re-authorize by entering in their account email and password to stop unauthorised transactions. The exploit with the activity is it will let an attacker bypass the re-authenticate screen and go straight to the transcation page. This is dangourse as the attacker could make a transaction on the users account without needing to know the users logging details.

### Step 1:

## Fixing the vulnerability

## Summary
a
