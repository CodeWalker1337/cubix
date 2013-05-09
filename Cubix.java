package cubix;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import cubix.entity.EntityAntiCube;
import cubix.entity.EntitiyPlayer;
import cubix.entity.EntityPulseCube;
import cubix.gui.GuiButton;
import cubix.gui.GuiScreen;
import cubix.gui.GuiSlider;
import cubix.gui.GuiSliderBase;
import cubix.gui.GuiSwitchDifficultyButton;

/**
 * [Cubix]
 * 
 * <michaelbehnerjava@hotmail.com>
 * 
 * Copyright(c) 2013, Michael Behner. All Rights Reserved.
 */
public class Cubix
{

	/** The width of the Display */
	public static final int WIDTH = 1000;

	/** The height of the Display */
	public static final int HEIGHT = 600;

	/** The version of the Game */
	public static final String version = "v1.42 ALPHA";

	/** The final declaration of the player */
	public static EntitiyPlayer player;

	/** Declaration of the spawning engine for the game */
	public static final SpawnEngine sEngine = new SpawnEngine(Difficulty.MEDIUM);

	/** Title screen Obj */
	public static GuiScreen titleScreen;
	/** Options screen Obj */
	public static GuiScreen optionsScreen;
	/** Gameover screen Obj */
	public static GuiScreen gameOverScreen;

	public static GuiButton buttonPlay;
	public static GuiButton buttonOptions;
	public static GuiSwitchDifficultyButton buttonDifficulty;
	public static GuiButton buttonOptionsBack;
	public static GuiSliderBase volumeSlider;

	public static SoundSystem soundSys = new SoundSystem();

	public static int score = 0;

	public static GameState gameState = GameState.MAIN_MENU;

	public Cubix()
	{

		// TODO: Scale speed of AntiCubes with difficulty
		// TODO: Write power ups code
		// TODO: Add More Options
		// TODO: Add Cheats

		initDisplay();
		initOpenGL();
		preInit();
		initEntities();
		postInit();

		while (!Display.isCloseRequested())
		{
			render();

			Display.update();
			Display.sync(60);

		}

		stopGame(true);

	}

	// =====================================================================
	// INITIALIZATION ======================================================

	public static void main(String[] args)
	{
		new Cubix();
	}

	private void initDisplay()
	{
		try
		{

			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();

		} catch (LWJGLException e)
		{
			System.err.println("Display could not be created: " + e.getStackTrace());
		}
	}

	private void preInit()
	{

	}

	private void initOpenGL()

	{
		glMatrixMode(GL_PROJECTION);
		glMatrixMode(GL_MODELVIEW);
		glOrtho(0, WIDTH, HEIGHT, 0, -1, 1);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

	}

	private void initEntities()
	{
		player = new EntitiyPlayer(WIDTH / 2, HEIGHT / 2, 20, 20, 4.0F);
	}

	private void postInit()
	{
		titleScreen = new GuiScreen(0, 0, WIDTH, HEIGHT, "Cubix_Title_Screen");
		optionsScreen = new GuiScreen(0, 0, WIDTH, HEIGHT, "Options_Screen");
		gameOverScreen = new GuiScreen(0, 0, WIDTH, HEIGHT, "Game_Over");
		buttonPlay = new GuiButton(WIDTH / 2 - 64, HEIGHT / 2 + 25, 128, 50, "playButton_static", "playButton_click", "playButton_hov");
		buttonOptions = new GuiButton(WIDTH / 2 - 64, HEIGHT / 2 + 100, 128, 50, "optionButton_static", "optionButton_click", "optionButton_hov");
		buttonOptionsBack = new GuiButton(WIDTH / 2 - 225, HEIGHT / 2 + 125, 128, 50, "backButton_static", "backButton_click", "backButton_hov");
		buttonDifficulty = new GuiSwitchDifficultyButton(WIDTH / 2 - 225, HEIGHT / 2 + 50, 128, 50, "", "", "");
		volumeSlider = new GuiSliderBase(WIDTH / 2 - 225, HEIGHT / 2 - 125, 512, 75, "sliderBase", new GuiSlider(WIDTH / 2 - 175, HEIGHT / 2 - 100, 64, 32, "slider_static", "slider_click", "slider_hov"), 100f);

		soundSys.playSound("bg_music");

	}

