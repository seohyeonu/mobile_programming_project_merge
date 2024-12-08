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

public class JoinTheMembershipActivity extends AppCompatActivity {
    ArrayList<String> getUserList = new ArrayList<String>();
    String [] newUserInfo = new String[3];

    int find_index = -1;
    private int is_changed_input_page1 = -1;
    private int is_duplicate_check = -1;
    private int is_ready_forwarding = -1;
    private int is_random_checkNum = -9999990;
    private int is_final_checkStatus = -1;
    ImageView appearedCheckIcon, appearedPasswordIcon, changeIcon;
    ImageButton backButton, duplicate_checkButton, forwardingButton, appearedCheckButton;
    Button create_an_accountButton;
    EditText inputName_ID, inputPhoneNum, appearedinputPassword, appearedInputCheckNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(JoinTheMembershipActivity.this);
        setContentView(R.layout.join_the_membership_activity);
        //
        Intent get_intent = getIntent();
        getUserList = get_intent.getStringArrayListExtra("getUserList");

        //!! 로직 변경해야함... 이름 > 전환번호 > 인증번호 > 아이디 > 비번 > 계정 생성으로 짜야함...

        //선언해줘야 할 위젯들
        backButton = findViewById(R.id.backButton); // (완료)
        duplicate_checkButton = findViewById(R.id.duplicate_check); // (완료)
        forwardingButton = findViewById(R.id.forwardingButton); // (완료)
        create_an_accountButton = findViewById(R.id.create_an_account); // (완료)
        inputName_ID = findViewById(R.id.input_id);  // (완료)
        inputPhoneNum = findViewById(R.id.input_phoneNum);  // (완료)

        // 인증번호 전송 후에 보여야 할 인증번호 기입 칸을 위해 위젯 선언 해주기
        appearedCheckIcon = findViewById(R.id.checkbox_icon);
        appearedInputCheckNum = findViewById(R.id.input_checkNum);
        appearedCheckButton = findViewById(R.id.checkButton);

        // 인증이 완료되면 계정 생성을 위해서 비밀번호 기입 칸을 보이기 위해 위젯 선언 해주기
        appearedPasswordIcon = findViewById(R.id.password_icon);
        appearedinputPassword = findViewById(R.id.input_password);

