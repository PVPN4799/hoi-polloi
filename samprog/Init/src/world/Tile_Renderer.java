package world;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import world.Model;
import world.Texture;
import world.Tile;
public class Tile_Renderer 
{
	private HashMap<String, Texture> tile_textures;
	private Model model;
	public Tile_Renderer() 
	{
		tile_textures = new HashMap<String, Texture>();
		
		float vertices[] = new float[] 
				{
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
		
		model = new Model(vertices,texture,indices);
	
	
	for(int i = 0; i < Tile.tiles.length;i++)
	{
		if(Tile.tiles[1] != null)
		{
		
			if(!tile_textures.containsKey(Tile.tiles[i].getTexture()));
			{
				String tex = Tile.tiles[i].getTexture();
				tile_textures.put(tex, new Texture(tex+".png"));
			}
		

		}
	}
	}
	public void Render_Tile(byte id, int x, int y, Shader shader, Matrix4f world, Camera cam)
	{
		shader.bind();
		if(tile_textures.containsKey(Tile.tiles[id].getTexture()))
		tile_textures.get(Tile.tiles[id].getTexture()).bind(0);
		
		Matrix4f tile_pos = new Matrix4f().translate (new Vector3f(x*2, y*2, 0));
		Matrix4f target = new Matrix4f();
		
		cam.getProjection().mul(world, target);
		target.mul(tile_pos);
		
		shader.setUniform("sampler",0);
		shader.setUniform("projection", target);
		
		model.render();
	}
}
	

