package com.adiel.gogogicha;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

/**
 * Created by hp on 30/04/2018.
 */

public class BuyingActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener{

    private QRCodeReaderView qrCodeReaderView;
    private String result = "";
    private boolean isScaned = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrView);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        finish();
        result = text;
        String[] data = result.split("/");
        Intent intent = new Intent(this,RideActivity.class);
        for (int i=0 ;i<data.length;i++){
            Log.d("I",i+" "+data[i]);
        }
        if(data[2].equals("gogogicha.com")&&!isScaned){

            isScaned=true;
            if(data.length>3){
                finish();
                intent.putExtra("code",data[3]);
                String[] parse = data[3].split("_");
                if(parse[0].equals("drinks")){
                    intent = new Intent(this,PaymentActivity.class);
                    intent.putExtra("code",data[3]);
                }
                startActivity(intent);


            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }
}
