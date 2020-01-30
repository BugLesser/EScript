package cc.decat.EScript.AST;

public class ASTNode_Var extends ASTNode{

	private String name;
	
	public ASTNode_Var(String name) {
		super(ASTNodeType.Var);
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
