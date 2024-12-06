package com.example.mobile_programming_recipe_recommendations;

// MainActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements HorizontalImageAdapter.OnItemClickListener {
    private String username = "로그인에서 입력된 아이디를 받아올겁니다.";
    MakeUserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 로그인 액티비티로부터 사용자 이름을 받음
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        user = new MakeUserInfo(username); // << 로직 변경 User / UserManger 클래스 오류로 재조정 중 입니다. (완료)
        user.initUserInfo(username);

        TextView userNameTextView = findViewById(R.id.userNameTextView);
        userNameTextView.setText(username);

        // RecyclerView 설정
        RecyclerView recyclerView = findViewById(R.id.horizontalRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 이미지 리소스 배열 설정
        int[] images = {
                R.drawable.listpic1,
                R.drawable.piclst1,
                R.drawable.piclst2,
                R.drawable.piclst3
        };

        // Adapter 설정
        HorizontalImageAdapter adapter = new HorizontalImageAdapter(images, this);
        recyclerView.setAdapter(adapter);

        // PagerSnapHelper를 사용하여 한 번에 하나씩 스크롤하도록 설정
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        // 각 버튼 클릭 리스너 설정 - 내 냉장고 액티비티로 이동 시 사용자 정보 전달
        ImageButton button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecipeView.class); // 예정
                startActivity(intent);
            }
        });

        ImageButton button2 = findViewById(R.id.button2); // 내 냉장고 버튼
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMyRefrigerator = new Intent(MainActivity.this, MyRefrigerator.class);
                goToMyRefrigerator.putStringArrayListExtra("MyList", user.userMyRefrigerator);
                startForResult.launch(goToMyRefrigerator);
            }
        });

        ImageButton button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 구현 안하기로 한 로직
            }
        });

        ImageButton button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Recommend.class);
                startActivity(intent);
            }
        });

//        ImageButton button5 = findViewById(R.id.button5);
//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, <구현 안하기로 한 로직>.class);
//                startActivity(intent);
//            }
//        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // startActivityForResult는 API 30부터 Deprecated상태라고 하여 그 대신 쓸 수 있는 방법을 찾아서 아래와 같은 기능을 추가해줬습니다. << (추가 완료)
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
        if(result.getResultCode() == RESULT_OK) {
            Intent getData = result.getData();
            if (getData != null && getData.getStringArrayListExtra("UpdateList") != null) {
                user.userMyRefrigerator = getData.getStringArrayListExtra("UpdateList");
            }
        }
    });

    @Override
    public void onItemClick(int position) {
        String foodN = "음식 이름이 들어갈 예정";
        int foodIdx = -1;

        if(position == 0) {
            foodN = "바지락 칼국수";
            foodIdx = 0;
        }
        else if(position == 1) {
            foodN = "단팥죽";
            foodIdx = 3;
        }
        else if(position == 2) {
            foodN = "돼지고기 덮밥";
            foodIdx = 1;
        }
        else if(position == 3) {
            foodN = "소고기 떡국";
            foodIdx = 2;
        }

        Intent intentRecipic = new Intent(MainActivity.this, recipeActivity.class);
        intentRecipic.putExtra("foodIdx", foodIdx);
        intentRecipic.putExtra("foodName", foodN);
        startActivity(intentRecipic);
    }
}