package comp.learnchinese;


import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;

public class Character implements Serializable {
    private static int imageCounter = 0;
    public String Pinyin;
    public String Sounds;
    public String Character;
    public String Translation;
    public String Lecture;
    public String CharacterPhotoWay;

    public Character() {}

    public Character(String pinyin, String sounds, String translation, String lecture, Bitmap characterPhoto, String path ) {
        if(lecture == "") {
            lecture = "0";
        }
        if(sounds == "") {
            lecture = "";
        }
        if(pinyin == "") {
            lecture = "";
        }
        Pinyin = pinyin;
        Sounds = sounds;
        Translation = translation;
        Lecture = lecture;
        CharacterPhotoWay = saveBitmap(characterPhoto, path);
        imageCounter++;
    }

    public Character(String pinyin, String character, String translation, String lecture) {
        if(lecture == "") {
            lecture = "0";
        }
        if(character == "") {
            Character = "";
        }
        if(pinyin == "") {
            pinyin = "";
        }
        Pinyin = pinyin;
        Character = character;
        Translation = translation;
        Lecture = lecture;
        imageCounter++;
    }

    String saveBitmap(Bitmap image, String path) {
//        path += "/"+imageCounter+".jpeg";
        File file = new File(path);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }

}
