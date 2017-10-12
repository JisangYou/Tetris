# tetris
## 테트리스를 만들기 위한 기초개념
### 클래스 구성

- MainActivity class 및 레이아웃
- Block class
- Stage class



#### MainActivity class
```Java
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
        int width = metrics.widthPixels;

        container = (FrameLayout) findViewById(R.id.container);
        // 2. 그림을 그리기 위한 스테이지를 만든다
        stage = new Stage(this, width, height, 10);

        // 3. 스테이지 안에서 돌아다니는 블럭을 만든다
        block = new Block(0, 0, stage.unit);

        // 4. 스테이지 안에 블럭을 삽입한다.
        stage.addBlock(block);

        // 5. 블럭이 삽입된 스테이지 전체를 화면에 반영한다.
        container.addView(stage);
    }

    public void up(View view) {
        // 블럭의 좌표값만 변경
        block.moveUp();
        // 화면을 갱신
        //   -> 코드안에서 변경된 블럭을 그리기 때문에 화면이 움직이는 것 처럼 보인다
        stage.invalidate();
    }

    public void down(View view) {
        block.moveDown();
        stage.invalidate();
    }

    public void right(View view) {
        block.moveRight();
        stage.invalidate();
    }

    public void left(View view) {
        block.moveLeft();
        stage.invalidate();
    }
}
```

- 화면과 블럭을 그려주고, 블럭의 움직임 기능들이 있음.
- 블럭을 옮긴다는 생각이 아니라, 화면에 격자의 좌표에 그려준다는 생각으로 접근해야함.
```Java
DisplayMetrics metrics = getResources().getDisplayMetrics();
int height = metrics.heightPixels;
int width = metrics.widthPixels;
```
- 화면에 격자를 그려주는 초기단계! keypoint


#### Block class
```Java
public class Block {
    Paint paint;
    float unit;
    float x;
    float y;

    // 블럭은 기본 크기와 좌표를 가지고 생성된다.
    public Block(int x, int y, float unit){
        this.x = x;
        this.y = y;
        this.unit = unit;
        this.paint = new Paint();
        paint.setColor(Color.RED);
    }
    public void moveUp() {
        y = y - unit;
    }
    public void moveDown(){
        y = y + unit;
    }
    public void moveRight(){
        x = x + unit;
    }
    public void moveLeft(){
        x = x - unit;
    }
}
```
- 블럭 데이터들을 한곳에 모아놓음.

####Stage class

```Java
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
```

- 메인액티비티에서 받은 전체 사이즈 중에서 자신의 원하는 격자의 수만큼 그릴 수 있는 클래스
- 위에서 언급했던 것과 마찬가지로 unit이 움직인다라는 생각이 아니라, 클릭이벤트가 생길때마다 변하는 블럭의 좌표값(x,y)을 스테이지에서 그려준다는 생각으로 접근 해야함.
