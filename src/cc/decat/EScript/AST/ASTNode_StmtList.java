package cc.decat.EScript.AST;

import java.util.ArrayList;

public class ASTNode_StmtList extends ASTNode{

	ArrayList<ASTNode> stmtList;
	
	public ASTNode_StmtList(ArrayList<ASTNode> stmtList) {
		super(ASTNodeType.StmtList);
		this.stmtList = stmtList;
	}
	
	public ASTNode_StmtList() {
		super(ASTNodeType.StmtList);
		this.stmtList = new ArrayList<ASTNode>();
	}

	public void insertAstNode(ASTNode node) {
		this.stmtList.add(node);
	}

	public ArrayList<ASTNode> getStmtList() {
		return stmtList;
	}

	public void setStmtList(ArrayList<ASTNode> stmtList) {
		this.stmtList = stmtList;
	}
	
	
}
