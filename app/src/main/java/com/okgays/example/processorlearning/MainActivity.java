package com.okgays.example.processorlearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.okgays.annotation.DIActivity;
import com.okgays.annotation.DIView;

@DIActivity
public class MainActivity extends AppCompatActivity {

    @DIView(R.id.label)
    TextView label;
    @DIView(R.id.button)
    Button button;
    @DIView(R.id.listview)
    ListView listView;

    boolean useReflect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (useReflect) {
            DIUtil.bindView(this);
        } else {
            BindUtil.bindView(this);
        }

        label.setText("annotation test");
    }
}
