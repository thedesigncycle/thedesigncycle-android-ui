
## Dependency




```java
compile 'com.thedesigncycle.ui:views:0.3.1'
```

&nbsp;
## CircleButton

![](https://i.imgur.com/4toNvty.gif | height = 150)


**XML**

``` xml
<com.thedesigncycle.ui.CircleButton
	  xmlns:app="http://schemas.android.com/apk/res-auto"
      android:layout_width="100dp"
      android:layout_height="100dp"
      app:buttonColor="#ff0000"
      app:icon="@drawable/ic_list"
  />
```




Attribute |  Description | Example |
---------|----------|-------|
      `buttonColor`   | Color of the button |       `#ff0000` or `@color/myButtonColor`
 `icon`| Icon resource| `@drawable/ic_call`

&nbsp;


**Java**

Create at runtime

```java 
CircleButton myCircleButton = new CircleButton(context);
```


or fetch from layout

```java
CircleButton myCircleButton = (CircleButton) findViewById(R.id.my_circle_button);
```


Customize
```java
myCircleButton.setColor("#FF0000");

myCircleButton.setIcon(getResources().getDrawable(R.drawable.ic_list));
```


**Methods**

 Method | Description  |
 -------|----------|
  `setButtonColor(int Color)` | Set color of the button |
 `setIcon(Drawable icon)` | Set icon drawable |
&nbsp;

`CircleButton` inherits all public methods from `View`, including `setOnClickListener(...)`


&nbsp;

## CircleToggleButton

![](https://i.imgur.com/ky4JCYN.gif | height = 150)

**XML**
```xml
 <com.thedesigncycle.ui.CircleToggleButton
        android:id="@+id/my_circle_toggle"
        android:layout_width="100dp"
        android:layout_height="100dp"
        app:buttonColorChecked="#00cc00"
        app:iconChecked="@drawable/ic_check"
        app:buttonColorUnchecked="#ff0000"
        app:iconUnchecked="@drawable/ic_wifi"
        />
```


 Attribute |  Description | Example
---------- |--------------|-----------
      `buttonColorChecked`   | Color of the button in **checked state** |       `#00ff00` or `@color/myCheckedColor`
`iconChecked`| Icon resource for **checked state** | `@drawable/ic_check`
      `buttonColorUnchecked`   | Color of the button in **unchecked state** |       `#ff0000` or `@color/myUncheckedColor`
`iconUnchecked`| Icon resource for **unchecked state** | `@drawable/ic_close`

&nbsp;

**Java**

Create at runtime
```java
CircleToggleButton circleToggleButton = new CircleToggleButton(context);
```

or fetch from layout
```java
CircleToggleButton circleToggleButton = (CircleToggleButton) findViewById(R.id.my_circle_toggle);
```

Customize
```java
circleToggleButton.setButtonColorUnchecked(Color.RED);
circleToggleButton.setButtonColorChecked(Color.parseColor("#00cc00"));
circleToggleButton.setIconUnchecked(getResources().getDrawable(R.drawable.ic_wifi));
circleToggleButton.setIconChecked(getResources().getDrawable(R.drawable.ic_check));
```

**Methods**

Method | Description  |
------------ | -------|
 `setButtonColorUnchecked(int Color)` | Set color of the button for **unchecked state** |
`setIconUnchecked(Drawable icon)`| Set icon drawable for **unchecked state** |
 `setButtonColorChecked(int Color)` | Set color of the button for **checked state** |
`setIconChecked(Drawable icon)`| Set icon drawable for **checked state** |

&nbsp;

`CircleToggleButton` inherits all public methods from `CompoundButton`, including `setOnCheckedChangeListener(...)`


&nbsp;

##RippleView

![](https://i.imgur.com/gZT3Rb3.gif | height=150)

###XML

```xml

    <com.thedesigncycle.ui.RippleView
        android:layout_width="200dp"
        android:layout_height="200dp"

        app:colorMode="gradient"
        app:gradientColor1="#FA67BF"
        app:gradientColor2="#E37F83"
        app:rippleCount="5"
        app:rippleDuration="3000"
        app:smallCircleScale="0.2"
        app:gravity="center"
        />
```


Attribute | Description | Example | Default
--------- | ----------- | --------- | -----
`colorMode` | Solid color or gradient | `solid` or `gradient` | `solid` |
`solidColor` | Color (if solid color mode) | `#00ff00` or `@color/myColor` |
`gradientColor1` | Color 1 of gradient (if gradient color mode) | `#00ff00` or `@color/myColor1` |
`gradientColor2` | Color 2 of gradient (if gradient color mode)| `#0000ff` or `@color/myColor2` |
`rippleCount` | Number of visible ripples at any instance of time | Recommended between `2` and `10` | `5`
`rippleDuration` | Duration of animation of ripple in milliseconds | Recommended between `100` and `5000` | `1000`
`smallCircleScale` | The scale of the initial circle | Between `0.1` and `0.9` | `0.5`
`gravity` | The anchor of the ripples | `center` `left` `top` `right` `bottom` `top|left` `bottom|right` etc. | `center`


&nbsp;

###Java

Create at runtime
```java
RippleView rippleView = new RippleView(context);
```

or fetch from layout
```java
RippleView rippleView = (RippleView) findViewById(R.id.my_ripple_view);
```

&nbsp;

**Customize**


Anchor to an edge

```java
rippleView.setGravity(Gravity.LEFT);
```

Anchor a corner

```java
rippleView.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
```


Gradient

```java
rippleView.setGradient(color1, color2);
```
Solid color
```java
rippleView.setSolidColor(color);
```

Ripple Count
```java
rippleView.setRippleCount(5);
```

Ripple Animation Duration (in milliseconds)
```java
rippleView.setRippleDuration(1000);
```


Scale of the starting ripple (relative to the view size)
```java
rippleView.setSmallCircleScale(0.5f);
```