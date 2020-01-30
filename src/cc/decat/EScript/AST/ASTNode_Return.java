package cc.decat.EScript.AST;

public class ASTNode_Return extends ASTNode{

	ASTNode retValueExpr;
	
	public ASTNode_Return(ASTNode retValueExpr) {
		super(ASTNodeType.Return);
		this.retValueExpr = retValueExpr;
	}
	
	public ASTNode_Return() {
		super(ASTNodeType.Return);
		this.retValueExpr = null;
	}

	public ASTNode getRetValueExpr() {
		return retValueExpr;
	}

	public void setRetValueExpr(ASTNode retValueExpr) {
		this.retValueExpr = retValueExpr;
	}

}
