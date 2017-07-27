package pojo;

public class Device {

	public int id;
	public String did;
	public String device_name;
	public String isDisabled;
	public int owner_id;
	
	public Device(int id, String did, String device_name, String isDisabled, int owner_i) {
		super();
		this.id = id;
		this.did = did;
		this.device_name = device_name;
		this.isDisabled = isDisabled;
		this.owner_id = owner_i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}

	public int getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", did=" + did + ", device_name=" + device_name + ", isDisabled=" + isDisabled
				+ ", owner_id=" + owner_id + "]";
	}
	
	
	
}
