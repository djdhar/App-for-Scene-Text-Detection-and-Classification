package com.example.dj.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.opencv.android.BaseLoaderCallback;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Vector;

import static org.opencv.core.Core.FILLED;
import static org.opencv.core.Core.countNonZero;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.core.CvType.CV_8UC3;
import static org.opencv.core.CvType.CV_8UC4;
import static org.opencv.core.Mat.zeros;
import static org.opencv.imgproc.Imgproc.MORPH_CLOSE;
import static org.opencv.imgproc.Imgproc.MORPH_ELLIPSE;
import static org.opencv.imgproc.Imgproc.MORPH_GRADIENT;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;
import static org.opencv.imgproc.Imgproc.getStructuringElement;

//import ml.mcm.fhooe.at.androidwekaexample.R;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.*;





public class MainActivity extends AppCompatActivity  {

    // Used to load the 'native-lib' library on application startup.
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1889;



    static {
        System.loadLibrary("native-lib");
    }
    public static final String TAG = "src";

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.wtf(TAG, "OpenCV failed to load!");
        }
    }
    ImageView imageView;
    TextView textView;
    String path = new String("INVALID");

    private BaseLoaderCallback loaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case SUCCESS:
                    Log.i(TAG, "OpenCV loaded successfully");
                    //cameraView.enableView();
                    break;
                default:
                    super.onManagerConnected(status);
            }
        }

    };


    private Classifier mClassifier = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        //TextView tv = (TextView) findViewById(R.id.sample_text);
        //tv.setText(stringFromJNI());
        //Mat mat = new Mat();


        OpenCVLoader.initDebug();
        Mat mat;
        Rect rect;
        RotatedRect rotatedRect;
        Button upload = (Button)(findViewById(R.id.button));
        Button localize = (Button)(findViewById(R.id.localize));
        Button flip = (Button)findViewById(R.id.flip);
        final Button glcm =(Button)findViewById(R.id.glcm);
        textView =(TextView)findViewById(R.id.textView);
        imageView = (ImageView)findViewById(R.id.imageView);
        int NUMBER_OF_ATTRIBUTES = 41; // 4 + 1 class
        int NUMBER_OF_INSTANCES = 1;



        //final


        ; // Set class index





        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);

            }
        });
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Bitmap newBitMap = rotateImage(bitmap,90);
                imageView.setImageBitmap(newBitMap);
            }
        });
        glcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("In Progress...");
                //if(glcm.getBackground()==Color.BLACK)
                glcm.setBackgroundColor( Color.RED );
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                Mat fresh = new Mat();
                Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                Utils.bitmapToMat(bmp32, fresh);
                Mat input = new Mat();
                Imgproc.cvtColor(fresh, input, Imgproc.COLOR_RGB2GRAY);

                HashMap<Double,String> hm = new HashMap<Double,String>();
                hm.put((double)0,"chinese");
                hm.put((double)1,"english");

                Attribute Attribute40 = new Attribute("a0");
                 Attribute Attribute1 = new Attribute("a1");
                 Attribute Attribute2 = new Attribute("a2");
                Attribute Attribute3 = new Attribute("a3");
                Attribute Attribute4 = new Attribute("a4");
                 Attribute Attribute5 = new Attribute("a5");
                //Attribute Attribute4 = new Attribute("a4");
                 Attribute Attribute6 = new Attribute("a6");
                 Attribute Attribute7 = new Attribute("a7");
                 Attribute Attribute8 = new Attribute("a8");
                 Attribute Attribute9 = new Attribute("a9");
                 Attribute Attribute10 = new Attribute("a10");
                 Attribute Attribute11 = new Attribute("a11");
                 Attribute Attribute12 = new Attribute("a12");
                Attribute Attribute13 = new Attribute("a13");
                 Attribute Attribute14 = new Attribute("a14");
                 Attribute Attribute15 = new Attribute("a15");
                 Attribute Attribute16 = new Attribute("a16");
                 Attribute Attribute17 = new Attribute("a17");
                 Attribute Attribute18 = new Attribute("a18");
                 Attribute Attribute19 = new Attribute("a19");
                Attribute Attribute20 = new Attribute("a20");
                Attribute Attribute21 = new Attribute("a21");
                 Attribute Attribute22 = new Attribute("a22");
                 Attribute Attribute23 = new Attribute("a23");
                 Attribute Attribute24 = new Attribute("a24");
                 Attribute Attribute25 = new Attribute("a25");
                 Attribute Attribute26 = new Attribute("a26");
               Attribute Attribute27 = new Attribute("a27");
              Attribute Attribute28 = new Attribute("a28");
                Attribute Attribute29 = new Attribute("a29");
                Attribute Attribute30 = new Attribute("a30");
              Attribute Attribute31 = new Attribute("a31");
            Attribute Attribute32 = new Attribute("a32");
                Attribute Attribute33 = new Attribute("a33");
                 Attribute Attribute34 = new Attribute("a34");
                Attribute Attribute35 = new Attribute("a35");
               Attribute Attribute36 = new Attribute("a36");
                Attribute Attribute37 = new Attribute("a37");
              Attribute Attribute38 = new Attribute("a38");
               Attribute Attribute39 = new Attribute("a39");


               Attribute ClassAtrribute = new Attribute("a40");

                FastVector att = new FastVector(41);
                att.addElement(Attribute1);
                att.addElement(Attribute2);
                att.addElement(Attribute3);
                att.addElement(Attribute4);
                att.addElement(Attribute5);
                att.addElement(Attribute6);
                att.addElement(Attribute7);
                att.addElement(Attribute8);
                att.addElement(Attribute9);
                att.addElement(Attribute10);
                att.addElement(Attribute11);
                att.addElement(Attribute12);
                att.addElement(Attribute13);
                att.addElement(Attribute14);
                att.addElement(Attribute15);
                att.addElement(Attribute16);
                att.addElement(Attribute17);
                att.addElement(Attribute18);
                att.addElement(Attribute19);
                att.addElement(Attribute20);
                att.addElement(Attribute21);
                att.addElement(Attribute22);
                att.addElement(Attribute23);
                att.addElement(Attribute24);
                att.addElement(Attribute25);
                att.addElement(Attribute26);
                att.addElement(Attribute27);
                att.addElement(Attribute28);
                att.addElement(Attribute29);
                att.addElement(Attribute30);
                att.addElement(Attribute31);
                att.addElement(Attribute32);
                att.addElement(Attribute33);
                att.addElement(Attribute34);
                att.addElement(Attribute35);
                att.addElement(Attribute36);
                att.addElement(Attribute37);
                att.addElement(Attribute38);
                att.addElement(Attribute39);
                att.addElement(Attribute40);
                att.addElement(ClassAtrribute);

                Instances trainingSet = new Instances("xd",att,1);
                trainingSet.setClassIndex(40);
                Instance iExample = new DenseInstance(40);
                Vector<Double> vector = new Vector<>(Glcm.glcm(input));
                iExample.setValue(Attribute1,vector.get(0));
                iExample.setValue(Attribute2,vector.get(1));
                iExample.setValue(Attribute3,vector.get(2));
                iExample.setValue(Attribute4,vector.get(3));
                iExample.setValue(Attribute5,vector.get(4));
                iExample.setValue(Attribute6,vector.get(5));
                iExample.setValue(Attribute7,vector.get(6));
                iExample.setValue(Attribute8,vector.get(7));
                iExample.setValue(Attribute9,vector.get(8));
                iExample.setValue(Attribute10,vector.get(9));
                iExample.setValue(Attribute11,vector.get(10));
                iExample.setValue(Attribute12,vector.get(11));
                iExample.setValue(Attribute13,vector.get(12));
                iExample.setValue(Attribute14,vector.get(13));
                iExample.setValue(Attribute15,vector.get(14));
                iExample.setValue(Attribute16,vector.get(15));
                iExample.setValue(Attribute17,vector.get(16));
                iExample.setValue(Attribute18,vector.get(17));
                iExample.setValue(Attribute19,vector.get(18));
                iExample.setValue(Attribute20,vector.get(19));
                iExample.setValue(Attribute21,vector.get(20));
                iExample.setValue(Attribute22,vector.get(21));
                iExample.setValue(Attribute23,vector.get(22));
                iExample.setValue(Attribute24,vector.get(23));
                iExample.setValue(Attribute25,vector.get(24));
                iExample.setValue(Attribute26,vector.get(25));
                iExample.setValue(Attribute27,vector.get(26));
                iExample.setValue(Attribute28,vector.get(27));
                iExample.setValue(Attribute29,vector.get(28));
                iExample.setValue(Attribute30,vector.get(29));
                iExample.setValue(Attribute31,vector.get(30));
                iExample.setValue(Attribute32,vector.get(31));
                iExample.setValue(Attribute33,vector.get(32));
                iExample.setValue(Attribute34,vector.get(33));
                iExample.setValue(Attribute35,vector.get(34));
                iExample.setValue(Attribute36,vector.get(35));
                iExample.setValue(Attribute37,vector.get(36));
                iExample.setValue(Attribute38,vector.get(37));
                iExample.setValue(Attribute39,vector.get(38));
                iExample.setValue(Attribute40,vector.get(39));

               /* iExample.setValue(Attribute1,0);
                iExample.setValue(Attribute2,1);
                iExample.setValue(Attribute3,2);
                iExample.setValue(Attribute4,3);
                iExample.setValue(Attribute5,4);
                iExample.setValue(Attribute6,5);
                iExample.setValue(Attribute7,6);
                iExample.setValue(Attribute8,7);
                iExample.setValue(Attribute9,8);
                iExample.setValue(Attribute10,9);
                iExample.setValue(Attribute11,10);
                iExample.setValue(Attribute12,11);
                iExample.setValue(Attribute13,12);
                iExample.setValue(Attribute14,13);
                iExample.setValue(Attribute15,14);
                iExample.setValue(Attribute16,15);
                iExample.setValue(Attribute17,16);
                iExample.setValue(Attribute18,17);
                iExample.setValue(Attribute19,18);
                iExample.setValue(Attribute20,19);
                iExample.setValue(Attribute21,20);
                iExample.setValue(Attribute22,21);
                iExample.setValue(Attribute23,22);
                iExample.setValue(Attribute24,23);
                iExample.setValue(Attribute25,24);
                iExample.setValue(Attribute26,25);
                iExample.setValue(Attribute27,26);
                iExample.setValue(Attribute28,27);
                iExample.setValue(Attribute29,28);
                iExample.setValue(Attribute30,29);
                iExample.setValue(Attribute31,30);
                iExample.setValue(Attribute32,31);
                iExample.setValue(Attribute33,32);
                iExample.setValue(Attribute34,33);
                iExample.setValue(Attribute35,34);
                iExample.setValue(Attribute36,35);
                iExample.setValue(Attribute37,36);
                iExample.setValue(Attribute38,37);
                iExample.setValue(Attribute39,38);
                iExample.setValue(Attribute40,39);*/

                trainingSet.add(iExample);
                double prediction=0;
                try {
                    Classifier cls = (Classifier) weka.core.SerializationHelper.read(getAssets().open("GLCM.model"));
                    prediction = cls.classifyInstance(trainingSet.instance(0));
                    textView.setText(hm.get(prediction));
                }
                catch (Exception e){
                    textView.setText(e+"" + vector);
                }

                //textView.setText("GLCM"+Glcm.glcm(fresh));

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            path= new String(getRealPathFromURI(imageUri));
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                double heightTowidth = (double) (bitmap.getHeight()) / (double) (bitmap.getWidth());
                if (bitmap.getHeight() <= 1000 && bitmap.getWidth() <= 1000) {
                    imageView.setImageBitmap(bitmap);
                } else {

                    if (heightTowidth > 1) {
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (1000 / heightTowidth), 1000, false);
                        imageView.setImageBitmap(scaledBitmap);
                    }
                    if (heightTowidth <= 1) {
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 1000, (int) (1000 * heightTowidth), false);
                        imageView.setImageBitmap(scaledBitmap);
                    }

                }
                //imageUri.
                //imageView.setImageURI(imageUri);
                //if(imageView.getImageMatrix().)
                //scaleImage(imageView);


                Mat m = new Mat();
            }
            catch (Exception e){
                textView.setText(e+"");
            }

        }
    }
    public void Localize(View v){
        Toast.makeText(getBaseContext(),path,Toast.LENGTH_LONG);

        // Mat mat = new Mat();
        if(path.equals("INVALID")==false) {
            //Mat input =imageView.getImageMatrix();
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            Mat fresh = new Mat();
            //Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Utils.bitmapToMat(bitmap, fresh);


            //input = new Mat(fresh.size(),CV_8UC1);
           // Mat helper = new Mat(fresh.size(), fresh.type());

            Mat input = new Mat();
            Imgproc.cvtColor(fresh, input, Imgproc.COLOR_RGB2GRAY);
            Mat copy = input.clone();


            copy.convertTo(copy, -1, 1, -20);
            copy.convertTo(copy, -1, 2, 0);
           /* Imgcodecs.imwrite(path+"BNW.png",input);
            Bitmap bmImg = BitmapFactory.decodeFile(path+"BNW.png");
            imageView.setImageBitmap(bmImg);*/


            // try {
            Mat rgb = copy.clone();
            // Imgproc.cvtColor(copy, rgb, Imgproc.COLOR_RGB2GRAY);
            Mat grad = new Mat();
            Mat morphKernel = new Mat();
            morphKernel = getStructuringElement(MORPH_ELLIPSE, new Size(7, 7));
            Imgproc.morphologyEx(rgb, grad, MORPH_GRADIENT, morphKernel);

            Mat bw = new Mat();
            Imgproc.threshold(grad, bw, 0.0, 255.0, THRESH_BINARY | THRESH_OTSU);
            Mat connected = new Mat();
            morphKernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(20, 2));
            Imgproc.morphologyEx(bw, connected, MORPH_CLOSE, morphKernel);


            Mat mask = new Mat(bw.size(), CV_8UC3);
            mask.setTo(new Scalar(0, 0, 0));
            List<MatOfPoint> contours2 = new ArrayList<>();
            Mat hierarchy = new Mat();


            Imgproc.findContours(connected, contours2, hierarchy, Imgproc.RETR_CCOMP,
                    Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));


            for (int i = 0; i < contours2.size(); ++i) {

                Rect rect = Imgproc.boundingRect(contours2.get(i));
                MatOfPoint2f mp = new MatOfPoint2f();
                contours2.get(i).convertTo(mp, CvType.CV_32FC2);
                RotatedRect rrect = Imgproc.minAreaRect(mp);
                ;
                Mat maskROI = new Mat(mask, rect);
                maskROI.setTo(new Scalar(0, 0, 0));
                Imgproc.drawContours(mask, contours2, i, new Scalar(255, 255, 255), FILLED, 8, hierarchy, 2, new Point(0, 0));

                double r;
                try {
                    Mat dj = new Mat();
                    Imgproc.cvtColor(maskROI, dj, Imgproc.COLOR_RGB2GRAY);
                    r = (double) countNonZero(dj) / (rect.width * rect.height);
                    dj.release();
                } catch (Exception e) {
                    r = 1;
                }

                if (r > .25 && (rect.height > 20 && rect.width > 20)){// && (rect.height < 0.9 * input.rows() && rect.width < 0.9 * input.cols())) {
                    //  {
                    // Imgproc.rectangle(fresh, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
                    Point[] pts = new Point[4];
                    rrect.points(pts);
                    //DrawRotatedRectangle(A,rrect.center,rrect.size,rrect.angle);

                    for (int j = 0; j < 4; j++) {
                        Imgproc.line(fresh, new Point((int) pts[j].x, (int) pts[j].y), new Point((int) pts[(j + 1) % 4].x, (int) pts[(j + 1) % 4].y), new Scalar(0, 255, 0), 2);
                    }

                }
                maskROI.release();

            }


            mask.release();
            //helper.release();

            rgb.release();
            grad.release();
            morphKernel.release();
            //connected.release();
            bw.release();
            mask.release();
            hierarchy.release();
            input.release();

            Bitmap atlast = convertMatToBitMap(fresh);
            imageView.setImageBitmap(atlast);

        }
        else{
            Toast.makeText(getBaseContext(),path,Toast.LENGTH_LONG);
        }

    }
    // And to convert the image URI to the direct file system path of the image file
    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
    public void Localize(String path){
        Mat m = new Mat();
        m.release();

    }
    private static Bitmap convertMatToBitMap(Mat input){
        Bitmap bmp = null;
        Mat rgb = new Mat();
        Imgproc.cvtColor(input, rgb, Imgproc.COLOR_BGR2RGB);

        try {
            bmp = Bitmap.createBitmap(rgb.cols(), rgb.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(rgb, bmp);
        }
        catch (CvException e){
            Log.d("Exception",e.getMessage());
        }
        return bmp;
    }

    private void scaleImage(ImageView view) throws NoSuchElementException  {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {
            // Check bitmap is Ion drawable
            //bitmap = Ion.with(view).getBitmap();
        }

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(250);
        Log.i("Test", "original width = " + Integer.toString(width));
        Log.i("Test", "original height = " + Integer.toString(height));
        Log.i("Test", "bounding = " + Integer.toString(bounding));

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Log.i("Test", "xScale = " + Float.toString(xScale));
        Log.i("Test", "yScale = " + Float.toString(yScale));
        Log.i("Test", "scale = " + Float.toString(scale));

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        Log.i("Test", "scaled width = " + Integer.toString(width));
        Log.i("Test", "scaled height = " + Integer.toString(height));

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

        Log.i("Test", "done");
    }

    private int dpToPx(int dp) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }


    public native String stringFromJNI();
}
