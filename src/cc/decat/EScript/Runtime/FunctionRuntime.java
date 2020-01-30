package cc.decat.EScript.Runtime;

import java.util.HashMap;
import java.util.Map;

public class FunctionRuntime {
	
	private Map<String, Integer> localMap = new HashMap<String, Integer>();
	private Map<String, Integer> parametMap = new HashMap<String, Integer>();
	
	public FunctionRuntime() {
		super();
	}

	public Map<String, Integer> getLocalMap() {
		return localMap;
	}

	public void setLocalMap(Map<String, Integer> localMap) {
		this.localMap = localMap;
	}

	public Map<String, Integer> getParametMap() {
		return parametMap;
	}

	public void setParametMap(Map<String, Integer> parametMap) {
		this.parametMap = parametMap;
	}
}