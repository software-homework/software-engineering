package businesslogicservice.finance;

import java.util.ArrayList;

import vo.courier.ImportVO;

public interface GetImportBL {
	public ArrayList<ImportVO> getCheckedImport();
	
	public String getiID();/*****获取快递接单id**/
	public String getirID();/*****获取快递接退货单id**/
	public boolean CheckI(ImportVO importvo);
	public boolean approvalImport(String id,int approval);/*****修改库存**/
}
