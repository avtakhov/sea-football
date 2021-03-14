package com.avtakhov.game.engine;

import com.avtakhov.game.game_objects.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class GameScreen implements Screen, ScreenInterface {
    private final float UPDATE_TIME = 1 / 30f;
    float timer = 0;

    private Stage stage;
    private OrthographicCamera camera;
    private Socket socket;
    Texture blue_ship;
    Texture red_ship;
    Texture main_shadow1;
    private Goal goalA;
    MainShip main;
    private String uid = "";
    RenderObject back;
    private int color;
    Ball ball;
    RenderObject arrow;
    HashMap<String, RenderObject> objects;
    BotShip bot;
    Gate gateLeft;
    Gate gateRight;
    ScoreBoard scoreBoard;
    Random random;

    public GameScreen(Game aGame) {
        random = new Random();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        stage = new Stage();
        red_ship = new Texture("red_ship.png");
        blue_ship = new Texture("blue_ship.png");
        main_shadow1 = new Texture("main_shadow.png");
        back = new RenderObject(new Texture("back.png"));
        int color = (int) (Math.random() * 2);
        main = new MainShip(color == 0 ? blue_ship : red_ship, camera);
        main.setColor(color);
        main.setBounds(camera.position.x, camera.position.y, 100, 40);
        back.setBounds(1000, 626, 2000, 1252);
        RenderObject backBack = new RenderObject(new Texture("back_back.png"));
        backBack.setBounds(1000, 626, 4000, 4000);
        stage.addActor(backBack);
        stage.addActor(back);
        stage.addActor(main);
        goalA = new Goal(new Texture("goal.png"));
        gateLeft = new Gate(new Texture("gate.png"));
        gateRight = new Gate(new Texture("gate1.png"));
        gateLeft.setBounds(24, back.getHeight() / 2, 48, 270);
        gateRight.setBounds(back.getWidth() - 24, back.getHeight() / 2, 48, 270);
        arrow = new RenderObject(new Texture("arrow.png"));
        ball = new Ball(new Texture("ball.png"));
        scoreBoard = new ScoreBoard(new Texture("score.png"));
        scoreBoard.setBounds(back.getWidth() / 2, back.getHeight() + 100, 600, 90);
        ball.setBounds(1000, 626, 40, 40);
        ball.setPosition(ball.getX(), ball.getY());
        //bot = new BotShip(new Texture("main.png"), ball);
        // bot.setBounds(camera.position.x - 50, camera.position.y - 50, 100, 40);
        //stage.addActor(bot);
        bot = new BotShip(new Texture("main.png"), ball);
        bot.setBounds(camera.position.x - 50, camera.position.y - 50, 128, 40);
        stage.addActor(bot);
        // Button homeButton = createButton(stage, "button.png", 0,
        //         back.getHeight() - 200, 450, 200);
        arrow.setBounds(main.getX(), main.getY(), 32, 32);
        stage.addActor(gateLeft);
        stage.addActor(gateRight);
        stage.addActor(arrow);
        stage.addActor(ball);
        stage.addActor(scoreBoard);
        //   stage.addActor(homeButton);
        objects = new HashMap<>();
        connectSocket();
        configSocketEvents();
    }

    public void connectSocket() {
        try {
            socket = IO.socket("http://467559-cs85334.tmweb.ru:8080");
            socket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int goal() {
        if (gateLeft.isGoal(ball)) {
            return -1;
        }
        if (gateRight.isGoal(ball)) {
            return 1;
        }
        return 0;
    }

    private void setStartPosition() {
        ball.setX(back.getWidth() / 2);
        ball.setY(back.getHeight() / 2);
        ball.setSpeedX(0);
        ball.setSpeedY(0);
        ball.setZ(0);
    }

    public void updateServer(float dt) {
        timer += dt;
        if (main != null && timer > UPDATE_TIME) {
            timer = 0;
            JSONObject data = new JSONObject();
            try {
                data.put("x", main.getX());
                data.put("y", main.getY());
                data.put("rot", main.getRotation());
                data.put("color", main.getColor1());
                int goal = goal();
                if (goal != 0) {
                    goalA.setBounds(main.getX(), main.getY(), 600, 600);
                    stage.addActor(goalA);
                    setStartPosition();
                    if (goal == 1) {
                        scoreBoard.setLEFT_SCORE(scoreBoard.getLEFT_SCORE() + 1);
                    }
                    if (goal == -1) {
                        scoreBoard.setRIGHT_SCORE(scoreBoard.getRIGHT_SCORE() + 1);
                    }
                }
                data.put("scoreLeft", scoreBoard.getLEFT_SCORE());
                data.put("scoreRight", scoreBoard.getRIGHT_SCORE());
                data.put("ballId", "");
                if (ball.isTouched()) {
                    ball.uid = uid;
                    data.put("ballId", uid);
                    ball.setTouched(false);
                }
                if (ball.uid.equals(uid)) {
                    data.put("ball", ball.getX() + " " + ball.getY() + " " + ball.getZ() + " " + ball.getSpeedX()
                            + " " + ball.getSpeedY() + " " + ball.getSpeedZ());
                } else {
                    data.put("ball", "");
                }
                socket.emit("playerMoved", data);
            } catch (JSONException e) {
                Gdx.app.log("SOCKET.IO", "Error");
            }
        }
    }

    public void configSocketEvents() {
        socket.on(Socket.EVENT_CONNECT, args -> {
            Gdx.app.log("SocketIO", "Connected");
        }).on("socketID", args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                String id = data.getString("id");
                uid = id;
                Gdx.app.log("SocketIO", "My ID: " + id);
            } catch (JSONException e) {
                Gdx.app.log("SocketIO", "Error getting ID");
            }
        }).on("newPlayer", args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                String id = data.getString("id");
                Gdx.app.log("SocketIO", "New Player Connect: " + id);
                RenderObject news = new RenderObject(objects.size() % 2 == 0 ? red_ship : blue_ship);
                news.setBounds(100, 100, 100, 40);
                objects.put(id, news);
                stage.addActor(news);
            } catch (JSONException e) {
                Gdx.app.log("SocketIO", "Error getting New PlayerID");
            }
        }).on("playerDisconnected", args -> {
            JSONObject data = (JSONObject) args[0];
            try {
                String id = data.getString("id");
                RenderObject a = objects.getOrDefault(id, new RenderObject(red_ship));
                a.remove();
            } catch (JSONException e) {
                Gdx.app.log("SocketIO", "Error getting disconnected PlayerID");
            }
        }).on("playerMoved", args -> {
            JSONObject data = (JSONObject) args[0];

            try {
                String id = data.getString("id");
                double x = data.getDouble("x");
                double y = data.getDouble("y");
                double rot = data.getDouble("rot");
                int color = data.getInt("color");
                int ls = data.getInt("scoreLeft");
                int rs = data.getInt("scoreRight");
                if (scoreBoard.getLEFT_SCORE() < ls) {
                    scoreBoard.setLEFT_SCORE(ls);
                }
                if (scoreBoard.getRIGHT_SCORE() < rs) {
                    scoreBoard.setRIGHT_SCORE(rs);
                }
                String bId = data.getString("ballId");
                if (bId.length() > 0) {
                    ball.uid = bId;
                }
                String bl = data.getString("ball");
                if (bl.length() > 0) {
                    String[] values = bl.split(" ");
                    ball.setX(Float.parseFloat(values[0]));
                    ball.setY(Float.parseFloat(values[1]));
                    ball.setZ(Float.parseFloat(values[2]));
                    ball.setSpeedX(Float.parseFloat(values[3]));
                    ball.setSpeedY(Float.parseFloat(values[4]));
                    ball.setSpeedZ(Float.parseFloat(values[5]));
                }
                if (objects.get(id) != null) {
                    objects.get(id).setPosition((float) x, (float) y);
                    objects.get(id).setRotation((float) rot);
                    // objects.get(id).set
                }
            } catch (JSONException ignored) {
            }
        }).on("getPlayers", args -> {
            JSONArray objects1 = (JSONArray) args[0];
            try {
                for (int i = 0; i < objects1.length(); i++) {
                    int color = objects1.getJSONObject(i).getInt("color");
                    RenderObject coopPlayer = new RenderObject(color == 0 ? blue_ship : red_ship);
                    Vector2 position = new Vector2();
                    position.x = ((Double) objects1.getJSONObject(i).getDouble("x")).floatValue();
                    position.y = ((Double) objects1.getJSONObject(i).getDouble("y")).floatValue();
                    coopPlayer.setBounds(position.x, position.y, 100, 40);
                    objects.put(objects1.getJSONObject(i).getString("id"), coopPlayer);
                    stage.addActor(coopPlayer);
                }
            } catch (JSONException ignored) {

            }
        });
    }

    private long start = System.currentTimeMillis();

    public void sleep(int fps) {
        if (fps > 0) {
            long diff = System.currentTimeMillis() - start;
            long targetDelay = 1000 / fps;
            if (diff < targetDelay) {
                try {
                    Thread.sleep(targetDelay - diff);
                } catch (InterruptedException ignored) {
                }
            }
            start = System.currentTimeMillis();
        }
    }

    @Override
    public void render(float delta) {
        sleep(60);
        Gdx.gl.glClearColor(0.28f, 0.45f, 0.67f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        Batch batch = stage.getBatch();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        stage.act();
        batch.end();

        for (Actor e : stage.getActors()) {
            if (!e.equals(back) && e instanceof RenderObject && !(e instanceof Bullet)) {

                if (e instanceof Ball) {
                    checkBounds((Ball) e);
                    continue;
                }
                checkBounds((RenderObject) e);
            }
            if (e instanceof Bullet) {
                ball.collide((Bullet) e);
                checkBounds(ball);
                if (((Bullet) e).toDelete) {
                    e.remove();
                }
            }
            if (e instanceof Goal) {
                if (((Goal) e).toDestroy()) {
                    ((Goal) e).setCounter(0);
                    e.remove();
                }
            }
            if (e instanceof MainShip) {
                ball.collide((MainShip) e);
                checkBounds(ball);
            }
        }
        //for (Map.Entry<String, RenderObject> renderObject : objects.entrySet()) {
        //    renderObject.getValue().act(Gdx.graphics.getDeltaTime());
        //}
        updateServer(Gdx.graphics.getDeltaTime());
        moveArrow();
        moveShip();
        moveBotShip();
    }

    private void moveShip() {
        checkBounds(main);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            main.move();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            main.rotateBy(-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            main.rotateBy(1);
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            createBullet(90, main);
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            createBullet(-90, main);
        }
    }

    private void moveBotShip() {
        //checkBounds(bot);

        //System.out.println(((ball.getX() - bot.getX()) * bot.getSpeedX() + (ball.getY() - bot.getY()) * bot.getSpeedY()));
        // TODO Math update
        if (((ball.getX() - bot.getX()) * bot.getSpeedX() + (ball.getY() - bot.getY()) * bot.getSpeedY()) < 0 || bot.getLastX() == bot.getX() &&
                bot.getLastY() == bot.getY()) {
            bot.rotateBy(10);
        } else {
            if (random.nextInt() % 27 == 0) {
                createBullet(90, bot);
                createBullet(-90, bot);
            }
        }
        bot.setLastX(bot.getX());
        bot.setLastY(bot.getY());
    }


    private void moveArrow() {
        double dist = Math.sqrt(Math.pow(ball.getX() - main.getX(), 2) + Math.pow(ball.getY() - main.getY(), 2));
        double cos = (ball.getX() - main.getX()) / dist;
        double sin = (ball.getY() - main.getY()) / dist;
        arrow.setPosition((float) (main.getX() + cos * main.getHeight() * 2),
                (float) (main.getY() + sin * main.getHeight() * 2));
        float degrees = (float) Math.toDegrees(Math.acos(cos));
        if (sin < 0) {
            degrees *= -1;
        }
        arrow.setRotation(degrees);
    }

    private void createBullet(float rotation, Ship ship) {
        Bullet bullet = new Bullet(new Texture("bullet1.png"));
        bullet.setBounds(ship.getX(), ship.getY(), 10, 10);
        bullet.setRotation(ship.getRotation() + rotation);
        bullet.setSpeedZ(0.5f);
        stage.addActor(bullet);
    }

    private void checkBounds(RenderObject object) {
        float newX = object.getX() + object.getSpeedX();
        float newY = object.getY() + object.getSpeedY();
        if (newX >= back.getWidth() || newX <= 0 || newY >= back.getHeight() || newY <= 0) {
            object.setX(object.getX() - object.getSpeedX());
            object.setY(object.getY() - object.getSpeedY());
        }
    }

    private void checkBounds(Ball object) {
        float newX = object.getX() + object.getSpeedX();
        float newY = object.getY() + object.getSpeedY();
        if (newX >= back.getWidth() || newX <= 0 || newY >= back.getHeight() || newY <= 0) {
            if (newX >= back.getWidth() || newX <= 0) {
                object.setSpeedX(-object.getSpeedX() / 2);
                object.setSpeedY(object.getSpeedY() / 2);
            } else {
                object.setSpeedX(object.getSpeedX() / 2);
                object.setSpeedY(-object.getSpeedY() / 2);
            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        red_ship.dispose();
        blue_ship.dispose();
    }
}