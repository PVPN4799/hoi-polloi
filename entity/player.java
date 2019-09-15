package entity;

import org.joml.*;
import org.lwjgl.glfw.GLFW;

import io.Window;
import world.Camera;
import world.Model;
import world.Shader;
import world.Texture;
import world.World;

public class player
{
	private Model model;
	private Transform transform;
	private Texture texture;
	
	public player() 
	{
		float vertices[] = new float[] 
				{
				-1f, 1f, 0, //top left 0
				1f, 1f, 0, //top right 1
				1f, -1f,0, //btm right 2
				-1f, -1f, 0, //btm left 3
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
		
		model = new Model(vertices,texture,indices);
		this.texture = new Texture("runner.png");
		
		transform = new Transform();
		transform.scale = new Vector3f(16,16,1);
	}

		public void update(float delta, Window window, Camera camera, World world)
		{
			if(window.getInput().isKeyDown(GLFW.GLFW_KEY_LEFT)) 
				transform.pos.add(new Vector3f(-10*delta, 0, 0));

			if(window.getInput().isKeyDown(GLFW.GLFW_KEY_RIGHT)) 
				transform.pos.add(new Vector3f(10*delta, 0, 0));


			if(window.getInput().isKeyDown(GLFW.GLFW_KEY_UP)) 
				transform.pos.add(new Vector3f(0, 10*delta, 0));

			if(window.getInput().isKeyDown(GLFW.GLFW_KEY_DOWN)) 
				transform.pos.add(new Vector3f(0, -10*delta, 0));
		
		
		if(transform.pos.x > (world.width*2)-1.5)
			transform.pos.x=(world.width*2)-1.5f;
		if(transform.pos.x < 0)
			transform.pos.x=0;
		if(transform.pos.y > 0)
			transform.pos.y=0;
		if(transform.pos.y < -(world.height*2)+1.5f)
			transform.pos.y=-(world.height*2)+1.5f;
	
		camera.setPosition(transform.pos.mul(-world.getScale(), new Vector3f()));;	
		}
	
		
		
	public void render(Shader shader, Camera camera)
	{
		shader.bind();
		shader.setUniform("sampler", 0);
		shader.setUniform("projection", transform.getProjection(camera.getProjection()));
		texture.bind(0);
		model.render();
	}
	
}
