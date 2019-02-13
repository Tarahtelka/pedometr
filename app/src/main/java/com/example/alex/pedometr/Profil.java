package com.example.alex.pedometr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Profil extends Activity implements OnClickListener {

    EditText etWeight;
    EditText etHeight;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        etWeight = (EditText) findViewById(R.id.weight);
        etHeight = (EditText) findViewById(R.id.height);
        submit = (Button) findViewById(R.id.btnSubmit);
    }

    public void onClick(View v) {
        Intent intent = new Intent();
        if(etHeight.getText().toString()==""||etWeight.getText().toString()=="") {
            etHeight.setText("193");
            etWeight.setText("90");
            intent.putExtra("weight", etWeight.getText().toString());
            intent.putExtra("height", etHeight.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }else{
            intent.putExtra("weight", etWeight.getText().toString());
            intent.putExtra("height", etHeight.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}