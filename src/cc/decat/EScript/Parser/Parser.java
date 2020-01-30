package cc.decat.EScript.Parser;

import java.util.ArrayList;

import cc.decat.EScript.AST.*;
import cc.decat.EScript.Lexter.Lexter;
import cc.decat.EScript.Token.Token;
import cc.decat.EScript.Token.TokenType;

/***
 * _语法分析
 * 
 * @author Decat
 *
 */
public class Parser {

	private Lexter lexter = null;
	private ArrayList<Token> tokenList = null;
	private int size = 0;
	private int index = 0;

	public Parser(Lexter lexter) {
		this.lexter = lexter;
		this.tokenList = lexter.ScanAll();
		this.size = tokenList.size();
	}

	private boolean check() {
		return index >= 0 && index < size;
	}

	private boolean check(int i) {
		return i >= 0 && i < size;
	}

	private Token peek(int i) {
		int current_index = index + i;
		if (check(current_index)) {
			Token t = tokenList.get(current_index);
			System.out.println("->\t" + t.getToken() + "\t" + t.getType());
			return t;
		}
		return null;
	}

	private Token peek() {
		if (check()) {
			Token t = tokenList.get(index);
			System.out.println("->\t" + t.getToken() + "\t" + t.getType());
			return t;
		}
		return null;
	}

	private boolean matchToken(String t, int i) {
		if (peek(i).getToken().equals(t)) {
			return true;
		}
		return false;
	}

	private boolean matchToken(String t) {
		if (peek().getToken().equals(t)) {
			return true;
		}
		return false;
	}

	private boolean matchToken(TokenType t, int i) {
		int curIndex = index + i;
		if (peek(curIndex).getType() == t) {
			return true;
		}
		return false;
	}

	private boolean matchToken(TokenType t) {
		if (peek().getType() == t) {
			return true;
		}
		return false;
	}

	private Token next() {
		return tokenList.get(index++);
	}

	Token token = null;

	public ASTNode ParserAll() {
		ASTNode_StmtList root = new ASTNode_StmtList();
		while (check()) {
			if (matchToken(TokenType.Function)) {
				root.insertAstNode(_Function());
			}
		}
		return root;
	}

	/* function test(arg1,arg2,arg3){} */
	private ASTNode _Function() {
		next();// function
		if (matchToken(TokenType.Symbol) && matchToken("(", 1)) {
			String funcName = null;
			ArrayList<String> params = null;
			ASTNode funcBody = null;
			funcName = next().getToken();
			params = _Function_Params();
			if (matchToken("{")) {
				// {
				funcBody = _stmtList();
				return new ASTNode_Function(funcName, params, funcBody);
			}
		}
		return null;
	}

	private ArrayList<String> _Function_Params() {
		next(); // (
		ArrayList<String> params = new ArrayList<String>();
		Token t = null;
		while (!matchToken(")")) {
			if (matchToken(TokenType.Symbol)) {
				t = next();
				params.add(t.getToken());
				if (matchToken(",")) {
					next();
				}
			} else {
				// error
			}
		}
		next();// )
		return params;
	}

	private ASTNode _stmtList() {
		ASTNode_StmtList stmtList = new ASTNode_StmtList();
		next(); // {
		while (!matchToken("}")) {
			stmtList.insertAstNode(_stmt());
		}
		next(); // }
		return stmtList;
	}

	private ASTNode _stmt() {
		ASTNode stmt = null;
		Token t = peek();
		if (matchToken(TokenType.Var)) {
			// 定义变量
			return _Defvar();
		} else if (matchToken(TokenType.Symbol) && matchToken("(", 1)) {
			return _callFunction(false);
		} else if (matchToken(TokenType.Symbol) && matchToken("=", 1)) {
			return _Assign();
		} else if (matchToken(TokenType.While)) {
			return _While();
		} else if (matchToken(TokenType.Return)) {
			return _Return();
		} else if(matchToken(TokenType.If)) {
			return _if();
		} else if(matchToken(TokenType.Break)) {
			return _break();
		} else if(matchToken(TokenType.Continue)) {
			return _continue();
		}
		return stmt;
	}

	private ASTNode _continue() {
		// TODO Auto-generated method stub
		next();
		return new ASTNode_Continue();
	}

	private ASTNode _break() {
		// TODO Auto-generated method stub
		next();
		return new ASTNode_Break();
	}

	private ASTNode _if() {
		// TODO Auto-generated method stub
		next();//if
		ASTNode codExpr = _conditionExpr();
		ASTNode then_body = _stmtList();
		ASTNode else_body = null;
		if(peek().getType() == TokenType.Else) {
			next();
			else_body = _stmtList();
		} else {
			//err
		}
		return new ASTNode_If(codExpr, then_body, else_body);
	}

	private ASTNode _Return() {
		// TODO Auto-generated method stub
		System.out.println("entry return");
		next();// return
		return new ASTNode_Return(_expr());
	}

