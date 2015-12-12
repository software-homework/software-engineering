//写入ArrayList<String>至.txt
package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Output
{
	/***************************************************************
	 * @param list
	 * @param path
	 ***************************************************************/
	public Output(ArrayList<Object> list, String path)
	{
		ObjectOutputStream oos = null;
		
		try 
		{
			oos = new ObjectOutputStream(new FileOutputStream(path));
			
			oos.writeObject(list);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			//关闭输出流
			try 
			{
				oos.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
	}
}
