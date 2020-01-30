package cc.decat.EScript.AST;

public class ASTNode_Expr extends ASTNode {

	private ASTNode left;
	private ASTNode BinaryOp;
	private ASTNode right;

	public ASTNode_Expr(ASTNode left, ASTNode binaryOp, ASTNode right) {
		super(ASTNodeType.Expr);
		this.left = left;
		this.BinaryOp = binaryOp;
		this.right = right;
	}

	public ASTNode getLeft() {
		return left;
	}

	public ASTNode getBinaryOp() {
		return BinaryOp;
	}

	public ASTNode getRight() {
		return right;
	}

	public static ASTNode getInstance(ASTNode left, ASTNode binaryOp, ASTNode right) {
		return new ASTNode_Expr(left, binaryOp, right);
	}
	
}
