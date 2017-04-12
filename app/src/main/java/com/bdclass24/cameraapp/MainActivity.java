package com.bdclass24.cameraapp;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import static com.bdclass24.cameraapp.R.id.prescriptionImage;

public class MainActivity extends AppCompatActivity {

    private Button takePhoto;
    private ImageView prescriptionImage;
    private int flag=0;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePhoto= (Button) findViewById(R.id.takePhoto);
        prescriptionImage= (ImageView) findViewById(R.id.prescriptionImage);

    }

    public void take_Photo(View view) {

        //code for taking photo
        if (flag==0){
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,5);
        }
        //code for saving photo
        else if (flag==1){
            savePhotoToMySdCard(bitmap);
            Toast.makeText(getApplicationContext(),"Save photo to SD card",Toast.LENGTH_SHORT).show();
            flag=0;
            takePhoto.setText("Take Photo");


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==5 && requestCode==RESULT_OK && data!=null){
         bitmap= (Bitmap) data.getExtras().get("data");
            prescriptionImage.setImageBitmap(bitmap);

            flag=1;
            takePhoto.setText("Save Photo");


        }

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void savePhotoToMySdCard(Bitmap bitmap){

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HH_mm_dd");
        String photoName=sdf.format(new Date());

        String root= Environment.getExternalStorageState().toString();
        File folder= new File(root+"/cameraApp");
        folder.mkdirs();

        File my_folder=new File(folder,photoName+".png");

        try{
            FileOutputStream stream=new FileOutputStream(my_folder);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
