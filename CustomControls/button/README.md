Custom Button
=======
This button handles onTouch events on itself and You can use it to place your event tracker class.

Usage:
====
Copy mbutton.xml into your layout resource folder
YOUR_PROJECT_RES_FOLDER\layout\mbutton.xml

Copy MButton.java into your source folder:
YOUR_PROJECT_SRC_FOLDER\MButton.java

Open your layout file, where You want to use this button and 
(Open it in Xml/Text View)
and add the button:
```xml
    <com.path.to.MButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="New Button with Tracker"
        android:id="@+id/button"
        android:tag="This is Button 1"
        android:onClick="onClick"
        android:clickable="true"
        android:focusable="true" />
```