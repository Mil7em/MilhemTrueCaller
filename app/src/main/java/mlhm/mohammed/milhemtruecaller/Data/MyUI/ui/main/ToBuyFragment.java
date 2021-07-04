package mlhm.mohammed.milhemtruecaller.Data.MyUI.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mlhm.mohammed.milhemtruecaller.Data.MyUtils.MyProduct;
import mlhm.mohammed.milhemtruecaller.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToBuyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToBuyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView lstvToBuy;
    private ToBuyProductAdapter adapter;

    public ToBuyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ToBuyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ToBuyFragment newInstance(String param1, String param2) {
        ToBuyFragment fragment = new ToBuyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         adapter=new ToBuyProductAdapter(getContext());
        View view = inflater.inflate(R.layout.fragment_to_buy, container, false);
        lstvToBuy=view.findViewById(R.id.lstvToBuy);
        lstvToBuy.setAdapter(adapter);
        readTasksFromFirebase(null);
        return view;

    }
    public void readTasksFromFirebase(final String stTosearch)
    {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid=auth.getUid();
        DatabaseReference reference= database.getReference();
        reference.child("AllProducts").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    MyProduct t=d.getValue(MyProduct.class);
                    Log.d("MyProduct",t.toString());
                    if (stTosearch==null || stTosearch.length()==0)
                    {
                        if (t.isCompleted()==false)
                          adapter.add(t);
                    }
                    else
                        if (t.getName().contains(stTosearch))
                            if (t.isCompleted()==false)
                                adapter.add(t);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}