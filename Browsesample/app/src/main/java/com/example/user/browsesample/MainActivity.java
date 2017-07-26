package com.example.user.browsesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
char[] neg;
    int count;
    public String s,s1;
    private static final int REQUEST_PICK_FILE = 1;
    private EditText passkey;

    private TextView filePath;
    private Button Browse;
    private File selectedFile;
    private TextView errors;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passkey=(EditText)findViewById(R.id.passkey);
        filePath = (TextView)findViewById(R.id.file_path);
        Browse = (Button)findViewById(R.id.browse);
        errors=(TextView)findViewById(R.id.errors);
        Browse.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.browse:
                Intent intent = new Intent(this, FilePicker.class);
                startActivityForResult(intent, REQUEST_PICK_FILE);
                break;
            case R.id.encrypt:
                try {
                    FileInputStream fin = new FileInputStream((String) filePath.getText());
                    BufferedInputStream bin = new BufferedInputStream(fin);
                    int i1, i2;
                    neg = new char[bin.available()];
                    s = "";
                    s1 = "";
                    StringBuilder sb = new StringBuilder();
                    count = 0;
                    int i3 = 0;
                    while ((i1 = bin.read()) != -1) {
                        if (i3 == passkey.getText().length()) ;
                        i3 = 0;
                        String fr= String.valueOf(passkey.getText());
                        char[] fr1=new char[Integer.parseInt(fr)];
                        i2 = 128 - (i1 + (int)fr1[i3]);
                        if (i2 < 0)
                            neg[count] = '1';
                        else
                            neg[count] = '0';
                        sb.append(neg[count]);
                        count++;
                        i3++;
                    }
                    s = sb.toString();
                    bin.close();
                    fin.close();
                    FileWriter writer = new FileWriter((String) filePath.getText());
                    BufferedWriter buffer = new BufferedWriter(writer);
                    buffer.write(s);
                    buffer.close();
                    writer.close();

                }
                catch(Exception e1)
                {
                    errors.setText("problem loading file");

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            switch(requestCode) {

                case REQUEST_PICK_FILE:

                    if(data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));
                        filePath.setText(selectedFile.getPath());
                    }
                    break;
            }
        }
    }
}

