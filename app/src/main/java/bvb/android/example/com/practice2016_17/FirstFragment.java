package bvb.android.example.com.practice2016_17;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    int mSelectedOption=0;
    Button mStudetnButtonRef, mStaffButtonRef;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_first, container, false);
        mStudetnButtonRef=(Button)rootView.findViewById(R.id.studetnButtonId);
        mStudetnButtonRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedOption=1;//means student button clicked
                ((callBack)getActivity()).luanchSecondFragmen(mSelectedOption);
            }
        });

        mStaffButtonRef=(Button)rootView.findViewById(R.id.staffButtonId);
        mStaffButtonRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedOption=2;//means staff button clicked
                ((callBack)getActivity()).luanchSecondFragmen(mSelectedOption);
            }
        });

        return rootView;
    }

    public interface callBack{
        public void luanchSecondFragmen(int selectedOption);
    }

}
