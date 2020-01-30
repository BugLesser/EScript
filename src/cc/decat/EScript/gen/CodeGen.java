package cc.decat.EScript.gen;

import java.util.ArrayList;
import java.util.HashMap;

import cc.decat.EScript.Runtime.ZL;

public class CodeGen {

	private static ArrayList<String> codeList = new ArrayList<String>();

	private static void addCode(String code) {
		codeList.add(code);
	}

	public static boolean isInteger(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c != '0' && c != '1' && c != '2' && c != '3' && c != '4' && c != '5' && c != '6' && c != '7' && c != '8'
					&& c != '9') {
				return false;
			}
		}
		return true;
	}
	
	private static boolean isStackCtn(String code) {
		return true;
	}

	public static ArrayList<ZL> gen(ArrayList<ZL> zlList) {

		HashMap<String, MFunction> funcMap = new HashMap<String, MFunction>();
		HashMap<String, Integer> labelMap = new HashMap<String, Integer>();
		String funcName = "";
		ArrayList<ZL> tmp = new ArrayList<ZL>();

		for (ZL zl : zlList) {
			if (zl.isType("start")) {
				funcName = zl.getV1();
				funcMap.put(funcName, new MFunction(funcName, tmp.size()));
				tmp.add(zl);
			} else if (zl.isType("param")) {
				funcMap.get(funcName).addParam(zl.getV1());
			} else if (zl.isType("local")) {
				funcMap.get(funcName).addLocal(zl.getV1());
//				addCode("push 0");
				tmp.add(new ZL("push", "0", null));
			} else if (zl.isType("label")) {
				labelMap.put(zl.getV1(), tmp.size());
			} else {
				tmp.add(zl);
			}
		}

		ArrayList<ZL> ret = new ArrayList<ZL>();

		for (int i = 0; i < tmp.size(); i++) {
			ZL zl = tmp.get(i);
			if (zl.isType("start")) {
				funcName = zl.getV1();
				zl = new ZL("nop", null, null);
			} else if (zl.isType("call")) {
				zl.setV1(funcMap.get(zl.getV1()).getIndex() + "");
			} else if (zl.isType("jmp")) {
				zl.setV1(labelMap.get(zl.getV1()).toString());
			} else if (zl.isType("jnz")) {
				zl.setV1(labelMap.get(zl.getV1()).toString());
			} else if (zl.isType("jz")) {
				zl.setV1(labelMap.get(zl.getV1()).toString());
			} else if (zl.isType("push")) {
				if (!isInteger(zl.getV1())) {
					if (funcMap.get(funcName).getLocalMap().containsKey(zl.getV1())) {
						// 局部变量
						zl.setV1("l" + funcMap.get(funcName).getLocal(zl.getV1()));
					} else if (funcMap.get(funcName).getParamMap().containsKey(zl.getV1())) {
						// 参数
						zl.setV1("p" + funcMap.get(funcName).getParam(zl.getV1()));
					}
				}
				if (zl.getV1().equals("eax")) {
					
					// 返回语句
					if (i + 1 < tmp.size() && !isStackCtn(tmp.get(i + 1).getType())) {
						zl = new ZL("nop", null, null);
					}
				}
			} else if (zl.isType("pop")) {
				//System.out.println("->>>\t" + tmp.get(i + 1));
				if (funcMap.get(funcName).getLocalMap().containsKey(zl.getV1())) {
					// 局部变量
					zl.setV1("l" + funcMap.get(funcName).getLocal(zl.getV1()));
				} else if (funcMap.get(funcName).getParamMap().containsKey(zl.getV1())) {
					// 参数
					zl.setV1("p" + funcMap.get(funcName).getParam(zl.getV1()));
				}
			} else if (zl.isType("mov")) {
				{
					if (funcMap.get(funcName).getLocalMap().containsKey(zl.getV1())) {
						// 局部变量
						zl.setV1("l" + funcMap.get(funcName).getLocal(zl.getV1()));
					} else if (funcMap.get(funcName).getParamMap().containsKey(zl.getV1())) {
						// 参数
						zl.setV1("p" + funcMap.get(funcName).getParam(zl.getV1()));
					}
				}
				{
					if (!isInteger(zl.getV2())) {
						if (funcMap.get(funcName).getLocalMap().containsKey(zl.getV2())) {
							// 局部变量
							zl.setV2("l" + funcMap.get(funcName).getLocal(zl.getV2()));
						} else if (funcMap.get(funcName).getParamMap().containsKey(zl.getV2())) {
							// 参数
							zl.setV2("p" + funcMap.get(funcName).getParam(zl.getV2()));
						}
					}
				}
			}
			System.out.println(zl);
			ret.add(zl);
		}
		return ret;
	}
}
