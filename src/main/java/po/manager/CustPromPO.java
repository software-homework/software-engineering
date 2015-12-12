package po.manager;

import java.io.*;

/**
 * 
 * @date 2014年11月30日
 * @author stk
 *
 */

/*
 * 针对客户的促销策略po
 */
@SuppressWarnings("serial")
public class CustPromPO implements Serializable{
	private String startTime;//起始日期
	private String endTime;//结束日期
	private String type;//客户类型
	private String goodsName;//赠品名称
	private double discount;//折让金额
	private double voucher;//代金券
	//-------------------------------------------------------------
	public CustPromPO(String startTime, String endTime, String type, String goodsName, double discount, double voucher) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setType(type);
		this.setGoodsName(goodsName);
		this.setDiscount(discount);
		this.setVoucher(voucher);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public double getVoucher() {
		return voucher;
	}

	public void setVoucher(double voucher) {
		this.voucher = voucher;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
}
