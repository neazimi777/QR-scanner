package com.example.qrscanner2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MaterialButton openCamera;
    private TextView instructionText, detailsLinkText;
    private String[] permissions = new String[]{Manifest.permission.CAMERA};
    private static final int PERMISSION_REQ_CODE = 100;
    private static final int ACTIVITY_REQ_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        openCamera = findViewById(R.id.open_camera_btn);
        instructionText = findViewById(R.id.instruction_txt);
        detailsLinkText = findViewById(R.id.details_link_txt);

        openCamera.setOnClickListener(this);
        detailsLinkText.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.open_camera_btn:
                if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
                    openCamera();

                } else {
                    ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQ_CODE);
                }


                break;


            case R.id.details_link_txt:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detailsLinkText.getText().toString()));
                startActivity(intent);


                break;


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                openCamera();
            } else {
                ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQ_CODE);
            }


        }

    }

    private void openCamera() {
        Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
        startActivityForResult(intent, ACTIVITY_REQ_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_REQ_CODE && resultCode == RESULT_OK) {
            instructionText.setVisibility(View.VISIBLE);
            detailsLinkText.setText(data.getStringExtra(ScannerActivity.RESULT_KEY));


        }


    }
}