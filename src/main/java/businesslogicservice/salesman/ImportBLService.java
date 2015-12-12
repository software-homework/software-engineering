package businesslogicservice.salesman;

import java.util.ArrayList;

import vo.salesman.ImportVO;

public interface ImportBLService {
	public String getiID();
	public String getirID();
	public boolean CheckI(ImportVO importvo);
	public ArrayList<ImportVO> ShowI();
	public ImportVO findImport(String ID);


}
