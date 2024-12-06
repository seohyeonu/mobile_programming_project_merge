package com.example.mobile_programming_recipe_recommendations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MakeUserInfo {
    // 생성자와 그 외 필요한 부분들 때문에 선언된 변수들 입니다.
    String userName;
    ArrayList<String> userMyRefrigerator;

    Integer[] imageID = {R.drawable.apple, R.drawable.brown_seaweed, R.drawable.cabbage, R.drawable.mayonnaise,
                        R.drawable.milk, R.drawable.oyster_mushroom, R.drawable.pea, R.drawable.spinach,
                        R.drawable.sweet_potato};

    // 생성자에 대한 정보입니다.
    public MakeUserInfo(String getUserName){
        userName = getUserName;
        userMyRefrigerator = new ArrayList<String>();
    }

    // 초기 유저들이 가지고 있는 내 냉장고의 재료 데이터를 어떻게 반영되는 지 보여드리고자 아래와 같은 함수를 만들게 됐습니다.
    public void initUserInfo(String getUserName){
        Date currentDate = new Date();
        Calendar plusDate = Calendar.getInstance();
        plusDate.setTime(currentDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY.MM.dd");

        if(getUserName.equals("susom02") == true){
            plusDate.add(Calendar.MONTH, 1);
            this.userMyRefrigerator.add(String.valueOf(imageID[2]) + "|" + dateFormat.format(plusDate.getTime()));
            plusDate.setTime(currentDate);

            plusDate.add(Calendar.MONTH, 1);
            this.userMyRefrigerator.add(String.valueOf(imageID[7]) + "|" + dateFormat.format(plusDate.getTime()));
            plusDate.setTime(currentDate);

            plusDate.add(Calendar.YEAR, 1);
            plusDate.add(Calendar.MONTH, 1);
            this.userMyRefrigerator.add(String.valueOf(imageID[3]) + "|" + dateFormat.format(plusDate.getTime()));
            plusDate.setTime(currentDate);

            plusDate.add(Calendar.DAY_OF_MONTH, 15);
            this.userMyRefrigerator.add(String.valueOf(imageID[4]) + "|" + dateFormat.format(plusDate.getTime()));
            plusDate.setTime(currentDate);
        }
        else if(getUserName.equals("testuserID") == true){
            plusDate.add(Calendar.MONTH, 1);
            this.userMyRefrigerator.add(String.valueOf(imageID[8]) + "|" + dateFormat.format(plusDate.getTime()));
            plusDate.setTime(currentDate);

            plusDate.add(Calendar.YEAR, 1);
            plusDate.add(Calendar.MONTH, 1);
            this.userMyRefrigerator.add(String.valueOf(imageID[1]) + "|" + dateFormat.format(plusDate.getTime()));
            plusDate.setTime(currentDate);

            plusDate.add(Calendar.MONTH, 1);
            this.userMyRefrigerator.add(String.valueOf(imageID[0]) + "|" + dateFormat.format(plusDate.getTime()));
            plusDate.setTime(currentDate);

            plusDate.add(Calendar.DAY_OF_MONTH, 15);
            this.userMyRefrigerator.add(String.valueOf(imageID[4]) + "|" + dateFormat.format(plusDate.getTime()));
            plusDate.setTime(currentDate);
        }
    }


}
