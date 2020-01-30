package cc.decat.EScript.AST;

public class ASTNode_Operator_Binary extends ASTNode{

	private BinaryOp_Type OpType;
	
	public ASTNode_Operator_Binary(BinaryOp_Type OpType) {
		super(ASTNodeType.Binary_Op);
		this.OpType = OpType;
	}
	
	public BinaryOp_Type getOpType() {
		return OpType;
	}
}
