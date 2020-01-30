package cc.decat.EScript.AST;

public class ASTNode_DefVar extends ASTNode {

	private String varName;
	private ASTNode value;
	
	public ASTNode_DefVar(String varName, ASTNode value) {
		super(ASTNodeType.DefVar);
		this.varName = varName;
		this.value = value;
		// TODO Auto-generated constructor stub
	}

	public String getVarName() {
		return varName;
	}
	
	public ASTNode getValue() {
		return value;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	
}
