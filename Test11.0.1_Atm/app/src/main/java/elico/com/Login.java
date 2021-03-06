package elico.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Level;
/*
可以連動Firebase進行登入，想要用新的帳號需要再學習firebase遠端新增檔案的code
當選中記住密碼，會返回最後一個執行login程式的帳號密碼到本地端(手機端)
下次再開啟軟體時，會記住密碼和勾選項狀態
 */
public class Login extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();
    //1.
    private EditText editText_account;
    private EditText editText_password;
    private CheckBox checkBox_PWRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
    }
    //設置預設值、獲取物件
    private void initialize() {
        //2.
        editText_account = findViewById(R.id.editTextText_AccountNumber);
        editText_password = findViewById(R.id.editText_Password);
        checkBox_PWRemember = findViewById(R.id.checkBox_PasswordRemembering);
        //9.當勾選時，本地設定記住最後一次輸入的帳號密碼
        //12.設定bool預設值為本地值或false
        checkBox_PWRemember.setChecked(getSharedPreferences("atm",MODE_PRIVATE)
                .getBoolean("REMEMBER_PASSWORD",false));
        checkBox_PWRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //10.變更資料庫值
                getSharedPreferences("atm",MODE_PRIVATE)
                        .edit()
                        .putBoolean("REMEMBER_PASSWORD",isChecked)
                        .commit();
            }
        });

        //8.如果本地(手機應用程式儲存空間)資料庫內已經有登入資料，則取得該user id, user password並設定其預設值
        String already_login_id = getSharedPreferences("atm",MODE_PRIVATE)
                .getString("USER_ID","");
        String already_login_password = getSharedPreferences("atm",MODE_PRIVATE)
                .getString("USER_PASSWORD","");
        editText_account.setText(already_login_id);
        editText_password.setText(already_login_password);
    }

    //按下button時執行一次
    public void login(View view){
        //3.
        final String input_id = editText_account.getText().toString();
        final String input_password = editText_password.getText().toString();
        //5.
        FirebaseDatabase
                .getInstance()
                .getReference("user")   //從user節點開始取得
                .child(input_id)            //帳號節點 -> data_id
                .child("password")          //密碼節點 -> data_password
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String data_password = (String) dataSnapshot.getValue();
                        if (input_password.equals(data_password))
                        {
                            //11.
                            boolean rememberPW = getSharedPreferences("atm",MODE_PRIVATE)
                                            .getBoolean("REMEMBER_PASSWORD",false);
                            if (rememberPW)
                            {
                                //7.複寫本地資料庫登入信息
                                getSharedPreferences("atm",MODE_PRIVATE)
                                        .edit()
                                        .putString("USER_ID",input_id)
                                        .putString("USER_PASSWORD",input_password)
                                        .commit();
                            }
                            //6.
                            //回傳登入成功
                            setResult(RESULT_OK);
                            //結束該畫面
                            finish();
                        }else{
                            //清空密碼
                            editText_password.setText("");
                            new AlertDialog.Builder(Login.this)
                                    .setTitle("登入結果")
                                    .setMessage("輸入密碼錯誤")
                                    .setPositiveButton("OK",null)
                                    .show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//4.
//        if (input_id.equals("Elico") && input_password.equals("0000"))
//        {
//            //回傳登入成功
//            setResult(RESULT_OK);
//            //結束該畫面
//            finish();
//        }
    }
    public void quit(View view){

    }
}