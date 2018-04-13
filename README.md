
## Dependency


```java
compile 'com.thedesigncycle.ui:views:0.2.3'
```

&nbsp;
## CircleButton


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


|Attribute|  Description| Example |
|--|--|--|
|      `buttonColor`   | Color of the button |       `#ff0000` or `@color/myButtonColor`
|`icon`| Icon resource| `@drawable/ic_call`

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

|Method| Description  |
|--|--|
| `setButtonColor(int Color)` | Set color of the button |
|`setIcon(Drawable icon)`| Set icon drawable |
&nbsp;

`CircleButton` inherits all public methods from `View`, including `setOnClickListener(...)`


&nbsp;

## CircleToggleButton

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

|Attribute|  Description| Example |
|--|--|--|
|      `buttonColorChecked`   | Color of the button in **checked state** |       `#00ff00` or `@color/myCheckedColor`
|`iconChecked`| Icon resource for **checked state** | `@drawable/ic_check`
|      `buttonColorUnchecked`   | Color of the button in **unchecked state** |       `#ff0000` or `@color/myUncheckedColor`
|`iconUnchecked`| Icon resource for **unchecked state** | `@drawable/ic_close`

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

|Method| Description  |
|--|--|
| `setButtonColorUnchecked(int Color)` | Set color of the button for **unchecked state** |
|`setIconUnchecked(Drawable icon)`| Set icon drawable for **unchecked state** |
| `setButtonColorChecked(int Color)` | Set color of the button for **checked state** |
|`setIconChecked(Drawable icon)`| Set icon drawable for **checked state** |

&nbsp;

`CircleToggleButton` inherits all public methods from `CompoundButton`, including `setOnCheckedChangeListener(...)`

