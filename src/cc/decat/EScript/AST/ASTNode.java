package cc.decat.EScript.AST;


public abstract class ASTNode {
	
	private ASTNodeType type = null;
	
	ASTNode(ASTNodeType type) {
		this.type = type;
	}

	public ASTNodeType getType() {
		return type;
	}
}
