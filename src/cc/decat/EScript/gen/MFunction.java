package cc.decat.EScript.gen;

import java.util.HashMap;

public class MFunction {
	
	private String funcName;
	private int index;
	private HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> localMap = new HashMap<String, Integer>();
	
	public MFunction(String funcName, int index) {
		this.funcName = funcName;
		this.index = index;
	}
	
	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public int getIndex() {
		return index;
	}



	public void setIndex(int index) {
		this.index = index;
	}

	public void addLocal(String localName) {
		this.localMap.put(localName, this.localMap.size() + 1);
	}
	
	public Integer getLocal(String localName) {
		return this.localMap.get(localName);
	}
	
	public void addParam(String paramName) {
		this.paramMap.put(paramName, this.paramMap.size() + 1);
	}
	
	public Integer getParam(String paramName) {
		return this.paramMap.get(paramName);
	}

	public HashMap<String, Integer> getParamMap() {
		return paramMap;
	}

	public HashMap<String, Integer> getLocalMap() {
		return localMap;
	}
}