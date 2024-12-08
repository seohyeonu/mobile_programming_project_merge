package com.example.mobile_programming_recipe_recommendations;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class FindUserPasswordActivity extends AppCompatActivity {
    ArrayList<String> getUserList = new ArrayList<String>();
    int find_index = -1;

    private int is_changed_input = -1;
    private int is_ready_forwarding = -1;
    private int is_random_checkNum = -9999990;
    private int is_final_checkStatus = -1;
    ImageView phoneIcon, appearedCheckIcon, appearedPasswordIcon;
    ImageButton backButton, forwardingButton, appearedCheckButton;
    Button newUserPasswordButton;
    EditText inputID, inputPhoneNum, appearedInputCheckNum, appearedInputPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(FindUserPasswordActivity.this);
        setContentView(R.layout.find_user_password_activity);
        //
        Intent get_intent = getIntent();
        getUserList = get_intent.getStringArrayListExtra("getUserList");

        //선언해줘야 할 위젯들
        backButton = findViewById(R.id.backButton); // (완료)
        forwardingButton = findViewById(R.id.forwardingButton); // (완료)
        newUserPasswordButton = findViewById(R.id.new_user_password); // (완료)
        inputID = findViewById(R.id.input_id);  // (완료)
        phoneIcon = findViewById(R.id.smartphone_icon);
        inputPhoneNum = findViewById(R.id.input_phoneNum);

        // 인증번호 전송 후에 보여야 할 인증번호 기입 칸을 위해 위젯 선언 해주기
        appearedCheckIcon = findViewById(R.id.checkbox_icon);
        appearedInputCheckNum = findViewById(R.id.input_checkNum);
        appearedCheckButton = findViewById(R.id.checkButton);

        // 인증 완료가 되면 비밀번호 재설정을 위해 기입 칸이 보이도록하기 위해 위젯 선언 해주기
        appearedPasswordIcon = findViewById(R.id.password_icon);
        appearedInputPassword = findViewById(R.id.input_password);

        // 입력에서 무결성을 확인 해주기 위한 작업들 입니다.
        inputID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                is_changed_input = -1;
                is_random_checkNum = -9999990;
                appearedInputCheckNum.setText("");
                if(is_final_checkStatus == 0) {
                    is_final_checkStatus = -2;
                }
                else {
                    is_final_checkStatus = -1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(inputPhoneNum.getText().toString().length() != 0){
                    forwardingButton.setVisibility(View.VISIBLE);
                }
                else {
                    forwardingButton.setVisibility(View.GONE);
                }
                is_ready_forwarding = -1;
                is_random_checkNum = -9999990;
                is_changed_input = -1;
                appearedInputCheckNum.setText("");
                if(is_final_checkStatus == 0){
                    is_final_checkStatus = -2;
                }
                else {
                    is_final_checkStatus = -1;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(inputPhoneNum.getText().toString().length() == 11 && inputPhoneNum.getText().toString().matches("\\d+") == true){
                    is_ready_forwarding = 0;
                }
                else {
                    is_ready_forwarding = -1;
                }
            }
        });

        // 버튼에 대한 기능을 추가 해주기
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // 아이디 + 전화번호를 대조해서 해당 유저가 있는지 탐색하는 구조도 추가해줘야 함 (완료)
        forwardingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputID.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (is_ready_forwarding == -1) {
                    Toast.makeText(getApplicationContext(), "전화번호 입력이 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    find_index = -1;
                    for(int index = 0; index < getUserList.size(); index++){
                        String[] find_user_info = getUserList.get(index).split("\\|");
                        if(find_user_info[2].equals(inputID.getText().toString()) == true && find_user_info[1].equals(inputPhoneNum.getText().toString()) == true ){
                            find_index = index;
                            break;
                        }
                    }

                    if(find_index != -1){
                        Random randomNum = new Random();
                        is_random_checkNum = (randomNum.nextInt(9) +1) * 100000 + randomNum.nextInt(10) * 10000 + randomNum.nextInt(10) * 1000 +
                                randomNum.nextInt(10) * 100 + randomNum.nextInt(10) * 10 + randomNum.nextInt(10);
                        Toast.makeText(getApplicationContext(), String.valueOf(is_random_checkNum), Toast.LENGTH_LONG).show();

                        appearedCheckIcon.setVisibility(View.VISIBLE);
                        appearedInputCheckNum.setVisibility(View.VISIBLE);
                        appearedCheckButton.setVisibility(View.VISIBLE);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "해당 유저를 찾을 수가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                appearedInputCheckNum.setText("");
            }
        });
        appearedCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputID.getText().toString().length() == 0){
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (is_ready_forwarding == -1) {
                    Toast.makeText(getApplicationContext(), "전화번호 입력이 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(appearedInputCheckNum.getText().toString().equals(String.valueOf(is_random_checkNum)) == true) {
                    Toast.makeText(getApplicationContext(), "인증이 완료 됐습니다.", Toast.LENGTH_SHORT).show();
                    is_changed_input = 0;
                    is_final_checkStatus = 0;

                    inputID.setFocusableInTouchMode(false);
                    phoneIcon.setVisibility(View.GONE);
                    inputPhoneNum.setVisibility(View.GONE);
                    forwardingButton.setVisibility(View.GONE);

                    appearedInputCheckNum.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    appearedInputCheckNum.setTypeface(Typeface.DEFAULT); // 이걸 하니까 가려지는 알갱이 크기가 같아지네... (해결 완료)
                    appearedInputCheckNum.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
                    appearedInputCheckNum.setText("");
                    appearedInputCheckNum.setHint("비밀번호 재입력");

                    appearedCheckButton.setVisibility(View.GONE);

                    appearedPasswordIcon.setVisibility(View.VISIBLE);
                    appearedInputPassword.setVisibility(View.VISIBLE);
                }
                else if(is_changed_input == -1){
                    Toast.makeText(getApplicationContext(), "입력이 바뀌어 다시 '전송' 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "인증번호 입력이 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 새로운 비밀번호 적용해주는 작업을 해줘야 함 (완료)
        newUserPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(appearedInputPassword.getText().toString().length() != 0 && appearedInputPassword.getText().toString().equals(appearedInputCheckNum.getText().toString()) == true){
                    String[] get_user_info =  getUserList.get(find_index).split("\\|");
                    getUserList.set(find_index, String.valueOf(get_user_info[0] + "|" + get_user_info[1] + "|" + get_user_info[2] + "|" + appearedInputPassword.getText().toString()));

                    Toast.makeText(getApplicationContext(), "비밀번호가 변경 됐습니다.", Toast.LENGTH_SHORT).show();
                    // 여기다가 get_intent.putExtra() 해주고, 메인에 getExtraData()로 가져와서 리스트 업 해줘야 함!!!!! (완료)
                    get_intent.putStringArrayListExtra("UpdateList", getUserList);
                    setResult(RESULT_OK, get_intent);
                    finish();
                }
                else if(is_final_checkStatus != -1){
                    if(appearedInputPassword.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "새로 등록 할 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(appearedInputCheckNum.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "비밀번호 재입력 칸에 입력을 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(appearedInputPassword.getText().toString().equals(appearedInputCheckNum.getText().toString()) == false) {
                        Toast.makeText(getApplicationContext(), "입력된 비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(inputID.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "아이디를 입력 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(inputPhoneNum.getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "전화번호를 입력 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "인증이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.find_user_password_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}