package com.xiaoyuw.fileexplorer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;


public class MainActivity extends Activity {

    private Button bt;
    private TextView tv;
    private String file;
    private String directory;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt=(Button) findViewById(R.id.btExplore);
        tv=(TextView) findViewById(R.id.tvFile);

        bt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv.setText("");
                        Uri uri=null;
                         directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                        System.out.println(directory);
                        try {
                            File f =new File(directory);
                            uri =Uri.fromFile(f);
                            Intent intent =new Intent(Intent.ACTION_PICK);
                            intent.setDataAndType(uri,"image/*");
                            startActivityForResult(intent, 1);

                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }

                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode==Activity.RESULT_OK) {
            switch (requestCode){
                case 1:
                    Uri uri = data.getData();
                    Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    for (int i = 0; i < cursor.getColumnCount(); i++) {
                        System.out.println(i + "-" + cursor.getColumnName(i) + "-" + cursor.getString(i));
                    }

                    path = cursor.getString(1);

                    tv.setText(path);
            }

        }else{
            Toast.makeText(this,"You did not select a file.",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
