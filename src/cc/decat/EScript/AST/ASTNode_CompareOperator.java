package cc.decat.EScript.AST;

public class ASTNode_CompareOperator extends ASTNode{

	private CompareOperator_Type op_type;
	
	public ASTNode_CompareOperator(CompareOperator_Type op_type) {
		super(ASTNodeType.Compare);
		this.op_type = op_type;
	}

	@Override
	public String toString() {
		return op_type.toString();
	}
	
	
}