	// =====================================================================
	// =====================================================================

	// =====================================================================
	// RENDER ==============================================================

	private void render()
	{

		glClear(GL_COLOR_BUFFER_BIT);
		detectInput(gameState);

		/* Render code for MAIN_MENU */
		if (gameState == GameState.MAIN_MENU)
		{

			titleScreen.render();
			buttonPlay.render();
			buttonOptions.render();

			Display.setTitle("Cubix " + version);

		}
		/* Render code for OPTIONS */
		else if (gameState == GameState.OPTIONS)
		{

			optionsScreen.render();
			buttonDifficulty.render();

			volumeSlider.render();
			buttonOptionsBack.render();

			Display.setTitle("Cubix " + version);

		}
		/* Render code for GAME */
		else if (gameState == GameState.GAME)
		{

			player.render();

			score = score + (sEngine.getDifficultyMultiplier() * player.getScoreMultiplier());

			for (int i = 0; i < sEngine.getLoadedEntities().size() - 1; i++)
			{
				if (sEngine.getLoadedEntities().get(i) instanceof EntityPulseCube)
				{
					((EntityPulseCube) sEngine.getLoadedEntities().get(i)).pulse();
				}
				if (sEngine.getLoadedEntities().get(i) instanceof EntityAntiCube)
				{
					((EntityAntiCube) sEngine.getLoadedEntities().get(i)).smartMoveTo(player);
				}

				sEngine.getLoadedEntities().get(i).render();

			}

			Display.setTitle("Cubix " + version + "   Difficulty: " + sEngine.getDifficulty() + "       [Score: " + score + "] [Consumed: " + player.getAmountEaten() + "]");

		}
		/* Render code for PAUSED */
		else if (gameState == GameState.PAUSED)
		{

			return;
		}
		/* Render code for GAME_OVER */
		else if (gameState == GameState.GAME_OVER)
		{
			gameOverScreen.render();
		}

	}

	// =====================================================================
	// =====================================================================

	// =====================================================================
	// Texture =============================================================

	public static Texture loadTexture(String name)
	{
		try
		{
			return TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/images/" + name + ".png"));
		} catch (FileNotFoundException e)
		{
			System.err.println("File: '" + "res/images/" + name + ".png" + "' was not found.");
		} catch (IOException e)
		{
			System.err.println("Generic IO Exception has occured: " + e.getStackTrace());
		}
		return null;
	}

	// =====================================================================
	// =====================================================================

	public static void stopGame(boolean absolute)
	{
		if (absolute)
		{
			Display.destroy();
			System.exit(0);
		} else
		{
			sEngine.stopSpawning();
			sEngine.clearEntities();
			setGameState(GameState.GAME_OVER);
		}

	}

	public static void setGameState(GameState state)
	{
		gameState = state;
	}

	public static void startGame()
	{

		setGameState(GameState.GAME);
		sEngine.startSpawning();

	}

	public void restartGame()
	{
		titleScreen.reload();
		gameOverScreen.reload();
		optionsScreen.reload();

		buttonPlay.reload();
		buttonOptions.reload();

		player.setDestroyed(false);
		player.respawn();
		sEngine.setDefaultSpawnRate();
		Cubix.score = 0;

		setGameState(GameState.MAIN_MENU);

	}

