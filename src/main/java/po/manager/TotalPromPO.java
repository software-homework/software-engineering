package po.manager;

import java.io.*;

/**
 * 
 * @date 2014年11月30日
 * @author stk
 *
 */

/*
 * 针对总价的促销策略po
 */
@SuppressWarnings("serial")
public class TotalPromPO implements Serializable{
	private String startTime;//起始日期
	private String endTime;//结束日期
	private double total;//总价
	private String goodsName;//赠品名称
	private double voucher;//代金券
	//-------------------------------------------------------------
	public TotalPromPO(String startTime, String endTime, double total, String goodsName, double voucher) {
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setTotal(total);
		this.setGoodsName(goodsName);
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
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
}
