package cc.decat.EScript.AST;


public class ASTNode_If extends ASTNode{

	private ASTNode conditionExpr;
	private ASTNode Then;
	private ASTNode Else;
	
	public ASTNode_If(ASTNode conditionExpr, ASTNode Then, ASTNode Else) {
		super(ASTNodeType.If);
		this.conditionExpr = conditionExpr;
		this.Then = Then;
		this.Else = Else;
	}

	public ASTNode getConditionExpr() {
		return conditionExpr;
	}

	public ASTNode getThen() {
		return Then;
	}

	public ASTNode getElse() {
		return Else;
	}
	
	public static ASTNode getInstance(ASTNode conditionExpr, ASTNode Then, ASTNode Else) {
		return new ASTNode_If(conditionExpr, Then, Else);
	}

}
