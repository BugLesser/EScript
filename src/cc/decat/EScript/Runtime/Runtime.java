package cc.decat.EScript.Runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import cc.decat.EScript.AST.*;

public class Runtime {

	private static Map<String, FunctionRuntime> funcMap = new HashMap<String, FunctionRuntime>();
	private static int labelIndex = 0;
	private static HashMap<String, Integer> varMap = new HashMap<String, Integer>();
	private static int varMapIndex = 1;
	private static String currentFuncName = "";
	private static String lastFuncName = "";
	private static ArrayList<ZL> zlList = new ArrayList<ZL>();
	private static String _while_start = "";
	private static String _while_then = "";
	private static String _while_end = "";

	static {
		zlList.add(new ZL("call", "main", null));
		zlList.add(new ZL("exit", null, null));
	}

	public static boolean checkNodeType(ASTNode node, ASTNodeType type) {
		return node != null && node.getType() == type;
	}

	private static ASTNode_ConstValue getASTNodeConstValue(ASTNode node) {
		if (checkNodeType(node, ASTNodeType.ConstValue)) {
			return (ASTNode_ConstValue) node;
		}
		return null;
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

	private static void code(String code) {
		System.out.println("\t" + code);
		zlList.add(parseZL(code));
	}


	public static ArrayList<ZL> execRootNode(ASTNode rootNode) {
		execASTNode(rootNode);
		return zlList;
	}

	public static void execASTNode(ASTNode node) {

		if (checkNodeType(node, ASTNodeType.Function)) {
			ASTNode_Function func = (ASTNode_Function) node;
			currentFuncName = func.getName();
			String funcs = func.getName();
			funcMap.put(funcs, new FunctionRuntime());
			code("start " + func.getName());
			for (String param : func.getParamNames()) {
				code("param " + param);
			}
			code("push ebp");
			code("mov ebp esp");
//			for(String paramName : func.getParamNames()) {
//				System.out.println("pop\t" + paramName);
//			}
			execASTNode(func.getBody());
//			code("end " + func.getName());
		} else if (checkNodeType(node, ASTNodeType.CallFunction)) {
			ASTNode_CallFunction callfunc = (ASTNode_CallFunction) node;
			for (int i = callfunc.getParamList().size() - 1; i >= 0; i--) {
				execASTNode(callfunc.getParamList().get(i));
			}
			if (callfunc.getFuncName().equals("print")) {
				code("syscall print");
			} else {
				code("call " + callfunc.getFuncName());
				code("popn");
				for (int i = 0; i < callfunc.getParamList().size(); i++) {
					code("popn");
				}
				if (callfunc.isRetValue()) {
					code("push eax");// ??
				}
			}
		} else if (checkNodeType(node, ASTNodeType.StmtList)) {
			for (ASTNode item : ((ASTNode_StmtList) node).getStmtList()) {
				execASTNode(item);
			}
		} else if (checkNodeType(node, ASTNodeType.Expr)) {
			ASTNode_Expr expr = (ASTNode_Expr) node;
			execASTNode(expr.getLeft());
			execASTNode(expr.getRight());
			execASTNode(expr.getBinaryOp());
		} else if (checkNodeType(node, ASTNodeType.ConstValue)) {
			ASTNode_ConstValue constValue = (ASTNode_ConstValue) node;
			if (constValue.getValue_type() == ConstValue_Type.Number) {
				code("push " + constValue.getValue_number());
			} else if (constValue.getValue_type() == ConstValue_Type.String) {
				code("push " + constValue.getValue_str());
			}
		} else if (checkNodeType(node, ASTNodeType.Binary_Op)) {
			switch (((ASTNode_Operator_Binary) node).getOpType()) {
			case Add:
				code("add");
				break;
			case Sub:
				code("sub");
				break;
			case Mul:
				code("mul");
				break;
			case Div:
				code("div");
				break;
			case Mod:
				code("mod");
			default:
				break;
			}
		} else if (checkNodeType(node, ASTNodeType.Var)) {
			ASTNode_Var var = ((ASTNode_Var) node);
//			if(funcMap.get(currentFuncName).getLocalMap().containsKey(var.getName())) {
//				code("push l" + funcMap.get(currentFuncName).getLocalMap().get(var.getName()));
//			}else if(funcMap.get(currentFuncName).getParametMap().containsKey(var.getName())) {
//				code("push p" + funcMap.get(currentFuncName).getParametMap().get(var.getName()));
//			}else {
//				code("var err");
//			}
			code("push " + var.getName());
		}
//		
//		else if(checkNodeType(node, ASTNodeType.While)) {
//			ASTNode_While While = (ASTNode_While)node;
//			String while_start = NewLabel();
//			String while_end = NewLabel();
//			code("label " + while_start);
//			execASTNode(While.getConditionExpr());
//			code("jz " + while_end);
//			execASTNode(While.getBody());
//			code("jmp " + while_start);
//			code("label " + while_end);
//		}
		else if (checkNodeType(node, ASTNodeType.ConditionExpr)) {
			ASTNode_ConditionExpr codExpr = (ASTNode_ConditionExpr) node;
			execASTNode(codExpr.getLeft());
			execASTNode(codExpr.getRight());
			code(codExpr.getCompareOperator().toString());
		} else if (checkNodeType(node, ASTNodeType.Assign)) {
			ASTNode_Assign assign = (ASTNode_Assign) node;
			execASTNode(assign.getExpr());
			// push value
			// pop var
			code("pop " + assign.getSymbol());
//			code("mov " + assign.getSymbol() +" eax");
		} else if (checkNodeType(node, ASTNodeType.DefVar)) {
			ASTNode_DefVar defvar = (ASTNode_DefVar) node;
			int index = funcMap.get(currentFuncName).getLocalMap().size();
			funcMap.get(currentFuncName).getLocalMap().put(defvar.getVarName(), index + 1);
			code("local " + defvar.getVarName());
			if (defvar.getValue() != null) {
				execASTNode(defvar.getValue());
				code("pop " + defvar.getVarName());
			}
		} else if (checkNodeType(node, ASTNodeType.Return)) {
			ASTNode_Return ret = (ASTNode_Return) node;
			execASTNode(ret.getRetValueExpr());
			code("pop eax");
			for (int i = 0; i < funcMap.get(currentFuncName).getLocalMap().size(); i++) {
				code("popn");
			}
			code("pop ebp");
			code("ret");
			// currentFuncName = lastFuncName;
		} else if (checkNodeType(node, ASTNodeType.If)) {
			ASTNode_If ifNode = (ASTNode_If) node;
			execASTNode(ifNode.getConditionExpr());
			String ifEndLabel = NewLabel();
			if (ifNode.getElse() != null) {
				String elseLabel = NewLabel();
				code("jz " + elseLabel);
				execASTNode(ifNode.getThen());
				code("jmp " + ifEndLabel);
				code("label " + elseLabel);
				execASTNode(ifNode.getElse());
				code("label " + ifEndLabel);
			} else {
				code("jz " + ifEndLabel);
				execASTNode(ifNode.getThen());
				code("label " + ifEndLabel);
			}
		} else if (checkNodeType(node, ASTNodeType.While)) {

			ASTNode_While While = (ASTNode_While) node;
//			String while_start = NewLabel();//_while_start
//			String while_then = NewLabel();//_while_then
//			String while_end = NewLabel();//_while_end

			String tmp_while_start = _while_start;
			String tmp_while_then = _while_then;
			String tmp_while_end = _while_end;

			_while_start = NewLabel();// _while_start
			_while_then = NewLabel();// _while_then
			_while_end = NewLabel();// _while_end

			code("label " + _while_start);
			ASTNode_StmtList conditionExprStmtList = (ASTNode_StmtList) While.getConditionExpr();
			for (ASTNode astNode : conditionExprStmtList.getStmtList()) {
				execASTNode(astNode);
			}
			code("jz " + _while_end);
			code("label " + _while_then);
			execASTNode(While.getBody());
			code("jmp " + _while_start);
			code("label " + _while_end);

			_while_start = tmp_while_start;
			_while_then = tmp_while_then;
			_while_end = tmp_while_end;
		} else if (checkNodeType(node, ASTNodeType.Break)) {
			code("jmp " + _while_end);
		} else if (checkNodeType(node, ASTNodeType.Continue)) {
			code("jmp " + _while_start);
		} else if (checkNodeType(node, ASTNodeType.Logical_Op)) {
			ASTNode_LogicalOp astNode_LogicalOp = (ASTNode_LogicalOp) node;
			if (astNode_LogicalOp.getOptype() == LogicalOperator_Type.And) {
				code("jz " + _while_end);
			} else if (astNode_LogicalOp.getOptype() == LogicalOperator_Type.Or) {
				code("jnz " + _while_then);
			}
		}
	}

	private static String NewLabel() {
		return "L" + labelIndex++;
	}

	private static String getAddrtoVar(String varName) {

		if (funcMap.get(currentFuncName).getLocalMap().containsKey(varName)) {
			return "l" + funcMap.get(currentFuncName).getLocalMap().get(varName);
		} else if (funcMap.get(currentFuncName).getParametMap().containsKey(varName)) {
			return "p" + funcMap.get(currentFuncName).getParametMap().get(varName);
		}
		return "err";
	}
}
