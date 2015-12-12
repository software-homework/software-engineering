package vo.finance;

import java.util.ArrayList;

import po.finance.AccountPO;
import po.finance.LedgerPO;
import po.salesman.CustomerPO;
import vo.salesman.CustomerVO;

public class LedgerVO
{
	public String time;
	public ArrayList<CustomerVO> customerVO;
	public ArrayList<AccountVO> accountVO;
	public String type;
	//================================================================================================
	public LedgerVO(ArrayList<CustomerVO> customerVO, ArrayList<AccountVO> accountVO, String time, String type)
	{
		this.customerVO = customerVO;
		this.accountVO = accountVO;
		this.time = time;
		this.type = type;
	}

	public LedgerPO ToPO(){
		ArrayList<CustomerPO> customerPOs = new ArrayList<CustomerPO>();
		ArrayList<AccountPO> accountPOs = new ArrayList<AccountPO>();
		
		for(int i = 0; i < customerVO.size(); i++){
			customerPOs.add(new CustomerPO(customerVO.get(i).id, 
					customerVO.get(i).type, 
					customerVO.get(i).level, 
					customerVO.get(i).name,
					customerVO.get(i).phone, 
					customerVO.get(i).email, 
					customerVO.get(i).address,
					customerVO.get(i).postcode, 
					customerVO.get(i).limit, 
					customerVO.get(i).proceed, 
					customerVO.get(i).pay, 
					customerVO.get(i).user));
		}
		for(int i = 0; i < accountVO.size(); i++){
			accountPOs.add(new AccountPO(accountVO.get(i).id, accountVO.get(i).amountOfAccount));
		}
		return new LedgerPO(customerPOs, accountPOs, time, type);
	}
}