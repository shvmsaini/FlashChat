# FlashChat

A simple chat app.

Users can register using Email and password (Without any email confirmation). The users can also set a username(display name).

This app works on my Firebase database and authentication system. If you want to change that to use your own Firebase then you can do that by following the procedure below:
1. Go to AndroidManifest file and select the package name (com.prochat.flashchatnewfirebase) then right click and go to refactor and set a desired package name(this is will change every usage of the app package to the one you set).
2. Go to Build.gradle(Module: app) and change the applicationId to your Package and sync.
3. Go to [Firebase](https://console.firebase.google.com/u/0/) > Add project and follow the guide to link Firebase to your project.

This app lacks many functionalities as of now, I will try add them as soon as I learn them and for that you can raise an issue as well.

Screenshots:

<img src="https://raw.githubusercontent.com/shvmsaini/ProjectImages/master/Screenshot_1592893390.png?token=AHD4PEPERCUZALPDC32I6HK67LQJQ" width="30%"></img>
<img src="https://raw.githubusercontent.com/shvmsaini/ProjectImages/master/Screenshot_1592893396.png?token=AHD4PEJXEJQ475NB2NQLZQK67LQNC" width="30%"></img>
<img src="https://raw.githubusercontent.com/shvmsaini/ProjectImages/master/Screenshot_1592894381.png?token=AHD4PEOMXKWKPQM2AQNKUDS67LQPA" width="30%"></img>

All the codes are based on java.

I am working on Android Studio 4.0 with Minimum SDK: API 21: Android 5.0 (Lollipop).

## Installation

METHOD 1 : Download as zip and open the zip with Android Studio.

METHOD 2 : Go to Android Studio>VCS>Checkout from Version Control>Git>"Paste this repository URL"
