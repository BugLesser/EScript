package cc.decat.EScript.AST;

public class ASTNode_LogicalOp extends ASTNode {

	LogicalOperator_Type optype;
	
	public ASTNode_LogicalOp(LogicalOperator_Type optype) {
		super(ASTNodeType.Logical_Op);
		this.optype = optype;
	}

	public LogicalOperator_Type getOptype() {
		return optype;
	}

	public void setOptype(LogicalOperator_Type optype) {
		this.optype = optype;
	}
	
}
