package com.example.mobile_programming_recipe_recommendations;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{
    EditText inputID, inputPassword;
    Button loginButton;
    TextView getNewUser, findUserID, findUserPass;

    ArrayList<String> userList = new ArrayList<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_main);
        //
        /*
        원래는 DB를 써야 하지만 시간 관계상 프로토타입으로 List를 DB처럼 이용해서
        | 0. 로그인 | 1. 아이디 찾기 | 2. 비밀번호 찾기 | 3. 회원가입 |이 가능하도록 구현했습니다.
         */
        userList.add("testuserName|01090901234|testuserID|!password0909");
        userList.add("이재성|01099092772|susom02|@password02");

        inputID = findViewById(R.id.input_id);
        inputPassword = findViewById(R.id.input_password);

        // 아이디와 비번이 List에 있는 확인하는 로직 + putExtra() / startActivity() 추가해주면 됨. (완료)
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int find_index = -1;
                String[] find_user_info = new String[100];
                for(int index = 0; index < userList.size(); index++){
                    find_user_info = userList.get(index).split("\\|");
                    if(find_user_info[2].equals(inputID.getText().toString()) == true && find_user_info[3].equals(inputPassword.getText().toString()) == true ){
                        find_index = index;
                        break;
                    }
                }
                if (find_index != -1) {
                    Toast.makeText(getApplicationContext(), String.valueOf(find_user_info[2]) + " 님 어서오세요!", Toast.LENGTH_SHORT).show();
                    // 성제 화면으로 넘기는 작업만 추가하면 끝.
                    Intent goToMainActivity = new Intent(LoginActivity.this, /*이 부분만 성제의 메인 클래스 명으로 수정하면 끝납니다.*/MainActivity.class);
                    goToMainActivity.putExtra("username", inputID.getText().toString());
                    startForResult.launch(goToMainActivity);
                }
                else if (inputID.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (inputPassword.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // startActivityForResult()로 이름|전화번호|아이디" List<String>을 넘겨줘야 한다. (완료)
        findUserID = findViewById(R.id.find_user_ID);
        findUserID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputID.setText("");
                inputPassword.setText("");
                Intent goToFindUserIDActivity = new Intent(LoginActivity.this, FindUserIDActivity.class);
                goToFindUserIDActivity.putStringArrayListExtra("getUserList", userList);
                startForResult.launch(goToFindUserIDActivity);
            }
        });

        // startActivityForResult()로 "아이디|전화번호" List<String>을 넘겨줘야 한다. (완료)
        findUserPass = findViewById(R.id.find_user_Pass);
        findUserPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputID.setText("");
                inputPassword.setText("");
                Intent goToFindUserPasswordActivity = new Intent(LoginActivity.this, FindUserPasswordActivity.class);
                goToFindUserPasswordActivity.putStringArrayListExtra("getUserList", userList);
                startForResult.launch(goToFindUserPasswordActivity);
            }
        });

        // startActivityForResult()로 새로 만든 유저의 "이름|전화번호|아이디|비밀번호"를 받아서 List<String>에 add() 해주자. (완료)
        getNewUser = findViewById(R.id.get_new_user);
        getNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputID.setText("");
                inputPassword.setText("");
                Intent goToJoinTheMembershipActivity = new Intent(LoginActivity.this, JoinTheMembershipActivity.class);
                //여태까지 등록된 user의 아이디가 적힌 리스트를 보내준다. 그래야 중복확인 가능하기 때문이다.
                goToJoinTheMembershipActivity.putStringArrayListExtra("getUserList", userList);
                startForResult.launch(goToJoinTheMembershipActivity);
            }
        });
        //
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // startActivityForResult는 API 30부터 Deprecated상태라고 하여 그 대신 쓸 수 있는 방법을 찾아서 아래와 같은 기능을 추가해줬습니다.
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
        if(result.getResultCode() == RESULT_OK) {
            Intent getData = result.getData();
            if (getData != null) {
                userList = getData.getStringArrayListExtra("UpdateList");
            }
        }
    });
}