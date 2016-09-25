package bvb.android.example.com.practice2016_17;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LP_minor1_2c extends AppCompatActivity implements FirstFragment.callBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lp_minor1_2c);
    }

    public void luanchSecondFragmen(int selectedOption){
        Configuration configuration=getResources().getConfiguration();
        if(configuration.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            if(selectedOption==1) {//student button in FirstFragment is clicked
                Toast.makeText(LP_minor1_2c.this, "student fragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.secondFragConainerId, new StudentFragment()).commit();
            }
            else{//staff button in FirstFragment is clicked
                Toast.makeText(LP_minor1_2c.this, "staff fragment", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.secondFragConainerId, new StaffFragment()).commit();
            }
        }
        else{
            if(selectedOption==1){
                Intent intent=new Intent(this,Student_Deailed_Activity.class);
                startActivity(intent);
            }
            else{
                Intent intent=new Intent(this,Staff_Detailed_Activity.class);
                startActivity(intent);
            }
        }

    }
}