	private ASTNode _Defvar() {

		//var symbol = expr
		next();// var
		Token peek = peek();
		if (peek.getType() == TokenType.Symbol) {
			next();
			if(peek().getType() == TokenType.Assign) {
				next();
				return new ASTNode_DefVar(peek.getToken(), _expr());
			} else {
				return new ASTNode_DefVar(peek.getToken(), null);
			}
		}
		return null;
	}

	private ASTNode _Assign() {
		String symbol = next().getToken();
		next(); // =
		return new ASTNode_Assign(symbol, _expr());
	}

	private ASTNode _callFunction(boolean isReturnValue) {
		String funcName = next().getToken();
		ArrayList<ASTNode> params = new ArrayList<ASTNode>();
		next();// (
		int ii = 0;
		while (!matchToken(")")) {
			if (matchToken(TokenType.String)) {
				params.add(new ASTNode_ConstValue(next().getToken()));
			} else {
				params.add(_expr());
			}
			if (matchToken(",")) {
				next();// ,
			}
		}
		// expr var string

		next();// )
		
		return new ASTNode_CallFunction(funcName, params, isReturnValue);
	}

	private ASTNode _While() {
		next();// while
		ASTNode codExpr = _conditionExpr();
		ASTNode body = _stmtList();
		return new ASTNode_While(codExpr, body);
	}

	private ASTNode _conditionExpr() {
		ASTNode ExprNode = null;
		ASTNode_StmtList stmtList = new ASTNode_StmtList();
		next();// (
		while(true) {
			ASTNode left = _expr();
			ASTNode comparOp = _compareOp();
			ASTNode right = _expr();
			stmtList.insertAstNode(new ASTNode_ConditionExpr(left, comparOp, right));
			if(peek().getType() == TokenType.AND) {
				next();
				stmtList.insertAstNode(new ASTNode_LogicalOp(LogicalOperator_Type.And));
			} else if(peek().getType() == TokenType.OR) {
				next();
				stmtList.insertAstNode(new ASTNode_LogicalOp(LogicalOperator_Type.Or));
			} else {
				break;
			}
		}
		next();// )
		return stmtList;
	}

	private ASTNode _compareOp() {
		CompareOperator_Type type = null;
		switch (peek().getType()) {
		case EQ:
			type = CompareOperator_Type.EQ;
			break;
		case NEQ:
			type = CompareOperator_Type.NEQ;
			break;
		case GT:
			type = CompareOperator_Type.GT;
			break;
		case GTE:
			type = CompareOperator_Type.GTE;
			break;
		case LT:
			type = CompareOperator_Type.LT;
			break;
		case LTE:
			type = CompareOperator_Type.LTE;
			break;
		default:
			return null;
		}
		next();
		return new ASTNode_CompareOperator(type);
	}

	private ASTNode _expr() {
		ASTNode v1 = _term();
		ASTNode v2 = null;
		ASTNode_Operator_Binary op = _binary_op();
		while (op != null && op.getType() != null
				&& (op.getOpType() == BinaryOp_Type.Add || op.getOpType() == BinaryOp_Type.Sub)) {
			next();
			v2 = _term();
			v1 = new ASTNode_Expr(v1, op, v2);
			op = _binary_op();
		}
		return v1;
	}

	private ASTNode_Operator_Binary _binary_op() {
		BinaryOp_Type type = null;
		switch (peek().getType()) {
		case Add:
			type = BinaryOp_Type.Add;
			break;
		case Sub:
			type = BinaryOp_Type.Sub;
			break;
		case Mul:
			type = BinaryOp_Type.Mul;
			break;
		case Div:
			type = BinaryOp_Type.Div;
			break;
		case Mod:
			type = BinaryOp_Type.Mod;
			break;
		default:
			return null;
		}
		return new ASTNode_Operator_Binary(type);
	}

	private ASTNode _term() {
		ASTNode v1 = _factor();
		ASTNode v2 = null;
		ASTNode_Operator_Binary op = _binary_op();
		while (op != null && (op.getOpType() == BinaryOp_Type.Mul || op.getOpType() == BinaryOp_Type.Div || op.getOpType() == BinaryOp_Type.Mod)) {
			next();
			v2 = _term();
			v1 = new ASTNode_Expr(v1, op, v2);
			op = _binary_op();
		}
		return v1;
	}

	private ASTNode _factor() {
		if (matchToken(TokenType.Number)) {
			return new ASTNode_ConstValue(new Long(next().getToken()));
		} else if (matchToken(TokenType.Symbol) && peek(1).getToken().equals("(")) {
			return _callFunction(true);
		} else if (matchToken(TokenType.Symbol)) {
			Token peek = peek();
			return new ASTNode_Var(next().getToken());
		} else if(matchToken(TokenType.LC)) {
			next();
			ASTNode expr = _expr();
			next();
			return expr;
		}
		return null;
	}

}
