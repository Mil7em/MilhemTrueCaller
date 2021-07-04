package mlhm.mohammed.milhemtruecaller.Data.MyUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mlhm.mohammed.milhemtruecaller.Data.MyUtils.MyProduct;
import mlhm.mohammed.milhemtruecaller.R;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button btnSave;
    private Spinner aSpinner;
    private EditText etItemName;
    private EditText etPrice;
    private EditText etAmount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //3. find view by id
        setContentView(R.layout.activity_addproduct);

         aSpinner=findViewById(R.id.aSpinner);
        aSpinner.setOnItemSelectedListener(this);

        btnSave = findViewById(R.id.btnSaveTask);
        etItemName =findViewById(R.id.etItemName);
        etAmount =findViewById(R.id.etAmount);
        etPrice=findViewById(R.id.etPrice);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readForm();
            }
        });
    }

    /**
     * this function checks if the user filed all the fields, so if he filled them
     * he can continue but if not the function returns error and he can't continue.
     */

    private void readForm() {
        String type = aSpinner.getSelectedItem().toString();
        String name = etItemName.getText().toString();
        String price=etPrice.getText().toString();
        String amount=etAmount.getText().toString();

        boolean isok=true;
        if(etItemName.length()==0)
        {
            isok=false;
            etItemName.setError("at least one char");

        }
        if(isok) {
            MyProduct myTask = new MyProduct();
            myTask.setType(type);
            myTask.setName(name);
            myTask.setAmount(Integer.parseInt(amount));
            myTask.setPrice(Double.parseDouble(price));


            saveTask(myTask);
        }

    }

    //5

    //6.3 request to save my task (firebase database)
    private void saveTask(MyProduct myTask){
        //1.
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        //2.
        DatabaseReference reference= database.getReference();
        //3.User id
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
        //4. My Object key
        String key=reference.child("All Products").push().getKey();
        //5.
        myTask.setKey(key);
        //6 actual storing
        reference.child("AllProducts").child(uid).child(key).setValue(myTask).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddProductActivity.this,"add successful",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(AddProductActivity.this,"add failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

        }

