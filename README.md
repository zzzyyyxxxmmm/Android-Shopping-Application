Android Shopping Application
==================
Jikang Wang
# OverView
It has four pages: Home, Group Buying, Find, Mine

## Technical Points
* UI Design: Implemented Tab Menu, Advanced ListView(ListView + Adapter + Holder), Complicated Layout Design.
* Server: Designed j2EE Server connected to Local MySQL, Recieved Post Request and sent Json information to Client. Rendering Info from sql to View.
* Third Party Frame:  XUtils(Dependency Injections, Http request), Picasso(Image Loader), MapSDK, Third-Party LoginSDK.


## Home
* It can locate where you are and you can also choose your own city. 
* The city info can be stored at ShareUtil.java in order to use at next time. It's implemented by android.content.SharedPreferences.Editor
* add the right slider bar: slide or choose the right bar to locate the city list. It's implemented by extending View
* Show the number of Catagory 

![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/7.png) ![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/6.png)
![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/8.png)

## Group Buying
* It's a classical ListView Design pattern(ListView + Adapter +Holder)
* Push From up to refreshe, Push from button to load more data, It's implemented by [PullRefresh](https://github.com/chrisbanes/Android-PullToRefresh)

![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/9.png)![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/10.png)
![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/11.png)

## Find
* It's implemented by importing AutoNavi SDK
* Show the near Shopping

![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/14.png)

## Mine
* User Sign In, Sign Up, Login In by Third Party Platform

![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/12.png)![Home](https://github.com/zzzyyyxxxmmm/Android_test_01/blob/master/Saved%20Pictures/13.png)

