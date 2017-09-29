package orgs.androidtown.tetris;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout container;
    Stage stage;
    Block block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 1. 화면(전체화면 status bar포함)의 가로세로 사이즈 가져오기
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int height = metrics.heightPixels;
        int width  = metrics.widthPixels;

        container  = (FrameLayout) findViewById(R.id.container);
        // 2. 그림을 그리기 위한 스테이지를 만든다
        stage      = new Stage(this, width, height, 10);

        // 3. 스테이지 안에서 돌아다니는 블럭을 만든다
        block = new Block(0, 0, stage.unit);

        // 4. 스테이지 안에 블럭을 삽입한다.
        stage.addBlock(block);

        // 5. 블럭이 삽입된 스테이지 전체를 화면에 반영한다.
        container.addView(stage);
    }

    public void up(View view){
        // 블럭의 좌표값만 변경
        block.moveUp();
        // 화면을 갱신
        //   -> 코드안에서 변경된 블럭을 그리기 때문에 화면이 움직이는 것 처럼 보인다
        stage.invalidate();
    }
    public void down(View view){
        block.moveDown();
        stage.invalidate();
    }
    public void right(View view){
        block.moveRight();
        stage.invalidate();
    }
    public void left(View view){
        block.moveLeft();
        stage.invalidate();
    }
}