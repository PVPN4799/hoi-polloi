package io;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

public class Window 
{
	private long window;
	private int width, height;
	private boolean fullscreen;
	private Input input;
	
	public static void setCallback()
	{
		glfwSetErrorCallback(new GLFWErrorCallback() 
				{

					@Override
					public void invoke(int error, long description) 
					{
						throw new IllegalStateException(GLFWErrorCallback.getDescription(description));
					}});
	}
	
	public Window() 
	{
		setSize(640,480);
		setFullscreen(false);
	}
	
	public void createWindow(String title)
	{

		window = glfwCreateWindow(width,
				height,
				title,
				fullscreen ? glfwGetPrimaryMonitor() : 0,
				0);
		// this line required here too
		glfwMakeContextCurrent(window);
		input = new Input(window);
		
		if(window == 0)
		{
			throw new IllegalStateException("Faled to create window!");
		}	
			
	    //set window position
		
		if(!fullscreen)
		{
			
			GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
			glfwSetWindowPos(window, 
			       (vid.width() -width)/2,
	               (vid.height() -height)/2);
	    
			glfwShowWindow(window);
			glfwMakeContextCurrent(window);
			
			input = new Input(window);
		}
	}
	
	public boolean shouldClose()
	{
		return glfwWindowShouldClose(window) != false;
	}

	public void swapBuffers()
	{
		glfwSwapBuffers(window);
	}
	
	public void setSize(int width, int height)
	{
		this.width = width;
		this.height = height;
		
	}
	
	public void setFullscreen(boolean fullscreen)
	{
		this.fullscreen = fullscreen;
	}
	
	public void update()
	{
		input.update();
		glfwPollEvents();
	}
	
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public boolean getFullScreen() { return fullscreen;}
	public long getWindow() { return window; }
	public Input getInput() { return input;}
}
