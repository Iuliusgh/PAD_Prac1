package com.example.educavial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Size;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class EscanearActivity extends AppCompatActivity {
        PreviewView previewView;
        Camera camera;
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
        ScaleGestureDetector scaleGestureDetector;
        ImageButton scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear);
        ActionBar actionBar =getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }
        if (ActivityCompat.checkSelfPermission(EscanearActivity.this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(EscanearActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        startCamera();
        scan=findViewById(R.id.scanButton);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analyze();
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
             startCamera();
            }
            else{
                Toast.makeText(this, "Esta aplicación necesita utilizar la cámara para fucionar.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void startCamera(){
        previewView = findViewById(R.id.previewView);
        cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().setTargetResolution(new Size(1920,1080)).build();
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();
                camera = cameraProvider.bindToLifecycle(
                        ((LifecycleOwner) this),
                        cameraSelector,
                        preview);

                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider());

            } catch (InterruptedException | ExecutionException e) {
            }
        }, ContextCompat.getMainExecutor(this));
        scaleGestureDetector=new ScaleGestureDetector(this, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(@NonNull ScaleGestureDetector detector) {
                float ratio=camera.getCameraInfo().getZoomState().getValue().getZoomRatio()*detector.getScaleFactor();
                camera.getCameraControl().setZoomRatio(ratio);
                return true;
            }

            @Override
            public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
                return true;
            }

            @Override
            public void onScaleEnd(@NonNull ScaleGestureDetector detector) {

            }
        });
        previewView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleGestureDetector.onTouchEvent(event);
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    camera.getCameraControl().startFocusAndMetering(
                    new FocusMeteringAction.Builder(previewView.getMeteringPointFactory().createPoint(event.getX(),event.getY()),FocusMeteringAction.FLAG_AF).
                            setAutoCancelDuration(5, TimeUnit.SECONDS).
                            build()
                    );
                }
                return true;
            }
        });
    }
    public void analyze() {
        ImageView imageView = findViewById(R.id.imageView2);
        int[] location = new int[2];
        imageView.getLocationInSurface(location);
        Bitmap bitmap = previewView.getBitmap();
        Bitmap senal = Bitmap.createBitmap(128,128, Bitmap.Config.ARGB_8888);
        int[] pixels= new int[128*128];
        bitmap.getPixels(pixels,0,128,location[0],location[1],128,128);
        senal.setPixels(pixels,0,128,0,0,128,128);
        detectarSeñal(senal);
    }

    private void detectarSeñal(Bitmap senal) {
        AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        View customLayout = getLayoutInflater().inflate(R.layout.escaneo_correcto_layout, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        ImageView imageView = customLayout.findViewById(R.id.imageView3);
        Canvas canvas =new Canvas(senal);
        imageView.draw(canvas);

    }
}