# PopupListView
An android powerful listview,which item in listview can be click and popup show inner view under item view!

Example
---------


  ![Image text](https://github.com/s8871404/PopupListView/blob/master/example.gif) 

Demo on Google play
---------
You can try it out here [Google Play](https://play.google.com/store/apps/details?id=com.baobomb.popuplistview_sample)

Compile library in Android Studio
---------
    In your build.gradle add the following code
    
    dependencies {
        compile 'com.github.s8871404:PopupListView:1.0.1@aar'
    }

How to use
----------

To use the PopupListView, you need add following code in your main layout xml:

     <com.baobomb.popuplistview.PopupListView
        android:id="@+id/popupListView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
     </com.baobomb.popuplistview.PopupListView>

And you need a layout for listView item design by yourself,
then a layout design by your self as the extend view which show after click list item

Set list in code
--------------
After set PopupListView at main layout and design two layout, one for list item another for inner view
now it can be set in code and use it amazing!
To set it: 
  
It's need an arrayList to collect PopupView,the custom list item object by library

       ArrayList<PopupView> popupViews = new ArrayList<>;

Then collect all the popupviews design by yourself

       PopupView popupView = new PopupView(this, R.layout.popup_view_item) {
          @Override
          //Set list item's element in here
            public void setViewsElements(View view) {
               TextView textView = (TextView) view.findViewById(R.id.title);
               textView.setText("Popup View " + String.valueOf(p));
               }
          @Override
          //Set extendView's element in here
            public View setExtendView(View view) {
               View extendView;
                 if (view == null) {
                 extendView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.extend_view, null);
                 TextView innerText = (TextView) extendView.findViewById(R.id.innerText);
                 innerText.setText("Inner View " + String.valueOf(p));
                 }else {
                 extendView = view;
                 }
                 return extendView;
                 }
              };
          popupViews.add(popupView);

After you collect all the popup list item then set it to the PopupListView

        PopupListView popupListView = (PopupListView) findViewById(R.id.popupListView);
        
if you want to custom the listview's parent layout you can init it and set the custom view layout res id to it
or just set it null then it will use the default layout,

        //isWithActionBar : default = false , if your app have an actionBar, set it true;
        //isWithStatusBar : default = true , if your app is set as full screen and status bar dismiss, set it false.
        popupListView.init(View customListBackground,Boolean isWithActionBar,Boolean isWithStatusBar); 
        popupListView.setItemViews(popupViews);
        
Then you can use it amazing!!

Control it 
-----------
If you just click the item and it already extend
When you want to minify it call following code

      if(popupListView.isZoomIn()){
        popupListView.zoomOut();
       }


Make it powerful
---------------

If it need to minify when user click back button
just add the following code at your activity

      @Override
      public void onBackPressed() {
        if (popupListView.isItemZoomIn()) {
            popupListView.zoomOut();
        } else {
            super.onBackPressed();
        }
      }
      
License
------------
    A subclass of the Android ListView component that enables item click to extend.
    
    Copyright 2015 CHEN WEI-LUNG
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
