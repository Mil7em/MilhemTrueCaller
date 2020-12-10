package mlhm.mohammed.milhemtruecaller.Data.MyUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mlhm.mohammed.milhemtruecaller.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button btnsigninMain=findViewById(R.id.btnsigninMain);
        Button btnsignupMain=findViewById(R.id.btnSignUpMain);
        btnsigninMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,signin.class));
            }
        });
    }
}