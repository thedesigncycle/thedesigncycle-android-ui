package com.thedesigncycle.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CircleButton myCircleButton = findViewById(R.id.my_circle_button);
        // myCircleButton.setIcon(getResources().getDrawable(R.drawable.ic_list));
        // myCircleButton.setButtonColor(Color.parseColor("#FF5E5E"));
        myCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Click", "click");
            }
        });
    }
}