        // 인증이 완료되면 전화번호 기입 칸을 없애주기 위해 위젯 선언 해주기
        changeIcon = findViewById(R.id.smartphone_icon);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 입력에 대한 무결성을 입증하기 위한 작업들 입니다.
        inputName_ID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(is_final_checkStatus == -2) {
                    if (inputName_ID.getText().toString().length() != 0) {
                        duplicate_checkButton.setVisibility(View.VISIBLE);
                    } else {
                        duplicate_checkButton.setVisibility(View.GONE);
                    }
                }
                else {
                    is_random_checkNum = -9999990;
                    is_changed_input_page1 = -1;
                }
                appearedInputCheckNum.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                is_duplicate_check = -1;
            }
        });
        appearedinputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                appearedInputCheckNum.setText("");
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
                if(is_final_checkStatus != -2) {
                    if (inputPhoneNum.getText().toString().length() != 0) {
                        forwardingButton.setVisibility(View.VISIBLE);
                    } else {
                        forwardingButton.setVisibility(View.GONE);
                    }
                    is_random_checkNum = -9999990;
                    is_changed_input_page1 = -1;
                    appearedInputCheckNum.setText("");
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

        // 버튼을 눌렀을 때 무결성이 깨졌는지 상시 체크할 수 있도록 해야겠구나.. (완료)
        // 메시지로 전송되는 인증번호는 Toast 메시지로 대체한 프로토타입으로 제작했습니다.
        forwardingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find_index = -1;
                for(int index = 0; index < getUserList.size(); index++){
                    String[] find_user_info = getUserList.get(index).split("\\|");
                    if(find_user_info[1].equals(inputPhoneNum.getText().toString()) == true){
                        find_index = index;
                        break;
                    }
                }

                if(inputName_ID.getText().toString().length() != 0 && is_ready_forwarding == 0 && find_index == -1){
                    Random randomNum = new Random();
                    is_random_checkNum = (randomNum.nextInt(9) +1) * 100000 + randomNum.nextInt(10) * 10000 + randomNum.nextInt(10) * 1000 +
                            randomNum.nextInt(10) * 100 + randomNum.nextInt(10) * 10 + randomNum.nextInt(10);
                    Toast.makeText(getApplicationContext(), String.valueOf(is_random_checkNum), Toast.LENGTH_LONG).show();

                    appearedCheckIcon.setVisibility(View.VISIBLE);
                    appearedInputCheckNum.setVisibility(View.VISIBLE);
                    appearedCheckButton.setVisibility(View.VISIBLE);

                    is_changed_input_page1 = 0;
                }
                else{
                    if(inputName_ID.getText().toString().length() == 0){
                        Toast.makeText(getApplicationContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(is_ready_forwarding == -1){
                        Toast.makeText(getApplicationContext(), "전화번호 입력이 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else if(find_index != -1){
                        Toast.makeText(getApplicationContext(), "이미 등록된 회원 입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                appearedInputCheckNum.setText("");
            }
        });
        // '확인' 버튼 동작에 대한 부분입니다.
        appearedCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_changed_input_page1 == 0 && is_ready_forwarding == 0){
                    if(appearedInputCheckNum.getText().toString().equals(String.valueOf(is_random_checkNum)) == true){
                        Toast.makeText(getApplicationContext(), "인증번호가 확인 되었습니다.", Toast.LENGTH_SHORT).show();
                        newUserInfo[0] = inputName_ID.getText().toString();
                        newUserInfo[1] = inputPhoneNum.getText().toString();
                        inputName_ID.setText("");
                        inputName_ID.setHint("아이디 입력 (중복확인 필수!)");

                        changeIcon.setImageResource(R.drawable.checkbox_icon);
                        inputPhoneNum.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        inputPhoneNum.setTypeface(Typeface.DEFAULT); // 이걸 하니까 가려지는 알갱이 크기가 같아지네... (해결 완료)
                        inputPhoneNum.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
                        inputPhoneNum.setText("");
                        inputPhoneNum.setHint("비밀번호 재입력");
                        forwardingButton.setVisibility(View.GONE);

                        appearedCheckIcon.setVisibility(View.GONE);
                        appearedInputCheckNum.setVisibility(View.GONE);
                        appearedCheckButton.setVisibility(View.GONE);

                        appearedPasswordIcon.setVisibility(View.VISIBLE);
                        appearedinputPassword.setVisibility(View.VISIBLE);

                        is_final_checkStatus = -2;
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if(inputName_ID.getText().toString().length() == 0){
                        Toast.makeText(getApplicationContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(is_changed_input_page1 == -1){
                        Toast.makeText(getApplicationContext(), "입력이 바뀌어 다시 '전송' 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(is_ready_forwarding == -1){
                        Toast.makeText(getApplicationContext(), "전화번호 입력이 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // '중복확인' 버튼 동작에 대한 부분입니다.
        duplicate_checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find_index = -1;
                for(int index = 0; index < getUserList.size(); index++){
                    String[] find_user_info = getUserList.get(index).split("\\|");
                    if(find_user_info[2].equals(inputName_ID.getText().toString()) == true){
                        find_index = index;
                        break;
                    }
                }

                if(find_index == -1){
                    Toast.makeText(getApplicationContext(), "사용해도 되는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    is_duplicate_check = 0;
                }
                else {
                    Toast.makeText(getApplicationContext(), "이미 사용 중인 아이디 입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*
        계정이 만들어지면서 생긴 유저 정보 List를 로그인의 메인화면으로 넘겨주는 작업을 해줘야 함. (완료)
            + 로그인 메인화면에서 startActivityforResult()로 넘어와서 넘겨주면 되겠다.
        */
        create_an_accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_duplicate_check == 0 && appearedinputPassword.getText().toString().length() != 0 && appearedinputPassword.getText().toString().equals(inputPhoneNum.getText().toString()) == true){
                    // << 리스트에 add() 작업을 해줘야 함. (완료)
                    getUserList.add(newUserInfo[0] + "|" + newUserInfo[1] + "|" + inputName_ID.getText().toString() + "|" + appearedinputPassword.getText().toString());
                    Toast.makeText(getApplicationContext(), "계정이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                    get_intent.putStringArrayListExtra("UpdateList", getUserList);
                    setResult(RESULT_OK, get_intent);
                    finish();
                }
                else if(is_final_checkStatus != -1){
                    if(inputName_ID.getText().toString().length() == 0){
                        Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(is_duplicate_check == -1){
                        Toast.makeText(getApplicationContext(), "아이디 중복확인을 눌러주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(appearedinputPassword.getText().toString().length() == 0){
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(inputPhoneNum.getText().toString().length() == 0){
                        Toast.makeText(getApplicationContext(), "비밀번호 재입력 칸에 입력을 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(appearedinputPassword.getText().toString().equals(inputPhoneNum.getText().toString()) == false){
                        Toast.makeText(getApplicationContext(), "입력된 비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(inputName_ID.getText().toString().length() == 0){
                        Toast.makeText(getApplicationContext(), "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(inputPhoneNum.getText().toString().length() == 0){
                        Toast.makeText(getApplicationContext(), "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "번호 인증을 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.join_the_membership_page), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}