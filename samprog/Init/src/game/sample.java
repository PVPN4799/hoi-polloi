package game;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.*;
import org.lwjgl.opengl.*;
import org.lwjgl.glfw.GLFWVidMode;
//the main class

public class sample
{

	public static void main(String[] args) 
	{
		Window.setCallback();
		
		if (!glfwInit())
		{
			throw new IllegalStateException("Failed to initialise GLFW");
		}
		
		Window win = new Window();
		win.setFullscreen(false);
		win.setSize(1024, 768);		
		win.createWindow("Game");
		GL.createCapabilities();
		Camera camera = new Camera(win.getWidth(), win.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		
		float vertices[] = new float[] {
				-0.5f, 0.5f, 0, //top left 0
				0.5f, 0.5f, 0, //top right 1
				0.5f, -0.5f,0, //btm right 2
				-0.5f, -0.5f, 0, //btm left 3
		};
		
		float texture[] = new float[] 
			{
				0,0,
				1,0,
				1,1,
				0,1
			};
		
		int indices [] = new int[]
				{
						0,1,2,
						2,3,0
				};
		
		Model model = new Model(vertices,texture,indices);
		
		Shader shader = new Shader("shader");
		
		Texture tex = new Texture("D:/Eclipse/workspace/models/test.png");
		
		Matrix4f scale = new Matrix4f()
				.translate(new Vector3f(100, 0, 0))
				.scale(256);
		Matrix4f target = new Matrix4f();
		
		camera.setPosition(new Vector3f(-100, 0, 0));
		
		double frame_cap = 1.0/60.0;
		
		double frame_time = 0;
		int frames = 0;
		
		double time = Timer.getTime();
		double unprocessed = 0;
		
		while(!win.shouldClose())
		{
			boolean can_render = false;
			
			double time_2  = Timer.getTime();
			double passed = time_2 - time;
			unprocessed+=passed;
			frame_time+=passed;
			time = time_2;
			//when game is not processing
			while(unprocessed >= frame_cap)
			{
				unprocessed-=frame_cap;
				can_render = true;
				target = scale;
				if(win.getInput().isKeyReleased(GLFW_KEY_ESCAPE))
				//glfwSetWindowShouldClose(win.getWindow(), true);
					System.out.println("TRUE");
			
				win.update();
				if(frame_time >= 1.0)
				{
					frame_time = 0;
					System.out.println("FPS: " + frames);
					frames = 0;
				}
			
			}
			if (can_render)
			{
			glClear(GL_COLOR_BUFFER_BIT);
			
			shader.bind();
			shader.setUniform("sampler", 0);
			shader.setUniform("projection", camera.getProjection().mul(target));
			tex.bind(0);
			model.render();
			
			win.swapBuffers();
			frames++;
			}
		}
		
		glfwTerminate();
	}
		
	}

