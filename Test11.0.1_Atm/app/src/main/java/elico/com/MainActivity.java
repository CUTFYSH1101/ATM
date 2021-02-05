package elico.com;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean logined = false;
    private int REQUEST_LOGIN = 100;
    private Function[] functions;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SetFunctions();
        SetLayout();

        // 當沒有登入卻按下返回鍵時，回到桌面
        if (!logined) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivityForResult(intent,REQUEST_LOGIN);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void SetFunctions() {
        functions = new Function[5];
        functions[0] = new Function(R.drawable.func_transaction,"交易紀錄");
        functions[1] = new Function(R.drawable.func_balance,"餘額查詢");
        functions[2] = new Function(R.drawable.func_finance,"投資理財");
        functions[3] = new Function(R.drawable.func_contacts,"聯絡人管理");
        functions[4] = new Function(R.drawable.func_exit,"離開");
    }

    // 創建Recycler新的顯示方法
    private class IconAdaper extends RecyclerView.Adapter<IconAdaper.IconHolder>{
        Context context = MainActivity.this;

        class IconHolder extends RecyclerView.ViewHolder{
            private ImageView imageView_Icon;
            private TextView textView_IconName;
            public IconHolder(@NonNull View itemView) {
                super(itemView);
                imageView_Icon = (ImageView) itemView.findViewById(R.id.imageView_IconImg);
                textView_IconName = (TextView) itemView.findViewById(R.id.textView_IconName);
            }
        }

        @NonNull
        @Override
        public IconHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.item_icon,
                    parent,false);
            return new IconHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IconHolder holder, final int position) {
            //  取得其中之一的物件
            final Function function = functions[position];
            //  設定每一格物件(holder)
            holder.imageView_Icon.setImageResource(function.getIcon());
            holder.textView_IconName.setText(function.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item_Clicked(function);
                }
            });

        }

        @Override
        public int getItemCount() {
            return functions.length;
        }
    }
    //  主畫面按鈕事件：關閉APP
    private void Item_Clicked(Function function) {
        Log.d(TAG, "SetFunctions: " + function.getName());
        switch (function.getIcon()){
            case R.drawable.func_balance:
                break;
            case R.drawable.func_contacts:
                break;
            case R.drawable.func_exit:
                finish();
                break;
            case R.drawable.func_finance:
                break;
            case R.drawable.func_transaction:
                break;
        }
    }

    // 設置Recycler
    /*
        兩種顯示模式：     GridLayoutManager or LinearLayoutManager
        兩種顯示layout：     only text       or  text + image
     */
    private void SetLayout() {
        //a1.Recycler
        RecyclerView recycler = findViewById(R.id.recycleView_Recycler);
        //系統內設置是否固定大小
        recycler.setHasFixedSize(true);
        //系統內設置排列方式使用預設
        recycler.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
//        recycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //a2.Adapter
        IconAdaper adapter = new IconAdaper();
//        FunctionAdapter adapter = new FunctionAdapter(MainActivity.this);
        recycler.setAdapter(adapter);
    }

    // 必須實作繼承
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果從Login頁面返回，但resultCode!=RESULT_OK，表示使用者按下返回鍵，尚未登入
        if (requestCode == REQUEST_LOGIN)
            if (resultCode != RESULT_OK)
                finish();
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