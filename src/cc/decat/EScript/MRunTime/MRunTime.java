package cc.decat.EScript.MRunTime;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cc.decat.EScript.Runtime.ZL;

public class MRunTime {

	private static int esp = 0, ebp = 0, eax = 0, ebx = 0, eip = 0;
	private static int flag = 0;
	private static int[] stack = new int[10240];


	public static boolean isInteger(String str) {
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if(
					c!='0' &&
					c!='1' &&
					c!='2' &&
					c!='3' &&
					c!='4' &&
					c!='5' &&
					c!='6' &&
					c!='7' &&
					c!='8' &&
					c!='9'
					) {
				return false;
			}
		}
		return true;
	}

	private static int getValueByV(String v) {

		if (isInteger(v)) {
			return Integer.parseInt(v);
		}
		if (v.equals("esp")) {
			return esp;
		}
		if (v.equals("ebp")) {
			return ebp;
		}
		if (v.equals("eax")) {
			return eax;
		}
		if (v.equals("ebx")) {
			return ebx;
		}
		if (v.equals("eip")) {
			return eip;
		}
		if (v.charAt(0) == 'l') {
			int i = Integer.parseInt(v.substring(1, v.length()));
			return stack[ebp + i];
		} else if (v.charAt(0) == 'p') {
			int i = Integer.parseInt(v.substring(1, v.length()));
			return stack[ebp - 1 - i];
		}
		//err
		System.err.println("err:\t" + eip);
		return 0;
	}
	
	private static void pop(int value, String v) {
		
		if (v.equals("esp")) {
			esp = value;
		}
		if (v.equals("ebp")) {
			ebp = value;
		}
		if (v.equals("eax")) {
			eax = value;
		}
		if (v.equals("ebx")) {
			ebx = value;
		}
		if (v.equals("eip")) {
			eip = value;
		}
		if (v.charAt(0) == 'l') {
			int i = Integer.parseInt(v.substring(1, v.length()));
			stack[ebp + i] = value;
		} else if (v.charAt(0) == 'p') {
			int i = Integer.parseInt(v.substring(1, v.length()));
			stack[ebp - 1 - i] = value;;
		}
	}
	
	private static void mov(String v, int value) {
		
		if (v.equals("esp")) {
			esp = value;
		}
		if (v.equals("ebp")) {
			ebp = value;
		}
		if (v.equals("eax")) {
			eax = value;
		}
		if (v.equals("ebx")) {
			ebx = value;
		}
		if (v.equals("eip")) {
			eip = value;
		}
		if (v.charAt(0) == 'l') {
			int i = Integer.parseInt(v.substring(1, v.length()));
			stack[ebp + i] = value;
		} else if (v.charAt(0) == 'p') {
			int i = Integer.parseInt(v.substring(1, v.length()));
			stack[ebp - 1 - i] = value;;
		}
	}

	public static void exec(List<ZL> ZLList) {

		while (eip < ZLList.size()) {
			ZL zl = ZLList.get(eip);
			String v1 = zl.getV1();
			String v2 = zl.getV2();
			if (zl.isType("push")) {
				stack[++esp] = getValueByV(v1);
			}
			else if(zl.isType("pop")) {
				int tmp = stack[esp--];
				pop(tmp, v1);
			}
			else if(zl.isType("popn")) {
				esp--;
			}
			else if(zl.isType("add")) {
				int a = stack[esp--];//right
				int b = stack[esp--];//left
				stack[++esp] = a + b;
			}
			else if(zl.isType("sub")) {
				int a = stack[esp--];
				int b = stack[esp--];
				stack[++esp] = b - a;
			}
			else if(zl.isType("mul")) {
				int a = stack[esp--];
				int b = stack[esp--];
				stack[++esp] = b * a;
			}
			else if(zl.isType("div")) {
				int a = stack[esp--];
				int b = stack[esp--];
				stack[++esp] = b / a;
			}
			else if(zl.isType("mod")) {
				int a = stack[esp--];
				int b = stack[esp--];
				stack[++esp] = b % a;
			}
			else if(zl.isType("mov")) {
				mov(v1, getValueByV(v2));
			}
			else if(zl.isType("jmp")) {
				eip = getValueByV(v1);
				continue;
			}
			else if(zl.isType("syscall")) {
				if(v1.equals("print")) {
					System.out.println("print:\t" + stack[esp--]);
				}
			}
			else if(zl.isType("call")) {
				stack[++esp] = eip + 1;
				eip = Integer.parseInt(v1);
				continue;
			}
			else if(zl.isType("exit")) {
				System.out.println("进程已结束");
				System.exit(0);
			}
			else if(zl.isType("ret")) {
				eip = stack[esp];
				continue;
			}
			else if(zl.isType("nop")) {
				//
			}
			else if(zl.isType("jz")) {
				if(flag == 0) {
					eip = Integer.parseInt(zl.getV1());
					continue;
				}
			}
			else if(zl.isType("jnz")) {
				if(flag != 0) {
					eip = Integer.parseInt(zl.getV1());
					continue;
				}
			}
			else if(zl.isType("gt")) {
				int a = stack[esp--];//10
				int b = stack[esp--];//L1
				if(b > a) {
					flag = 1;
				}else {
					flag = 0;
				}
			}
			else if(zl.isType("lt")) {
				int a = stack[esp--];//10
				int b = stack[esp--];//L1
				if(b < a) {
					flag = 1;
				}else {
					flag = 0;
				}
			}
			else if(zl.isType("gte")) {
				int a = stack[esp--];//10
				int b = stack[esp--];//L1
				if(b >= a) {
					flag = 1;
				}else {
					flag = 0;
				}
			}
			else if(zl.isType("lte")) {
				int a = stack[esp--];//10
				int b = stack[esp--];//L1
				if(b <= a) {
					flag = 1;
				}else {
					flag = 0;
				}
			}
			else if(zl.isType("eq")) {
				int a = stack[esp--];//10
				int b = stack[esp--];//L1
				if(b == a) {
					flag = 1;
				}else {
					flag = 0;
				}
			}
			else if(zl.isType("neq")) {
				int a = stack[esp--];//10
				int b = stack[esp--];//L1
				if(b != a) {
					flag = 1;
				}else {
					flag = 0;
				}
			}
			else {
				System.err.println("err code:\t" + zl);
				System.exit(0);
			}
			
			eip++;
		}
	}

	private static List<ZL> getZLByFile(String filename) throws Exception {

		List<ZL> zlList = new ArrayList<ZL>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
		String line = null;
		while ((line = br.readLine()) != null) {
			ZL zl = parseZL(line);
			System.out.println(zl);
			zlList.add(zl);
		}
		return zlList;
	}

	private static ZL parseZL(String str) {

		String[] split = str.split(" ");
		if (split.length == 3) {
			return new ZL(split[0], split[1], split[2]);
		} else if (split.length == 2) {
			return new ZL(split[0], split[1], null);
		} else if (split.length == 1) {
			return new ZL(split[0], null, null);
		} else {
			// error
		}
		return null;
	}

}