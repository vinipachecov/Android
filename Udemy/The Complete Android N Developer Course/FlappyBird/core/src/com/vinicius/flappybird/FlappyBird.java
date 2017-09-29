package com.vinicius.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.Random;

//About

//  Gdx.graphics.getWidth() /2 - birds[flapState].getWidth() / 2 ->
// half of the screeen height and half of the sprite height
// get the x coordinate for drawing in the center of the screen
//  Gdx.graphics.getHeight() /2 - birds[flapState].getHeight() /2 -> get the y coordinate for drawing in the center of the screen
// half of the screen width and half of the sprite width


public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	int flatState = 0;
	float birdY = 0;

//	how fast the bird will be moving
	float velocity = 0;

//	game control
	int gamestate = 0;

//	speed up the fall of the bird;
	float gravity = 2;

//	Tubes
	Texture topTube;
	Texture bottomTube;

//	gap between the tubes
	float gap = 400;

//	Tubes settings
	float maxTubeOffset;
	Random randomGenarator;

	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;




	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		birdY =  Gdx.graphics.getHeight() /2 - birds[0].getHeight() /2;

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		randomGenarator = new Random();

//		tube settings
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

		for (int i = 0; i < numberOfTubes ; i++) {

			tubeOffset[i] = (randomGenarator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + i * distanceBetweenTubes;

		}



	}


//			half the gap up and half the width of the tube from the center
	public void drawTubes(){

		for (int i = 0; i < numberOfTubes ; i++) {

//			off the edge of the screen
			if (tubeX[i] < - topTube.getWidth()) {

				tubeX[i] += numberOfTubes * distanceBetweenTubes;

			} else {

				tubeX[i] -=  tubeVelocity;
			}



			batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
			batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

		}

	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gamestate != 0) {


//			bird jump
			if (Gdx.input.justTouched()) {

				velocity = -30;

			}



			drawTubes();

//			avoid bird falling beyond the bottom of the screen
			if ( birdY  > 0  || velocity < 0 ) {

				velocity += gravity;
				birdY -= velocity;

			}


		}else {

			if (Gdx.input.justTouched()) {

				gamestate = 1;

			}
		}

		if (flatState == 0) {

			flatState = 1;
		} else {
			flatState = 0;
		}

		batch.draw(birds[flatState], Gdx.graphics.getWidth() / 2 - birds[flatState].getWidth() / 2, birdY);
		batch.end();
	}
}
