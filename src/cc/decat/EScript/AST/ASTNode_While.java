package cc.decat.EScript.AST;

public class ASTNode_While extends ASTNode{

	private ASTNode conditionExpr;
	private ASTNode body;
	
	public ASTNode_While(ASTNode conditionExpr, ASTNode body) {
		super(ASTNodeType.While);
		this.conditionExpr = conditionExpr;
		this.body = body;
	}

	public ASTNode getConditionExpr() {
		return conditionExpr;
	}

	public ASTNode getBody() {
		return body;
	}
	
}
