package xyz.gamification2018.foxhunt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AnimalIdentifyFragment extends Fragment {

    Activity activity = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // returning layout
        return inflater.inflate(R.layout.fragment_animal_identify, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // set toolbar title
        activity = getActivity();
        assert activity != null;
        ((ImageView)activity.findViewById(R.id.imageIdentify)).setImageBitmap(((MainActivity)activity).photo);
        activity.setTitle("Animal Identify");
    }
}
