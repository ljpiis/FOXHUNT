package xyz.gamification2018.foxhunt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraFragment extends Fragment {

    Activity activity = null;

    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private Button capture, switchCamera;
    private Context context;
    private FrameLayout cameraPreview;
    private boolean cameraFront = false;
    public static Bitmap bitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // returning layout
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        assert activity != null;

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        context = getContext();

        mCamera =  Camera.open();
        mCamera.setDisplayOrientation(90);
        cameraPreview = (FrameLayout) activity.findViewById(R.id.cPreview);
        mPreview = new CameraPreview(context, mCamera);
        cameraPreview.addView(mPreview);

        mCamera.startPreview();

        // set toolbar title
        activity.setTitle("Camera");
    }
}

