package com.example.roomdatabasebai1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<User> listUser;
    UserAdapter userAdapter;
    AppDatabase db;
    UserDao userDao;
    User obj = null;
    TextView tvThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "userdb")
                .allowMainThreadQueries()
                .build();
        userDao = (UserDao) db.userdao();
        listUser = userDao.getAll();
        userAdapter = new UserAdapter(listUser,this,R.layout.list_item);
        listView = findViewById(R.id.listView_1);
        listView.setAdapter(userAdapter);

        //Them
        tvThem = findViewById(R.id.txtThem);
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text =  tvThem.getText().toString();
                if(text.equalsIgnoreCase("") == false){
                    User u = new User(listUser.size(),text);
                    userDao.insertAll(u);
                    listUser = userDao.getAll();
                    userAdapter.changeList(listUser);
                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User u = listUser.get(i);
                tvThem.setText(u.getName());
                obj = u;
            }
        });
        Button btnRemove = findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDao.delete(obj);
                listUser = userDao.getAll();
                userAdapter.changeList(listUser);
                obj = null;
                tvThem.setText("");
            }
        });
    }
}