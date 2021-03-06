package me.it.hes.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class Main {

	public static int width = 1200;
	public static int height = width*9/16;
	
	public static int pix_width = 800;
	public static int pix_height = 450;

	public static boolean running = false;

	public static long window;
	private GLFWKeyCallback keyCallback;
	private GLFWWindowSizeCallback sizeCallback;
	private GLFWMouseButtonCallback mouseCallback;
	private GLFWScrollCallback scrollCallback;
	
	private Game game;

	// BASE
	public void start() {
		running = true;
		run();
	}

	private void init() {
		if (glfwInit() != GL_TRUE) {
			System.err.println("Could not initialize GLFW!");
			return;
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_SAMPLES, 1);

		OSSpecifics.GLFWSpecifics();
		window = glfwCreateWindow(width, height, "HeS", NULL, NULL);
		if (window == NULL) {
			System.err.println("Could not create GLFW window!");
			return;
		}
		ByteBuffer FBW = BufferUtils.createByteBuffer(4), FBH = BufferUtils.createByteBuffer(4); 
		glfwGetFramebufferSize(window, FBW, FBH);
		pix_width = FBW.getInt(0);
		pix_height = FBH.getInt(0);

		InitWindow();
		InitGL();
		// if(!SteamAPI.init(System.getProperty("user.dir"))) {
		// System.err.println("Erreur Steam");
		// }
	}

	public void run() {
		init();
		game = new Game();

		long lastTime = System.nanoTime();
		double delta = 0.0;
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1.0) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
		keyCallback.release();
		sizeCallback.release();
		mouseCallback.release();
		scrollCallback.release();
		// SteamAPI.shutdown();
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		game.Render();
		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.out.println("Error " + error);
		glfwSwapBuffers(window);
	}

	public static float mouseX, mouseY;
	private DoubleBuffer mx = BufferUtils.createDoubleBuffer(1),my = BufferUtils.createDoubleBuffer(1);
	
	private void update() {
		game.Update();
		glfwPollEvents();
		glfwGetCursorPos(window, mx, my);
		mouseX = (float)mx.get(0);
		mouseY = (float)my.get(0);
	}

	private void InitGL() {
		GL.createCapabilities();
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		glEnable(GL_BLEND);
		glEnable(GL20.GL_POINT_SPRITE);
		glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE);
		glEnable(GL13.GL_MULTISAMPLE);
		glViewport(0, 0, pix_width, pix_height);
	}

	private void InitWindow() {
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		glfwSetKeyCallback(window, keyCallback = new Input());
		glfwSetWindowSizeCallback(window, sizeCallback = new ResizeHandler());
		glfwSetMouseButtonCallback(window, mouseCallback = new MouseHandler());
		glfwSetScrollCallback(window, scrollCallback = new ScrollHandler());
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);
	}

	public static void main(String[] args) {
		new Main().start();
	}
}
