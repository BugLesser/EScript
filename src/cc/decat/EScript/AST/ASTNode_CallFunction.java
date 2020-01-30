package cc.decat.EScript.AST;

import java.util.ArrayList;

public class ASTNode_CallFunction extends ASTNode{

	private String funcName;
	private ArrayList<ASTNode> paramList;
	private boolean isRetValue;

	public ASTNode_CallFunction(String funcName, ArrayList<ASTNode> paramList, boolean isRetValue) {
		super(ASTNodeType.CallFunction);
		this.funcName = funcName;
		this.paramList = paramList;
		this.isRetValue = isRetValue;
	}
	
	public ASTNode_CallFunction(String funcName) {
		super(ASTNodeType.CallFunction);
		this.funcName = funcName;
		this.paramList = new ArrayList<ASTNode>();
	}

	public String getFuncName() {
		return funcName;
	}

	public ArrayList<ASTNode> getParamList() {
		return paramList;
	}

	public boolean isRetValue() {
		return isRetValue;
	}
}
