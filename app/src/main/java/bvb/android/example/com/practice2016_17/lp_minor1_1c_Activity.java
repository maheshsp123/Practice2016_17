package bvb.android.example.com.practice2016_17;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class lp_minor1_1c_Activity extends AppCompatActivity {
    Course[] courses=new  Course[10];
    int courseCount=0;
    TextView subTitleTextViewRef;
    EditText CieEditTextRef, SeeEditTextRef,creditsEditTextRef;
    Button courseSubmitButtonRef, SGPButtonRef;
    TextView SgpResultTextViewRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lp_minor1_1c);

        subTitleTextViewRef=(TextView) findViewById(R.id.subTitleTextViewId);
        CieEditTextRef=(EditText) findViewById(R.id.CieEditTextid);
        SeeEditTextRef=(EditText) findViewById(R.id.SeeEditTextId);
        creditsEditTextRef=(EditText) findViewById(R.id.creditsEditTextId);
        courseSubmitButtonRef=(Button)findViewById(R.id.courseSubmitButtonId);
        SGPButtonRef=(Button)findViewById(R.id.SGPbuttonId);
        SgpResultTextViewRef=(TextView) findViewById(R.id.SgpResultTextViewId);

        courseSubmitButtonRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int credits, CIEmarks,SEEmarks;
                CIEmarks=Integer.parseInt( CieEditTextRef.getText().toString() );
                SEEmarks=Integer.parseInt( SeeEditTextRef.getText().toString() );
                credits=Integer.parseInt( creditsEditTextRef.getText().toString() );

                if(CIEmarks>50 || SEEmarks>50)
                {
                    Toast.makeText(lp_minor1_1c_Activity.this, "marks must be 50 and below", Toast.LENGTH_SHORT).show();
                    return;
                }
                courses[courseCount]=new Course(credits, CIEmarks,SEEmarks);
                courseCount++;
                //update the sub title
                subTitleTextViewRef.setText("Course "+(courseCount+1)+" Details:");
                //clear the text
                CieEditTextRef.setText(""); SeeEditTextRef.setText("");
                creditsEditTextRef.setText("");            }
        });

        SGPButtonRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double sgp;
                int numeo=0,denom=0;
                for(int i=0;i<courseCount;i++){
                    numeo=numeo+(courses[i].CIEmarks+courses[i].SEEmarks)*courses[i].credits;
                    denom=denom+courses[i].credits;
                }
                sgp=numeo/denom/10.0;
                SgpResultTextViewRef.setText("Your SGP= "+sgp);
            }
        });
    }
}

class Course
{
    int credits, CIEmarks,SEEmarks;
    Course(int credits,int CIEmarks,int SEEmarks){
        this.credits=credits; this.CIEmarks=CIEmarks; this.SEEmarks=SEEmarks;
    }
}
