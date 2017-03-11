package com.example.fyy.printscreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.service.quicksettings.TileService;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


public class ScreenShootService extends TileService {


    @Override
    public void onClick() {
        super.onClick();
        Log.d(" --------------->", "onClick: ");
//        Intent intent = new Intent(this,screenShoot.class);
//        startActivity(intent);
        screenShoot.screenshoot.shot();
//       shot();
//        screenShoot ss = new screenShoot();
//        ss.shot();

    }


    public void shot(){
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/Pictures/Screenshots/" + now + ".jpg";

//            View view  = getApplicationContext().getWindow().getDecorView().getRootView();
            View view =  new View(getBaseContext()).getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }




    }

    public void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

}
