package bvb.android.example.com.practice2016_17;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginDemoActivity extends AppCompatActivity {
    String LOG_CAT=loginDemoActivity.class.getSimpleName();
    UserCredentials[] userArray=new UserCredentials[5];
    UserCredentials temp=new UserCredentials("null","null",-1);
    EditText userNameED_ref;
    EditText passwordED_ref;
    Button submitButton_ref;
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
    final String myPrefenceFileName="PracticePreferences";
    final String rememberKey="Remember";
    final String usernameKey="UserName";
    final String passwordKey="PasswordKey";
    UserCredentials storedUser=new UserCredentials("null","null",-1);
    boolean storedUserFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_demo);
        //add dummy user credentials
         userArray[0]=new UserCredentials("aaa","aaa123",0);
         userArray[1]=new UserCredentials("bbb","bbb123",1);
         userArray[2]=new UserCredentials("ccc","ccc123",1);
         userArray[3]=new UserCredentials("ddd","ddd123",1);
         userArray[4]=new UserCredentials("eee","eee123",1);

        userNameED_ref=(EditText)findViewById(R.id.userNameTV);
        passwordED_ref=(EditText)findViewById(R.id.passwordTV);
        submitButton_ref=(Button)findViewById(R.id.submitBtn);


        submitButton_ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp.userName= userNameED_ref.getText().toString();
                temp.password= passwordED_ref.getText().toString();
                Log.i(LOG_CAT,""+temp.userName+" "+temp.password);
                boolean flag=false;
                for(UserCredentials itr:userArray)
                {
                    //if(itr.userName.equalsIgnoreCase("aaa") && itr.password.equalsIgnoreCase("aaa123"))
                    if(itr.userName.equalsIgnoreCase(temp.userName) && itr.password.equals(temp.password))
                    {
                        flag=true;
                        break;
                    }
                }
                if(flag==true) {
                    Log.i(LOG_CAT, "login successfull");
                    Toast.makeText(loginDemoActivity.this, "login successfull", Toast.LENGTH_SHORT).show();
                    editor.putString(usernameKey,temp.userName);
                    editor.putString(passwordKey, temp.password);
                    editor.commit();
                    storedUser.userName=sharedPreferences.getString(usernameKey,"nothing");
                    storedUser.password=sharedPreferences.getString(passwordKey,"nothing");
                    Log.i(LOG_CAT,"new user"+usernameKey+"="+storedUser.userName+" "+passwordKey+"="+storedUser.password);

                }
                else {
                    Log.i(LOG_CAT, "login failed");
                    Toast.makeText(loginDemoActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                }


            }
        });
        //shared preference
        sharedPreferences=getSharedPreferences(myPrefenceFileName, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(sharedPreferences.contains(usernameKey))
            if(sharedPreferences.contains(passwordKey));
            {

                storedUserFlag=true;
                storedUser.userName=sharedPreferences.getString(usernameKey,"");
                storedUser.password=sharedPreferences.getString(passwordKey,"");
                Log.i(LOG_CAT,usernameKey+"="+storedUser.userName+" "+passwordKey+"="+storedUser.password);

            }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(storedUserFlag){
            userNameED_ref.setText(sharedPreferences.getString(usernameKey,""));
            passwordED_ref.setText(sharedPreferences.getString(passwordKey,""));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clicked_item_Id=item.getItemId();
        if(clicked_item_Id==R.id.forget_data)
            Toast.makeText(loginDemoActivity.this, "data erased", Toast.LENGTH_SHORT).show();
        else if(clicked_item_Id==R.id.reset_m_item)
        {
            userNameED_ref.setText("");
            passwordED_ref.setText("");
            Toast.makeText(loginDemoActivity.this, "form reset", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
