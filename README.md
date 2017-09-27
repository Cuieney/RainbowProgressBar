## Android RainbowProgressBar

***
The RainbowProgressBar is a progressbar, colorful and amazing(For everyone)

Today is a sunny day and I saw a rainbow ,so I decided to do this for Android developer hhhh

And also you can contribute more style, or new idea to me.

***
## Demo
![rainbow icon](https://github.com/Cuieney/RainbowProgressBar/blob/master/app/rainbow.gif)



[Download Demo](https://github.com/Cuieney/RainbowProgressBar/blob/master/app/example-debug.apk "a Safari extension")
***
## version
v1.0.1
Add a new style 

v1.0.0
The basic function

## Usage
***
### Gradle

```
compile 'com.cuieney.library:library:1.0.0'

```

### Maven

```
<dependency>
  <groupId>com.cuieney.library</groupId>
  <artifactId>library</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>


```

Copy this code to your layout

```
    <com.cuieney.progress.library.RainbowProgressBar
        android:id="@+id/rainbow_progress_bar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

```

I made some predesign style. You can use color via style property.



![rainbow icon](https://github.com/Cuieney/RainbowProgressBar/blob/master/app/line.png)
![rainbow icon](https://github.com/Cuieney/RainbowProgressBar/blob/master/app/circle.png)

In the above picture, they look so great,
you just needed set startColor and endColor attributes，can achieve the effect


#### Attributes

Attributes | format 
------------ | ------------- 
progress_current | integer  
progress_max | integer 
progress_start_color | color 
progress_end_color | color 
progress_unreached_color | color 
progress_height | dimension 
progress_radius | dimension 
progress_type | enum
        

for example(line progress bar), the default style:


``` 
  <com.cuieney.progress.library.RainbowProgressBar
        android:id="@+id/progress1"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        app:progress_max="100"
        app:progress_current="20"
        app:progress_start_color="#00ff00"
        app:progress_end_color="#0000ff"
        app:progress_type="line"
        app:progress_height="5dp"
        />

```

### About me:
a intersting man,
stay hungry stay foolish,good good study day day up hhh
