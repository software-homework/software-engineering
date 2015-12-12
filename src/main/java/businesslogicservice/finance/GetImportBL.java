package businesslogicservice.finance;

import java.util.ArrayList;

import vo.salesman.ImportVO;

public interface GetImportBL {
	public ArrayList<ImportVO> getCheckedImport();
	
	public String getiID();/*****获取进货单id**/
	public String getirID();/*****获取进货退货单id**/
	public boolean CheckI(ImportVO importvo);
	public boolean approvalImport(String id,int approval);/*****修改库存**/
}
