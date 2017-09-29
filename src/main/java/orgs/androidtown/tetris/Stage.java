package orgs.androidtown.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by pc on 9/28/2017.
 */

public class Stage extends View {
    Paint paint;
    float stage_width;
    float stage_height;
    float unit;
    int unit_count;

    Block block = null;

    /**
     * stage 생성자
     *
     * @param context
     * @param width  화면 전체의 가로 사이즈
     * @param height 화면 전체의 세로 사이즈
     * @param count  분할하기 위한 화면의 개수
     */
    public Stage(Context context , int width, int height, int count) {
        super(context);
        stage_width = width;
        stage_height = height;
        unit_count = count;
        unit = stage_width / unit_count;

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
    }

    /**
     * stage에 블럭을 삽입하는 함수
     *
     * @param block
     */
    public void addBlock(Block block){
        this.block = block;
    }

    /**
     * invalidate 함수를 호출하면 화면 전체가 지워지고 onDraw 함수가 호출된다
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 배경의 격자를 그리는 코드
        drawBackground(canvas);
        // 블럭을 그리는 코드
        if(block != null) {
            drawBlock(canvas);
        }
    }

    /**
     * unit_count의 개수만큼 배경을 그린다
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas){
        for(int i=0 ; i<unit_count ;i++){
            for(int j=0 ; j<unit_count ; j++){
                canvas.drawRect(
                        unit * i         // 0  0   0
                        ,unit * j         // 0  50  100
                        ,unit * (i + 1)   // 50 50  50
                        ,unit * (j + 1)   // 50 100 150
                        ,paint);
            }
        }
    }

    /**
     * 블럭의 현재좌표를 이용해 그린다
     *
     * @param canvas
     */
    private void drawBlock(Canvas canvas){
        canvas.drawCircle(block.x, block.y, block.unit, block.paint);
    }
}


