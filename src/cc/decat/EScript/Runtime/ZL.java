package cc.decat.EScript.Runtime;

public class ZL {

	private String type;
	private String v1;
	private String v2;

	public ZL(String type, String v1, String v2) {
		super();
		this.type = type.toLowerCase();
		this.v1 = v1;
		this.v2 = v2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.toLowerCase();
	}

	public String getV1() {
		return v1;
	}

	public void setV1(String v1) {
		this.v1 = v1;
	}

	public String getV2() {
		return v2;
	}

	public void setV2(String v2) {
		this.v2 = v2;
	}
	
	public boolean isType(String t) {
		return this.type.equals(t.toLowerCase());
	}

	@Override
	public String toString() {
		if(v1 != null && v2 != null) {
			return type + " " + v1 + " " + v2;
		}else if(v1 != null) {
			return type + " " + v1;
		}else {
			return type;
		}
	}

}