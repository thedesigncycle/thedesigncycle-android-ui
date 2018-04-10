## CircleButton

**Dependency**
Add this in your module's `build.gradle` file

    `compile 'com.thedesigncycle.ui:views:0.1'


**Usage**

**In layout**

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
|`icon`| Icon Drawable| `@drawable/ic_call`




**At runtime**

Imports

```java
import com.thedesigncycle.ui.CircleButton;
```


Create at runtime

```java
CircleButton myCircleButton = new CircleButton();
```


or fetch from layout

```java
CircleButton myCircleButton = (CircleButton) findViewById(R.id.my_circle_button);
```


Customize
```java
myCircleButton.setColor("#FF0000");

myCircleButton.setIcon(getResources().getDrawable(R.drawable.ic_list);
```


**Methods**

|Method| Description  |
|--|--|
| `setButtonColor(int Color)` | Set color of the button |
|`setIcon(Drawable icon)`| Set icon drawable |

