package po.finance;

import java.io.Serializable;

import vo.finance.AccountVO;

//账户类
public class AccountPO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String amountOfAccount;
	
	//================================================================================================

	enum ResultMessage
	{
	}
	
	public AccountPO(String id,	String amountOfAccount)
	{
		this.id = id;
		this.amountOfAccount = amountOfAccount;
	}
	
	public String[] toStringArray()
	{
		String[] res = new String[2];
		
		res[0] = this.id;
		res[1] = this.amountOfAccount;
		
		return res;
	}

	/****************************************************************************
	 * get set
	 ****************************************************************************/
	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getAmountOfAccount()
	{
		return amountOfAccount;
	}

	public void setAmountOfAccount(String amountOfAccount)
	{
		this.amountOfAccount = amountOfAccount;
	}
	//================================================================================================

	public AccountVO toVO()
	{
		AccountVO accountVO = new AccountVO(this.id, this.amountOfAccount);
		
		return accountVO;
	}
}
