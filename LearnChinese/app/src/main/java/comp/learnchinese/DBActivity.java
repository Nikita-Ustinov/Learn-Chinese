package comp.learnchinese;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class DBActivity extends AppCompatActivity implements View.OnClickListener{

    static final String TAG = "myTag";
    static int countPhoto = 0;
    Button btnSaveAndSnap;
    Button btnSave;
    Bitmap currentImage;
    Button btnExit;
    LinkedList<Character> DBCharacters = new LinkedList<>();
    EditText etPinyin;
    EditText etSound;
    EditText etTranslation;
    EditText etLecture;
    private static final int PHOTO_INTENT_REQUEST_CODE = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        DBCharacters = deserialization();
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, PHOTO_INTENT_REQUEST_CODE);
        try {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PHOTO_INTENT_REQUEST_CODE);
            }catch (Exception e) {}

        etPinyin = (EditText)findViewById(R.id.etPinyin);
        etSound = (EditText)findViewById(R.id.etSound);
        etTranslation = (EditText)findViewById(R.id.etTranslation);
        etLecture = (EditText)findViewById(R.id.etLecture);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serializace();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode== PHOTO_INTENT_REQUEST_CODE){
            Bitmap thumbnail=(Bitmap) data.getExtras().get("data");
            ImageView image=(ImageView) findViewById(R.id.imageView);
            image.setImageBitmap(thumbnail);
            currentImage = thumbnail;
        }
    }

    void serializace() {
        try {
            String path = this.getFilesDir()+"/DB.out";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(DBCharacters);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  LinkedList<Character> deserialization() {
        LinkedList<Character> output = new LinkedList<>();
        try {
            String path = this.getFilesDir()+"/DB.out";
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            output = (LinkedList<Character>) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveAndSnap : {
                saveNewCharacter();
//                Intent cameraIntent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, PHOTO_INTENT_REQUEST_CODE);
                break;
            }
            case R.id.btnSave : {
                saveNewCharacter();
                break;
            }
            case R.id.btnExit: {
                serializace();
                finish();
                break;
            }
        }
    }

    void saveNewCharacter() {
        String translation = etTranslation.getText().toString();
        if(translation.equals("")) {
            Toast toast = Toast.makeText(this, "Write translation please!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Character templ = new Character(etPinyin.getText().toString(), etSound.getText().toString(),
                    etTranslation.getText().toString(), etLecture.getText().toString(), currentImage, this.getFilesDir().toString()+"/"+DBCharacters.size()+".jpeg");
            DBCharacters.addLast(templ);
            Toast toast = Toast.makeText(this, "The sign was successfully saved.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
