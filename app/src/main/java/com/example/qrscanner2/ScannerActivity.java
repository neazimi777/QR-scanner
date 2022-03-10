package com.example.qrscanner2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScannerActivity extends AppCompatActivity {
    public static final String RESULT_KEY = "resultKey";
    private CodeScannerView codeScannerView;
    private CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        init();
    }

    private void init() {

        codeScannerView = findViewById(R.id.code_scanner_view);
        codeScanner = new CodeScanner(this, codeScannerView);
        codeScanner.setCamera(CodeScanner.CAMERA_FRONT);

        codeScanner.setDecodeCallback(decodeCallback);

    }

    @Override
    protected void onResume() {
        super.onResume();

        codeScanner.startPreview();

    }


    DecodeCallback decodeCallback = new DecodeCallback() {
        @Override
        public void onDecoded(@NonNull final Result result) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new AlertDialog.Builder(ScannerActivity.this)
                            .setTitle("توجه")
                            .setMessage("آیا میخواهید از اطلاعات این QR استفاده کنید؟")
                            .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.putExtra(RESULT_KEY, result.getText());

                                    setResult(RESULT_OK, intent);
                                    finish();


                                }
                            })
                            .setNegativeButton("خیر ، تلاش مجدد", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    codeScanner.startPreview();


                                }
                            })


                            .show();


                }
            });


        }
    };


}