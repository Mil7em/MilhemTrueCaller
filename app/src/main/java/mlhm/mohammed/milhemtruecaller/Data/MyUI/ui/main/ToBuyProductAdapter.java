package mlhm.mohammed.milhemtruecaller.Data.MyUI.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mlhm.mohammed.milhemtruecaller.Data.MyUI.AddProductActivity;
import mlhm.mohammed.milhemtruecaller.Data.MyUtils.MyProduct;
import mlhm.mohammed.milhemtruecaller.R;

public class ToBuyProductAdapter extends ArrayAdapter<MyProduct> {
    public ToBuyProductAdapter(@NonNull Context context) {
        super(context, R.layout.toby_items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vproduct = LayoutInflater.from(getContext()).inflate(R.layout.toby_items, parent, false);
        TextView Name1 = vproduct.findViewById(R.id.tvName);
        TextView Type1 = vproduct.findViewById(R.id.tvType);
        TextView Amount1 = vproduct.findViewById(R.id.tvAmount);
        TextView Price1 = vproduct.findViewById(R.id.tvPrice);
        Button Delete1 = vproduct.findViewById(R.id.btnDelete);
        CheckBox BuyIt = vproduct.findViewById(R.id.cbBuyIt);
        final MyProduct myProduct = getItem(position);
        BuyIt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            /**
             * This function checks if the user has put V on the product, so if he put it, the function
             * saves this product in the firebase automatically.
             *
             */
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    myProduct.setCompleted(isChecked);
                    updateProduct(myProduct);
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference reference = database.getReference();
//                    FirebaseAuth auth = FirebaseAuth.getInstance();
//                    reference.child("AllProducts").child(uid)

            }
        });
        Delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delProduct(myProduct);
                

            }
        });
        Name1.setText(myProduct.getName());
        Type1.setText(myProduct.getType());
        Amount1.setText(myProduct.getAmount()+"");
        Price1.setText(myProduct.getPrice()+"");


        return vproduct;
    }
    private void updateProduct(MyProduct myProduct){
        //1.
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        //2.
        DatabaseReference reference= database.getReference();
        //3.User id
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
        //4. My Object key
       // String key=reference.child("AllProducts").push().getKey();
        //5.
        //myProduct.setKey(key);
        //6 actual storing
        reference.child("AllProducts").child(uid).child(myProduct.getKey()).setValue(myProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"update successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"update failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });

    }
    private void delProduct(MyProduct myProduct){
        //1.
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        //2.
        DatabaseReference reference= database.getReference();
        //3.User id
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid=auth.getCurrentUser().getUid();
        //4. My Object key
        // String key=reference.child("AllProducts").push().getKey();
        //5.
        //myProduct.setKey(key);
        //6 actual storing
        reference.child("AllProducts").child(uid).child(myProduct.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"delete successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"delete failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });

    }
}

