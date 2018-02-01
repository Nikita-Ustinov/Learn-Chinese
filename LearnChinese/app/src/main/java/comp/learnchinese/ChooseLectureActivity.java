package comp.learnchinese;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class ChooseLectureActivity extends AppCompatActivity {

//    CheckBox cbLecture1; CheckBox cbLecture2;  CheckBox cbLecture3;  CheckBox cbLecture4;
//    CheckBox cbLecture5; CheckBox cbLecture6;  CheckBox cbLecture7;  CheckBox cbLecture8;
//    CheckBox cbLecture9; CheckBox cbLecture10; CheckBox cbLecture11; CheckBox cbLecture12;
//    CheckBox cbLecture13; CheckBox cbLecture14; CheckBox cbLecture15; CheckBox cbLecture16;
//    CheckBox cbLecture17; CheckBox cbLecture18;  CheckBox cbLecture19;  CheckBox cbLecture20;
//    CheckBox cbLecture21; CheckBox cbLecture22;  CheckBox cbLecture23;  CheckBox cbLecture24;
//    CheckBox cbLecture25; CheckBox cbLecture26; CheckBox cbLecture27; CheckBox cbLecture28;
//    CheckBox cbLecture29; CheckBox cbLecture30;
    CheckBox[] lectureArray = new CheckBox[30];
    boolean[] isNeedToShowLecture = new boolean[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_lecture);
//        generateCheckBoxes();
//        lectureArray[0] = cbLecture1;
//        lectureArray[1] = cbLecture2; lectureArray[2] = cbLecture3; lectureArray[3] = cbLecture4;
//        lectureArray[4] = cbLecture5; lectureArray[5] = cbLecture6; lectureArray[6] = cbLecture7;
//        lectureArray[7] = cbLecture8; lectureArray[8] = cbLecture9; lectureArray[9] = cbLecture10;
//        lectureArray[10] = cbLecture11; lectureArray[11] = cbLecture12; lectureArray[12] = cbLecture13;
//        lectureArray[13] = cbLecture14; lectureArray[14] = cbLecture15; lectureArray[15] = cbLecture16;
//
//        lectureArray[16] = cbLecture17;
//        lectureArray[17] = cbLecture18; lectureArray[18] = cbLecture19; lectureArray[19] = cbLecture20;
//        lectureArray[20] = cbLecture21; lectureArray[21] = cbLecture22; lectureArray[22] = cbLecture23;
//        lectureArray[23] = cbLecture24; lectureArray[24] = cbLecture25; lectureArray[25] = cbLecture26;
//        lectureArray[26] = cbLecture27; lectureArray[27] = cbLecture28; lectureArray[28] = cbLecture29;
//        lectureArray[29] = cbLecture30;

        lectureArray[0] = (CheckBox)findViewById(R.id.checkBox1); lectureArray[1] = (CheckBox)findViewById(R.id.checkBox2);
        lectureArray[2] = (CheckBox)findViewById(R.id.checkBox3); lectureArray[3] = (CheckBox)findViewById(R.id.checkBox4);
        lectureArray[4] = (CheckBox)findViewById(R.id.checkBox5); lectureArray[5] = (CheckBox)findViewById(R.id.checkBox6);
        lectureArray[6] = (CheckBox)findViewById(R.id.checkBox7); lectureArray[7] = (CheckBox)findViewById(R.id.checkBox8);
        lectureArray[8] = (CheckBox)findViewById(R.id.checkBox9); lectureArray[9] = (CheckBox)findViewById(R.id.checkBox10);
        lectureArray[10] = (CheckBox)findViewById(R.id.checkBox11); lectureArray[11] = (CheckBox)findViewById(R.id.checkBox12);
        lectureArray[12] = (CheckBox)findViewById(R.id.checkBox13); lectureArray[13] = (CheckBox)findViewById(R.id.checkBox14);
        lectureArray[14] = (CheckBox)findViewById(R.id.checkBox15); lectureArray[15] = (CheckBox)findViewById(R.id.checkBox16);

        lectureArray[16] = (CheckBox)findViewById(R.id.checkBox17); lectureArray[17] = (CheckBox)findViewById(R.id.checkBox18);
        lectureArray[18] = (CheckBox)findViewById(R.id.checkBox29); lectureArray[19] = (CheckBox)findViewById(R.id.checkBox20);
        lectureArray[20] = (CheckBox)findViewById(R.id.checkBox21); lectureArray[21] = (CheckBox)findViewById(R.id.checkBox22);
        lectureArray[22] = (CheckBox)findViewById(R.id.checkBox23); lectureArray[23] = (CheckBox)findViewById(R.id.checkBox24);
        lectureArray[24] = (CheckBox)findViewById(R.id.checkBox25); lectureArray[25] = (CheckBox)findViewById(R.id.checkBox26);
        lectureArray[26] = (CheckBox)findViewById(R.id.checkBox27); lectureArray[27] = (CheckBox)findViewById(R.id.checkBox28);
        lectureArray[28] = (CheckBox)findViewById(R.id.checkBox29); lectureArray[29] = (CheckBox)findViewById(R.id.checkBox30);
    }

    public void btnBeginClick(View view) {

        for(int i=0; i<lectureArray.length-1; i++) {
            if(lectureArray[i].isChecked() == true) {
                isNeedToShowLecture[i] = true;
            }
            else
                isNeedToShowLecture[i] = false;
        }
        Intent intent = new Intent(this, ShowCharacter.class);
        intent.putExtra("lectures to show", isNeedToShowLecture);
        startActivity(intent);
    }

    public void btnBackClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    void generateCheckBoxes() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.checkboxLayout);

        CheckBox templ = new CheckBox(this);
        templ.setText("Lecture 1");
        CheckBox templ2 = new CheckBox(this);
        templ2.setText("Lecture 2");

        CheckBox templ3 = new CheckBox(this);
        templ2.setText("Lecture 3");

        CheckBox templ4 = new CheckBox(this);
        templ2.setText("Lecture 4");

        layout.addView(templ);
        layout.addView(templ2);
        layout.addView(templ3);
        layout.addView(templ4);
    }
}
