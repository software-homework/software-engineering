package vo.manager;

/**
 * 
 * @date 2014年11月27日
 * @author stk
 *
 */

/*
 * 组合快递降价策略vo
 */
public class BargainVO {
	public String startTime;//起始日期
	public String endTime;//结束日期
	public String goodsName;//组合快递名称
	public double promotion;//降价额度
	//-------------------------------------------------------------
	public BargainVO(String startTime, String endTime, String goodsName, double promotion) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.goodsName = goodsName;
		this.promotion = promotion;
	}
}
