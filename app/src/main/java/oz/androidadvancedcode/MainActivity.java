package oz.androidadvancedcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import oz.ozlibrary.OzLog;
import oz.ozlibrary.zxing.activity.CaptureActivity;
import oz.ozlibrary.zxing.encoding.EncodingHandler;

public class MainActivity extends AppCompatActivity
{

    private static final int RESULT_QR = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FinalActivity.initInjectedView(this);

        OzLog.log(this, "android advanced code");

        createQRCode();

    }


    @ViewInject(id = R.id.msg)
    TextView txtMsg;

    @ViewInject(id = R.id.qrcode)
    ImageView imgQRCode;

    @ViewInject(id = R.id.save)
    Button btnSave;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        switch (requestCode)
        {
            case RESULT_QR:

                String msg = data.getExtras().getString("result");

                txtMsg.setText(msg);

                return;

        }


    }


    private void createQRCode()
    {

        try
        {

            bmQRCOde = EncodingHandler.createQRCode("1234567890", 480);

            imgQRCode.setImageBitmap(bmQRCOde);

            btnSave.setEnabled(true);

        } catch (WriterException e)
        {
            e.printStackTrace();
        }


    }


    Bitmap bmQRCOde = null;

    String QRCODE_PATH = null;

    public void onSave(View view)
    {

        QRCODE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "QRCode" + File.separator;

        txtMsg.setText(QRCODE_PATH);

        File root = new File(QRCODE_PATH);

        if (!root.exists()) root.mkdirs();

        if(bmQRCOde == null) return;

        File file = new File(QRCODE_PATH, Base64.encode(new String(""+System.currentTimeMillis()).getBytes(), 0)+".png");

        try
        {

            FileOutputStream fos = new FileOutputStream(file);

            BufferedOutputStream bos = new BufferedOutputStream(fos);

            bmQRCOde.compress(Bitmap.CompressFormat.PNG, 99, bos);

            bos.flush();

            bos.close();

            OzLog.log(this, "保存完成");

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();


        if (id == R.id.action_qr)
        {

            this.startActivityForResult(new Intent(this, CaptureActivity.class), RESULT_QR);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
