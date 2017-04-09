package com.osdar.firebaseintegration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.osdar.firebaseintegration.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "firebaseProject";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
                case R.id.test:
                    return true;
            }
            return false;
        }

    };
    private DatabaseReference myRef;
    private AppCompatEditText mName;
    private AppCompatEditText mPass;
    private AppCompatButton mAddUser;
    private FirebaseDatabase databaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseInstance = FirebaseDatabase.getInstance();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initView();
        readFromDataBase();
    }

    private void readFromDataBase() {
        myRef = databaseInstance.getReference("users");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s :
                        dataSnapshot.getChildren()) {
                    Log.d("dataSnapshot", s.getValue(User.class).toString() + " ");
                }
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("dataSnapshot", dataSnapshot.getValue(User.class).toString() + " ");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("dataSnapshot", databaseError.getMessage()+ " ");
            }
        });
    }


    private void initView() {
        mName = (AppCompatEditText) findViewById(R.id.name);
        mPass = (AppCompatEditText) findViewById(R.id.pass);
        mAddUser = (AppCompatButton) findViewById(R.id.add_user);
        mAddUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_user) {
            addUserToDatabase();
        }
    }

    private void addUserToDatabase() {
        String userName = mName.getText().toString();
        String password = mPass.getText().toString();
        int random = (int) (Math.random() * 500000 + 1);
        if (!userName.isEmpty() && !password.isEmpty()) {
            writeNewUser("Id" + random, userName, password);
        } else {
            Toast.makeText(this, "Add user name and password!", Toast.LENGTH_SHORT).show();
        }

    }

    private void writeNewUser(String userId, String name, String password) {
        User user = new User(name, password);

        myRef.child(userId).setValue(user);
    }
}
