//从.txt文件读入ArrayList<String>
package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Input 
{
	/**
	 * @param path
	 * @return list
	 * @throws IOException 
	 */
	@SuppressWarnings({ "unchecked" })
	public ArrayList<Object> input(String path)
	{
		ObjectInputStream ois = null;
		FileReader fr = null;
		ArrayList<Object> list = new ArrayList<Object>();
		File f = null;
		
		//判断文件是否已存在
		f = new File(path);		
		if(!f.exists())
		{
			try 
			{
				f.createNewFile();
			} 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}
		
		//判断是否为空
		try 
		{
			fr = new FileReader(path);
			if(fr.read() == -1)
			{
				return new ArrayList<Object>();
			}
		} 
		catch (FileNotFoundException e2) 
		{
			e2.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				fr.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		
		//读信息
		try 
		{		
			ois = new ObjectInputStream(new FileInputStream(path));
			
			//读出
			list = (ArrayList<Object>)ois.readObject();
			
			//返回值
			return list;
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			//关闭流
			try
			{
				ois.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		return list;
	}
}
