package cc.decat.EScript.AST;


//逻辑表达式 a+1 < b+1
public class ASTNode_ConditionExpr extends ASTNode{

	private ASTNode left;
	private ASTNode compareOperator;
	private ASTNode right;

	public ASTNode_ConditionExpr(ASTNode left, ASTNode compareOperator, ASTNode right) {
		super(ASTNodeType.ConditionExpr);
		this.left = left;
		this.compareOperator = compareOperator;
		this.right = right;
	}

	public ASTNode getLeft() {
		return left;
	}

	public ASTNode getCompareOperator() {
		return compareOperator;
	}

	public ASTNode getRight() {
		return right;
	}

	public static ASTNode getInstance(ASTNode left, ASTNode compareOperator, ASTNode right) {
		return new ASTNode_ConditionExpr(left, compareOperator, right);
	}
	
}
