package bvb.android.example.com.practice2016_17;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Frag","DetailedFrag: oncreateView() ");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed, container, false);
    }


}
