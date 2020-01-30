package cc.decat.EScript.AST;

public class ASTNode_Assign extends ASTNode {

	private String symbol;
	private ASTNode expr;
	
	public ASTNode_Assign(String symbol, ASTNode expr) {
		super(ASTNodeType.Assign);
		this.symbol = symbol;
		this.expr = expr;
	}

	public String getSymbol() {
		return symbol;
	}

	public ASTNode getExpr() {
		return expr;
	}

	public static ASTNode getInstance(String symbol, ASTNode expr) {
		return new ASTNode_Assign(symbol, expr);
	}
}
