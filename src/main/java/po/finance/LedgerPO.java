package po.finance;

import java.io.Serializable;
import java.util.ArrayList;

import po.salesman.CustomerPO;
import vo.finance.AccountVO;
import vo.finance.LedgerVO;
import vo.salesman.CustomerVO;

/**
 * @author QQ
 * @param time 创建时间
 * @param type 期初建账/非期初建账
 */
public class LedgerPO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String time;
	private ArrayList<CustomerPO> customerPO;
	private ArrayList<AccountPO> accountPO;
	private String type;
		
	public LedgerPO(ArrayList<CustomerPO> customerPO, ArrayList<AccountPO> accountPO, String time, String type){
		this.customerPO = customerPO;
		this.accountPO = accountPO;
		this.setTime(time);
		this.setType(type);
	}
	

	
	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}


	public ArrayList<CustomerPO> getCustomerPO() {
		return customerPO;
	}



	public void setCustomerPO(ArrayList<CustomerPO> customerPO) {
		this.customerPO = customerPO;
	}



	public ArrayList<AccountPO> getAccountPO() {
		return accountPO;
	}



	public void setAccountPO(ArrayList<AccountPO> accountPO) {
		this.accountPO = accountPO;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	/**
	 * ToVO
	 */
	public LedgerVO ToVO(){
		ArrayList<CustomerVO> customerVO = new ArrayList<CustomerVO>();
		ArrayList<AccountVO> accountVO = new ArrayList<AccountVO>();

		for(int i = 0; i < this.customerPO.size(); i++){
			customerVO.add(new CustomerVO(this.customerPO.get(i).getType(),
					this.customerPO.get(i).getLevel(),
					this.customerPO.get(i).getName(),
					this.customerPO.get(i).getPhone(),
					this.customerPO.get(i).getAddress(),
					this.customerPO.get(i).getPostcode(),
					this.customerPO.get(i).getLimit(),
					this.customerPO.get(i).getProceed(),
					this.customerPO.get(i).getPay(),
					this.customerPO.get(i).getUser()));
		}
		for(int i = 0; i < this.accountPO.size(); i++){
			accountVO.add(new AccountVO(this.accountPO.get(i).getId(), this.accountPO.get(i).getAmountOfAccount()));
		}
		
		return new LedgerVO(customerVO, accountVO, time, type);
	}
}