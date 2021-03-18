# This document explains how to set up the vulnerable apps to be able to follow the walkthroughs.

### Step 1:
First install Android Studios from here https://developer.android.com/studio.


### Step 2:
Next install the VulnerableSMSApp and VulnerableBankingApp APK files from here (Insert link here).


### Step 3:
Now start Android Studios and press the green arrow button to start the emulator. (Make user to use an emulator with API version 27 or above)


![image](https://user-images.githubusercontent.com/45278231/111658585-64f5a900-8804-11eb-859b-62129339cf7a.png)


### Step 4:
Then copy the APK files into the file Appdata/Local/Android/Sdk/platform-tools.


![image](https://user-images.githubusercontent.com/45278231/111658795-95d5de00-8804-11eb-90bf-74377fdad6a2.png)


### Step 5:
Then open a terminal or cmd at that file location and run the command to install the apps on the emulator.


adb install -r -t VulnerableBankingApp.apk
adb install -r -t VulnerableSMSApp.apk


(If you are using Linux or MacOS put "./" infront of adb)


Now you should have both of the vulnerable apps on the emulator.


## Possible issues
If the emulator doesn't start with no warnings this could be because virtualization is turned off in the bios. Here is a guide on how to turn this on https://2nwiki.2n.cz/pages/viewpage.action?pageId=75202968#:~:text=ON%20the%20System.-,Press%20F2%20key%20at%20startup%20BIOS%20Setup.,changes%20and%20Reboot%20into%20Windows.
