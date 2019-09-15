	
package world;

import entity.player;
import entity.Transform;

public class Tile 
{

	public static Tile tiles[] = new Tile[16];
	public static byte not = 0;
	
	
	public static final Tile test_tile = new Tile("test");
	public static final Tile test2 = new Tile("kingpop").setSolid();
	private boolean solid;


	private byte id;
	private String texture;
	
	public Tile(String texture) {
		this.id = not;
		not++;
		this.texture=texture;
		this.solid = false;
		if (tiles[id] != null)
			throw new IllegalStateException("Tiles  at ["+ id + "] is already being used");
		tiles[id]=this;
	}

	public static Tile[] getTiles() {
		return tiles;
	}

	public static Tile getTestTile() {
		return test_tile;
	}

	public byte getId() {
		return id;
	}

	public String getTexture() {
		return texture;
	}
	
	public Tile setSolid() { 
		this.solid = true;
	    return this; 
	}
	
	public  boolean isSolid()
	{ 
		return solid;
	}

}
