/**
 * 对象未找到
 */
package exception;

@SuppressWarnings("serial")
public class NotFoundPO extends Exception 
{
	public NotFoundPO(String msg)
	{
		super(msg);
	}
}
