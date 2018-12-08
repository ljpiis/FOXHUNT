package xyz.gamification2018.foxhunt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class ProfileFragment extends Fragment {

    Activity activity = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return layout file
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        assert activity != null;
        activity.setTitle("Profile");
        if (((MainActivity)activity).scrollDown) {
            final ScrollView scrollView = activity.findViewById(R.id.scrollProfile);

            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, activity.findViewById(R.id.headerProfileTeams).getTop());
                }
            });
        }
    }
}
