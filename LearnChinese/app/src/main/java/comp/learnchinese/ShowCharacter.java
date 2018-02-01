package comp.learnchinese;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

public class ShowCharacter extends AppCompatActivity {
//    LinkedList<Character> DB;
    LinkedList<Character> DBtoShow = new LinkedList<>();
    int count = 0;  //item number in DB
    TextView tvTranslationShow;
    TextView tvPinyinShow;
    TextView tvCharacter;
    TextView tvCountCharacter;
    boolean[] showLecture;
    String characterString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_character);
        tvPinyinShow = (TextView) findViewById(R.id.tvPinyinShow);
        tvTranslationShow = (TextView) findViewById(R.id.tvTranslationShow);
        tvCharacter = (TextView) findViewById(R.id.tvCharacter);
        tvCountCharacter = (TextView) findViewById(R.id.tvCountCharacter);

//        DB = deserialization();
        String path = null;

        Intent intent = getIntent();
        showLecture = intent.getBooleanArrayExtra("lectures to show");
        characterString = intent.getStringExtra("Character to show");

        if (characterString != null) {
            Character character = findCaracter(characterString);
            DBtoShow.addLast((Character)character);
        }
        else {
            for (int i = 0; i < showLecture.length; i++) {
                if (showLecture[i] == true) {
                    for (int j = 0; j < MainActivity.DBCharacters.size(); j++) {
                        if (Integer.parseInt(MainActivity.DBCharacters.get(j).Lecture) == (i + 1)) {
                            DBtoShow.addLast(MainActivity.DBCharacters.get(j));
                        }
                    }
                }
            }
        }

        if (DBtoShow.size() != 0) {
            DBtoShow = shuffleList(DBtoShow);
            tvCharacter.setText(DBtoShow.get(0).Character);
            tvCountCharacter.setText(1 + "/" + DBtoShow.size());
        } else {
            Toast.makeText(this, "You have no any characters to show", Toast.LENGTH_LONG);
            try {
                wait(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }



//        try {
//            bm = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        imageView.setImageBitmap(bm);
    }

    LinkedList<Character> deserialization() {
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

    public void btnKnow(View view) {
        count++;
        if(count > DBtoShow.size()-1) {
            Toast toast = Toast.makeText(this, "End of data set", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0 ,0);
            toast.show();
            finish();
        }
        else {
            tvPinyinShow.setText("");
            tvTranslationShow.setText("");
            tvCharacter.setText(DBtoShow.get(count).Character);
            tvCountCharacter.setText( (count+1) +"/" + DBtoShow.size());
        }
    }

    public void btnDontKnow(View view) {
        tvPinyinShow.setText(DBtoShow.get(count).Pinyin.toString());
        tvTranslationShow.setText(DBtoShow.get(count).Translation.toString());
    }


    LinkedList<Character> shuffleList(LinkedList<Character> list) {
        int elementNumber;
        Character templ;
        for(int i=0; i<DBtoShow.size()-1; i++) {
            elementNumber = (int)(Math.random()*(DBtoShow.size()-i-1));
            templ = list.get(DBtoShow.size()-i-1);
            list.set(DBtoShow.size()-i-1, list.get(elementNumber));
            list.set(elementNumber, templ);
        }
        return list;
    }

    Character findCaracter(String character) {
        Character result = null;
        for(int i=0; i<MainActivity.DBCharacters.size(); i++) {
            if(MainActivity.DBCharacters.get(i).Character.equals(character)) {
                return MainActivity.DBCharacters.get(i);
            }
        }
        return result;
    }
}
