package cc.decat.EScript.AST;

public class ASTNode_ConstValue extends ASTNode{

	private ConstValue_Type value_type;
	private String value_str;
	private Long value_number;
	
	public ASTNode_ConstValue(String value_str) {
		super(ASTNodeType.ConstValue);
		this.value_type = ConstValue_Type.String;
		this.value_str = value_str;
	}
	
	public ASTNode_ConstValue(Long value_number) {
		super(ASTNodeType.ConstValue);
		this.value_type = ConstValue_Type.Number;
		this.value_number = value_number;
	}

	public ConstValue_Type getValue_type() {
		return value_type;
	}

	public String getValue_str() {
		return value_str != null ? value_str : "";
	}

	public Long getValue_number() {
		return value_number != null ? value_number : 0;
	}
	
	public static ASTNode getInstance(Long value_number) {
		return new ASTNode_ConstValue(value_number);
	}
	
	public static ASTNode getInstance(String value_str) {
		return new ASTNode_ConstValue(value_str);
	}

}