	/** Is called in main render loop. Listens for keys. */
	private void detectInput(GameState gameState)
	{

		boolean moveUp = Keyboard.isKeyDown(Keyboard.KEY_W);
		boolean moveDown = Keyboard.isKeyDown(Keyboard.KEY_S);
		boolean moveLeft = Keyboard.isKeyDown(Keyboard.KEY_A);
		boolean moveRight = Keyboard.isKeyDown(Keyboard.KEY_D);
		boolean esc = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
		boolean enter = Keyboard.isKeyDown(Keyboard.KEY_RETURN);
		boolean back = Keyboard.isKeyDown(Keyboard.KEY_BACK);
		boolean pause = Keyboard.isKeyDown(Keyboard.KEY_PAUSE);

		/* MAIN_MENU GAMESTATE */
		if (gameState == GameState.MAIN_MENU)
		{
			if (buttonPlay.isClicked())
			{
				unloadScreen(titleScreen);
				startGame();
			}

			if (buttonOptions.isClicked())
			{
				unloadScreen(titleScreen);
				loadScreen(optionsScreen);
			}

		}

		/* ======================== */

		/* OPTIONS GAMESTATE */
		if (gameState == GameState.OPTIONS)
		{

			if (esc || back)
			{

				unloadScreen(optionsScreen);
				loadScreen(titleScreen);

			}

			if (buttonDifficulty.isClicked())
			{
				buttonDifficulty.setSelectedIndex(1);
			}

			if (buttonOptionsBack.isClicked())
			{
				unloadScreen(optionsScreen);
				loadScreen(titleScreen);
			}
		}
		/* ======================== */

		/* GAME GAMESTATE */
		if (gameState == GameState.GAME)
		{
			if (moveUp)
			{
				player.moveVertical(-1);

			}
			if (moveDown)
			{
				player.moveVertical(1);
			}

			if (moveLeft)
			{
				player.moveHorizontal(-1);
			}
			if (moveRight)
			{
				player.moveHorizontal(1);
			}

			if (pause)
			{
				setGameState(GameState.PAUSED);
			}

		}
		/* ======================== */

		/* PAUSE GAMESTATE */
		if (gameState == GameState.PAUSED)
		{
			if (Keyboard.isKeyDown(Keyboard.KEY_RETURN))
			{
				setGameState(GameState.GAME);
			}
		}
		/* ======================== */

		/* GAME_OVER GAMESTATE */
		if (gameState == GameState.GAME_OVER)
		{
			if (esc)
			{
				loadScreen(gameOverScreen);
				restartGame();
			}
		}
		/* ======================== */

	}

	private void loadScreen(GuiScreen s)
	{
		if (s.equals(titleScreen))
		{
			titleScreen.reload();
			buttonPlay.reload();
			buttonOptions.reload();
			setGameState(GameState.MAIN_MENU);
		}
		if (s.equals(optionsScreen))
		{
			optionsScreen.reload();
			buttonDifficulty.reload();
			buttonOptionsBack.reload();
			volumeSlider.reload();
			setGameState(GameState.OPTIONS);
		}
		if (s.equals(gameOverScreen))
		{
			gameOverScreen.reload();
			setGameState(GameState.GAME_OVER);

		}
	}

	private void unloadScreen(GuiScreen s)
	{
		if (s.equals(titleScreen))
		{
			titleScreen.hide();
			buttonPlay.hide();
			buttonOptions.hide();
		}
		if (s.equals(optionsScreen))
		{
			optionsScreen.hide();
			buttonDifficulty.hide();
			buttonOptionsBack.hide();
			volumeSlider.hide();
		}
		if (s.equals(gameOverScreen))
		{
			gameOverScreen.hide();

		}
	}

	static TrueTypeFont font2;

	public static void drawString()
	{
		try
		{

			InputStream inputStream = ResourceLoader.getResourceAsStream("res/myfont.ttf");

			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);

			awtFont2 = awtFont2.deriveFont(50f); // set font size

			font2 = new TrueTypeFont(awtFont2, false);

		} catch (Exception e)
		{

			e.printStackTrace();

		}
	}
}