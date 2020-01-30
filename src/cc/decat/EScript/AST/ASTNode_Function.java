package cc.decat.EScript.AST;

import java.util.ArrayList;

//函数定义体

public class ASTNode_Function extends ASTNode{

	private String	Name;
	private ArrayList<String> ParamNames;
	private ASTNode Body;
	
	public ASTNode_Function(String name, ArrayList<String> ParamNames, ASTNode body) {
		super(ASTNodeType.Function);
		this.Name = name;
		this.ParamNames = ParamNames;
		this.Body = body;
	}

	public String getName() {
		return Name;
	}

	public ArrayList<String> getParamNames() {
		return ParamNames;
	}

	public ASTNode getBody() {
		return Body;
	}
	
	public static ASTNode getInstance(String name, ArrayList<String> ParamNames, ASTNode body) {
		return new ASTNode_Function(name, ParamNames, body);
	}
	
}
