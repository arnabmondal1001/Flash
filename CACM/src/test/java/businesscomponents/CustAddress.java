package businesscomponents;

public class CustAddress {
	
	String addressType ;
    String addrVerfyCd;
    String addrVerfyMessg ;
    String useCleansedAddress ;           
    String cleanAddressLine1;
    


	public String getAddressType() {
		return addressType;
	}
	void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getAddrVerfyCd() {
		return addrVerfyCd;
	}
	public void setAddrVerfyCd(String addrVerfyCd) {
		this.addrVerfyCd = addrVerfyCd;
	}
	public String getAddrVerfyMessg() {
		return addrVerfyMessg;
	}
	public void setAddrVerfyMessg(String addrVerfyMessg) {
		this.addrVerfyMessg = addrVerfyMessg;
	}
	public String getUseCleansedAddress() {
		return useCleansedAddress;
	}
	public void setUseCleansedAddress(String useCleansedAddress) {
		this.useCleansedAddress = useCleansedAddress;
	}
	public String getCleanAddressLine1() {
		return cleanAddressLine1;
	}
	public void setCleanAddressLine1(String cleanAddressLine1) {
		this.cleanAddressLine1 = cleanAddressLine1;
	}
    
	

}
