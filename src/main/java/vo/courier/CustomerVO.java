package vo.courier;

public class CustomerVO {
	public int id;
	public String type;
	public String level;
	public String name;
	public String phone;
	public String address;
	public String email;
	public int postcode;
	public double limit;
	public double proceed;
	public double pay;
	public String user;
	
	public CustomerVO(String type,String level,String name,String phone,String address,int postcode,double limit,double proceed,double pay,String user){
		this.type = type;
		this.level=level;
		this.name=name;
		this.phone=phone;
		this.address=address;
		this.postcode=postcode;
		this.limit=limit;
		this.proceed=proceed;
		this.pay=pay;
		this.user=user;
		
	}
	
	public CustomerVO(String name){
		this.name=name;
	}
	
	public CustomerVO(String level,String phone,String address,int postcode,double limit){
		this.level=level;
		this.phone=phone;
		this.address=address;
		this.postcode=postcode;
		this.limit=limit;
	}
	public CustomerVO(){
		
	}


}
