package ukdw.ac.id.smart_ticketing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

/**
 * Created by hp on 23/04/2018.
 */

public class ScanActivity extends AppCompatActivity {
    private SurfaceView cameraView;
    private TextView barcodeInfo;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        cameraView = (SurfaceView)findViewById(R.id.svCamera);
        barcodeInfo = (TextView)findViewById(R.id.txvScan);

        barcodeDetector = new BarcodeDetector.Builder(this) .setBarcodeFormats(Barcode.QR_CODE) .build();

        cameraSource = new CameraSource .Builder(this, barcodeDetector) .setRequestedPreviewSize(640, 480) .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    cameraSource.start(cameraView.getHolder());
                }catch (Exception e) {
                    Log.e("CAMERA SOURCE", e.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray barcodes = detections.getDetectedItems();
                if(barcodes.size()!=0){
                    barcodeInfo.post(new Runnable() {
                        @Override
                        public void run() {
                            barcodeInfo.setText(barcodes.valueAt(0).toString());
                        }
                    });
                }
            }
        });
    }


}
