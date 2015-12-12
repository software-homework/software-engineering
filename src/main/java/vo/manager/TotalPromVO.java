package vo.manager;

/**
 * 
 * @date 2014年11月27日
 * @author stk
 *
 */

/*
 * 针对总价的促销策略vo
 */
public class TotalPromVO {
	public String startTime;//起始日期
	public String endTime;//结束日期
	public double total;//总价
	public String goodsName;//赠品名称
	public double voucher;//代金券
	//-------------------------------------------------------------
	public TotalPromVO(String startTime, String endTime, double total, String goodsName, double voucher) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.total = total;
		this.goodsName = goodsName;
		this.voucher = voucher;
	}
}
