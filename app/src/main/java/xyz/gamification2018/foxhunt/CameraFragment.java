package xyz.gamification2018.foxhunt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
    private Context context;
    private FrameLayout cameraPreview;

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

        activity.findViewById(R.id.camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(final byte[] arg0, Camera arg1) {
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
                        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                        ((MainActivity)activity).photo = rotatedBitmap;
                        mCamera.release();
                        ((MainActivity)activity).openAnimalIdentify(v);
                    }
                });
            }
        });

        // set toolbar title
        activity.setTitle("Camera");
    }
}

