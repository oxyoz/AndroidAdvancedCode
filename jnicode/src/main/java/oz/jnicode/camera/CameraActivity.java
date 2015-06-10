package oz.jnicode.camera;


import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.IOException;

import oz.jnicode.R;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(contentView());

        init();

    }


    SurfaceView mSurfaceView;

    SurfaceHolder mHolder;

    Camera mCamera = null;

    private void init()
    {

        mHolder = mSurfaceView.getHolder();

        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }


    public View contentView()
    {

        FrameLayout root = new FrameLayout(this);

        mSurfaceView = new SurfaceView(this);

        root.addView(mSurfaceView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        TextView track = new TextView(this);

        track.setText("拍照");

        track.setGravity(Gravity.CENTER);

        track.setMinHeight(128);

        track.setBackgroundResource(R.drawable.sel_track);

        track.setOnClickListener(this);

        FrameLayout.LayoutParams trackParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        trackParams.gravity = Gravity.BOTTOM;

        root.addView(track, trackParams);

        return root;

    }


    @Override
    public void onClick(View v)
    {

        mCamera.takePicture(null, null, null, jpeg);

    }


    final Camera.ShutterCallback shutter = new Camera.ShutterCallback()
    {
        @Override
        public void onShutter()
        {

        }

    };


    final Camera.PictureCallback raw = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {

        }
    };

    final Camera.PictureCallback postview = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {

        }
    };


    final Camera.PictureCallback jpeg = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {

            Bundle bundle = new Bundle();

            bundle.putByteArray("bitmap", data);

            Intent intent = new Intent();

            intent.putExtras(bundle);

            setResult(Activity.RESULT_OK, intent);

            finish();
        }
    };


    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

        if (mCamera != null) return;

        mCamera = Camera.open();

        try
        {

            mCamera.setPreviewDisplay(mHolder);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

        mCamera.startPreview();

        mCamera.autoFocus(autoFocusCallback);

    }

    final Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback()
    {

        @Override
        public void onAutoFocus(boolean success, Camera camera)
        {


        }

    };


    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

        mCamera.stopPreview();

        mCamera.release();

        mCamera = null;

    }


}
