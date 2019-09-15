import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import collision.AABB;
import entity.player;
import world.Tile_Renderer;
import world.World;
import world.Shader;
import world.Camera;
import world.Texture;
import world.Tile;
import io.Window;
public class sample
{
	public static void main(String[] args) 
	{
		Window.setCallback();
		
		AABB box1 = new AABB(new Vector2f(4,0), new Vector2f(4,1));
		AABB box2 = new AABB(new Vector2f(5,0), new Vector2f(4,1));
		
		if(box1.isIntersecting(box2))
			System.out.println("ROCKER GOT YOU!!");
		
		if (!glfwInit())
		{
			throw new IllegalStateException("Failed to initialise GLFW");
		}
		
		Window win = new Window();
		win.setFullscreen(false);
		win.setSize(640, 480);		
		win.createWindow("Wrath of the Rockers");
		GL.createCapabilities();
		Camera camera = new Camera(win.getWidth(), win.getHeight());
		
		glEnable(GL_TEXTURE_2D);
		Tile_Renderer tiles = new Tile_Renderer();
		
//		float vertices[] = new float[] {
//		   -0.5f, 0.5f, 0, //top left 0
//			0.5f, 0.5f, 0, //top right 1
//			0.5f, -0.5f,0, //btm right 2
//         -0.5f, -0.5f, 0, //btm left 3
//		};
		
//			float texture[] = new float[] 
//			{
//				0,0,
//				1,0,
//				1,1,
//				0,1
//			};
		
//		int indices [] = new int[]
//				{
//						0,1,2,
//						2,3,0
//				};
		
//		Model model = new Model(vertices,texture,indices);
		
		Shader shader = new Shader("shader");
				
		
		World world = new World();
		
		player player1 = new player();
	   
		world.setTile(Tile.test2, 0, 1);
		world.setTile(Tile.test2, 4, 1);
		world.setTile(Tile.test2, 6, 2);
		world.setTile(Tile.test2, 4, 3);
		world.setTile(Tile.test2, 7, 9);
		world.setTile(Tile.test2, 9, 12);
		world.setTile(Tile.test2, 2, 12);
		world.setTile(Tile.test2, 8, 12);
		world.setTile(Tile.test2, 13, 13);
		world.setTile(Tile.test2, 4, 10);
		world.setTile(Tile.test2, 9, 9);
		world.setTile(Tile.test2, 9, 6);
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
				//target = scale;
				if(win.getInput().isKeyReleased(GLFW_KEY_ESCAPE))
				//glfwSetWindowShouldClose(win.getWindow(), true);
					System.out.println("TRUE");
			
				if(win.getInput().isKeyDown(GLFW.GLFW_KEY_D))
				{
					camera.getPosition().sub(new Vector3f(1,0,0));
				}
				if(win.getInput().isKeyDown(GLFW.GLFW_KEY_A))
				{
					camera.getPosition().sub(new Vector3f(-1,0,0));
				}
				if(win.getInput().isKeyDown(GLFW.GLFW_KEY_S))
				{
					camera.getPosition().sub(new Vector3f(0,-1,0));
				}
				if(win.getInput().isKeyDown(GLFW.GLFW_KEY_W))
				{
					camera.getPosition().sub(new Vector3f(0,1,0));
				}
				
				player1.update((float)frame_cap, win, camera, world);
				
				world.correctCamera(camera, win);
				
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
			
			//shader.bind();
			//shader.setUniform("sampler", 0);
			//shader.setUniform("projection", camera.getProjection().mul(target));
			//tex.bind(0);
			//model.render();
			
			
			world.render(tiles, shader, camera, win);
			player1.render(shader, camera);
			
			
			win.swapBuffers();
			frames++;
			}
		}
		
		glfwTerminate();
	}
		
	}

