package com.example.educavial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class EscanearActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escanear);
        if (ActivityCompat.checkSelfPermission(EscanearActivity.this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(EscanearActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                PreviewView previewView = findViewById(R.id.previewView);
                ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                        ProcessCameraProvider.getInstance(this);
                cameraProviderFuture.addListener(() -> {
                    try {
                        ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                        Preview preview = new Preview.Builder().build();
                        ImageCapture imageCapture = new ImageCapture.Builder()
                                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                                .build();
                        CameraSelector cameraSelector = new CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build();
                        Camera camera = cameraProvider.bindToLifecycle(
                                ((LifecycleOwner) this),
                                cameraSelector,
                                preview,
                                imageCapture);
                        preview.setSurfaceProvider(
                                previewView.getSurfaceProvider());
                    } catch (InterruptedException | ExecutionException e) {
                    }
                }, ContextCompat.getMainExecutor(this));
            }
            else{
                Toast.makeText(this, "Esta aplicación necesita utilizar la cámara para fucionar.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}