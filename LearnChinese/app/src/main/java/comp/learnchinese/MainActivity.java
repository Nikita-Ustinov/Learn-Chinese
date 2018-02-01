package comp.learnchinese;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity  {
    static final String TAG = "myTag";
    EditText search;
    public static LinkedList<Character> DBCharacters = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search = findViewById(R.id.etSearch);
        DBCharacters = deserialization();
    }

    public void buttonSearchClick(View view) {
        Character target = null;
        if(search.getText().toString() == "") {
            Toast toast = Toast.makeText(this, "Search field is empty", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            target = findCaracter(search.getText().toString());
            if(target != null) {
                Intent intent = new Intent(this, ShowCharacter.class);
                intent.putExtra("Character to show", target.Character);
                startActivity(intent);
            }
        }
    }

    Character findCaracter(String character) {
        Character result = null;
        for(int i=0; i<DBCharacters.size(); i++) {
            if(DBCharacters.get(i).Character.equals(character)) {
                return DBCharacters.get(i);
            }
        }
        return result;
    }

    public void buttonStudyClick(View view) {
        Intent intent = new Intent(this, ChooseLectureActivity.class);
        startActivity(intent);
    }

    public void buttonDBClick(View view) {

        try {
            Intent intent = new Intent(this, DBwithKeyboardActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
        }
    }

    public void btnDelateDBClick(View view) {
        Toast.makeText(this, "Temporarly disabled function", Toast.LENGTH_LONG);
        LinkedList<Character> templ = new LinkedList<>();
        try {
            String path = this.getFilesDir()+"/DB.out";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(templ);
            out.close();
            fileOut.close();
            Toast toast = Toast.makeText(this, "The DB was successfully deleted.", Toast.LENGTH_SHORT);
            toast.show();
            DBCharacters = templ;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    // работа с базой данных

    public static boolean isDBContains(Character templ) {
        boolean isNotUnique = false;
        for(int i=0; i<MainActivity.DBCharacters.size(); i++) {
            if(compareCharacters(templ, MainActivity.DBCharacters.get(i)))
                isNotUnique = true;
        }
        return isNotUnique;
    }

    static boolean compareCharacters(Character input1, Character input2) {
        boolean isNotUnique = false;    //врзвращает true если элементы имеют одинаковый китайский символ
        if(input1.Character.equals(input2.Character))
            isNotUnique = true;
        return  isNotUnique;
    }

    public void createDB(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        boolean lectureFlag = false;
//        try(FileReader reader = new FileReader("lectures/1.txt"))
//        {
//            int c;
//            while((c=reader.read())!=-1){
//                input+=(char)c;
//                System.out.print((char)c);
//            }
//        }
//        catch(IOException ex){
//            System.out.println(ex.getMessage());
//            return;
//        }
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.lectures);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String [] stringArray = input.split("\n");
        Character templ = new Character();
        char [] lectureNumber ;
        String [] words;
        String trueLectureNumber = "";
        boolean isFirstLectureNumber = true;
        for(int i=0; i<stringArray.length; i++) {
//            int a =stringArray[i].length();
            if(stringArray[i].contains("null")) {
                stringArray[i] = stringArray[i].replace("null","");
            }
            if(stringArray[i].length()<3) {
                lectureNumber = stringArray[i].toCharArray();
                if(isFirstLectureNumber) {
                    trueLectureNumber = String.valueOf(lectureNumber[1]);
                    isFirstLectureNumber = false;
                }
                else {
                    try {
                        if(lectureNumber.length == 2) {
                            trueLectureNumber = String.valueOf(lectureNumber[0]);
                            trueLectureNumber += String.valueOf(lectureNumber[1]);
                        }
                        else {
                            trueLectureNumber = String.valueOf(lectureNumber[0]);
                        }

                    }catch (Exception e) {}
                }
            }
            else {
                words = stringArray[i].split("\\.");
                for(int j=0; j<words.length; j++) {
                    switch (j)  {
                        case 0: {
                            templ.Character = words[j];
                            break;
                        }
                        case 1: {
                            templ.Pinyin = words[j];
                            break;
                        }
                        case 2: {
                            templ.Translation = words[j];
                            break;
                        }
                    }
                }
            }

            if(templ.Translation != null) {
                templ.Lecture = trueLectureNumber;
                if(!isDBContains(templ))
                    DBCharacters.addLast(templ);
                templ = new Character();
            }
        }
        serializace(activity);
        Toast toast = Toast.makeText(this, "The DB was successfully actualized.", Toast.LENGTH_SHORT);
        toast.show();
    }

    String convertStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = is.read();
        while(i != -1) {
            baos.write(i);
            i = is.read();
        }
        return baos.toString();
    }

    String convertStreamToString(BufferedReader inBR) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        String i = inBR.readLine();
//        while(i != -1) {
//            baos.write(i);
//            i = inBR.readLine();
//        }
//        return baos.toString();

        String endFlag;
        String output = null;
        while((endFlag=inBR.readLine()) !=null) {
            output += endFlag+"\n";
            output += inBR.readLine()+"\n";
        }
        return output;
    }

    public LinkedList<Character> deserialization() {
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

    public void serializace(Activity activity) {
        try {
            String path = activity.getFilesDir()+"/DB.out";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(MainActivity.DBCharacters);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializace() {
//        DBwithKeyboardActivity help = new DBwithKeyboardActivity();
//        help.serializaceHelp();
        DBwithKeyboardActivity activity = new DBwithKeyboardActivity();
        try {
            String path = activity.getFilesDir()+"/DB.out";
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(MainActivity.DBCharacters);
            out.close();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
