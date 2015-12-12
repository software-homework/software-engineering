package po.manager;

import java.io.*;

/**
 * 
 * @date 2014年11月30日
 * @author stk
 *
 */

/*
 * 组合快递降价策略po
 */
@SuppressWarnings("serial")
public class BargainPO implements Serializable{
	private String startTime;//起始日期
	private String endTime;//结束日期
	private String goodsName;//组合快递名称
	private double promotion;//降价额度
	//------------------------------------------------------------
	public BargainPO(String startTime, String endTime, String goodsName, double promotion) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setGoodsName(goodsName);
		this.setPromotion(promotion);
	}
	//-------------------------------------------------------------
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getPromotion() {
		return promotion;
	}

	public void setPromotion(double promotion) {
		this.promotion = promotion;
	}
}
