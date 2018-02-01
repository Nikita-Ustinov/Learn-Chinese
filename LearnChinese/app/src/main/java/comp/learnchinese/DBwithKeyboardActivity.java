package comp.learnchinese;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public class DBwithKeyboardActivity extends AppCompatActivity implements View.OnClickListener{

    static final String TAG = "myTag";
    static int countPhoto = 0;
    Button btnSaveAndSnap;
    Button btnSave;
    Button btnExit;

//    LinkedList<Character> DBCharacters = new LinkedList<>();
    EditText etPinyin;
    EditText etCharacter;
    EditText etTranslation;
    EditText etLecture;
    private static final int PHOTO_INTENT_REQUEST_CODE = 100;
    MainActivity mainA = new MainActivity();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbwith_keyboard);

//        MainActivity.DBCharacters = deserialization();

        etPinyin = (EditText)findViewById(R.id.etPinyinKB);
        etCharacter = (EditText)findViewById(R.id.etCharacterKB);
        etTranslation = (EditText)findViewById(R.id.etTranslationKB);
        etLecture = (EditText)findViewById(R.id.etLectureKB);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainA.serializace(this);
    }



//    public  LinkedList<Character> deserialization() {
//        LinkedList<Character> output = new LinkedList<>();
//        try {
//            String path = this.getFilesDir()+"/DB.out";
//            FileInputStream fileIn = new FileInputStream(path);
//            ObjectInputStream in = new ObjectInputStream(fileIn);
//            output = (LinkedList<Character>) in.readObject();
//            in.close();
//            fileIn.close();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return output;
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSaveAndSnap : {
                saveNewCharacter();
                etCharacter.setText("");
                etTranslation.setText("");
                break;
            }
            case R.id.btnSave : {
                saveNewCharacter();
                break;
            }
            case R.id.btnExit: {
                mainA.serializace(this);
                finish();
                break;
            }
            case R.id.btnActualize: {
                try {
                    mainA.createDB(this);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    void saveNewCharacter() {
        String translation = etTranslation.getText().toString();
        if(translation.equals("")|| etCharacter.equals("")) {
            Toast toast = Toast.makeText(this, "Write something please!", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Character templ = new Character(etPinyin.getText().toString(), etCharacter.getText().toString(),
                    etTranslation.getText().toString(), etLecture.getText().toString());
            if(!MainActivity.isDBContains(templ)) {  //проверка если символ уже существует в базе данных
                MainActivity.DBCharacters.addLast(templ);
                mainA.serializace(this);
                Toast toast = Toast.makeText(this, "The sign was successfully saved.", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(this, "This sign is already exist in data set.", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
    }

}
