package cc.decat.EScript.Token;

import java.util.HashMap;
import java.util.Map;

public final class TokenManager {

	private static final Map<String, TokenType> TokenMap = new HashMap<String, TokenType>();

	static {
		
		/* token map init */
		TokenMap.put("if", TokenType.If);
		TokenMap.put("else", TokenType.Else);
		TokenMap.put("function", TokenType.Function);
		TokenMap.put("return", TokenType.Return);
		TokenMap.put("while", TokenType.While);
		TokenMap.put("for", TokenType.For);
		TokenMap.put("break", TokenType.Break);
		TokenMap.put("continue", TokenType.Continue);
		TokenMap.put("var", TokenType.Var);
		TokenMap.put("class", TokenType.Class);
		TokenMap.put("method", TokenType.Method);
		TokenMap.put("import", TokenType.Import);
		TokenMap.put("from", TokenType.From);
	
		/* operator */
		TokenMap.put("+", TokenType.Add);
		TokenMap.put("-", TokenType.Sub);
		TokenMap.put("*", TokenType.Mul);
		TokenMap.put("/", TokenType.Div);
		TokenMap.put("%", TokenType.Mod);
		
		TokenMap.put("=", TokenType.Assign);
		//TokenMap.put("++", TokenType.Else);
		//TokenMap.put("--", TokenType.For);
		//TokenMap.put("+=", TokenType.For);
		//TokenMap.put("-=", TokenType.Else);
		//TokenMap.put("*=", TokenType.Var);
		//TokenMap.put("/=", TokenType.Class);
		TokenMap.put(">", TokenType.GT);
		TokenMap.put("<", TokenType.LT);
		TokenMap.put(">=", TokenType.GTE);
		TokenMap.put("<=", TokenType.LTE);
		TokenMap.put("==", TokenType.EQ);
		TokenMap.put("!=", TokenType.NEQ);
		TokenMap.put("&&", TokenType.AND);
		TokenMap.put("||", TokenType.OR);
		TokenMap.put("!", TokenType.NOT);
		
		TokenMap.put("&", TokenType.Other);
		TokenMap.put("|", TokenType.Other);
		TokenMap.put("^", TokenType.Other);
		TokenMap.put("~", TokenType.Other);
		TokenMap.put("@", TokenType.Other);
		TokenMap.put("#", TokenType.Other);
		TokenMap.put("'", TokenType.Other);
		TokenMap.put(".", TokenType.Other);
		
		
		TokenMap.put("//", TokenType.Other);
		TokenMap.put("/*", TokenType.Other);
		TokenMap.put("*/", TokenType.Other);
		
		TokenMap.put("(", TokenType.LC);
		TokenMap.put(")", TokenType.RC);
		
		TokenMap.put(",", TokenType.Other);
		TokenMap.put(";", TokenType.Other);
		TokenMap.put("\"", TokenType.Other);
		
		TokenMap.put("{", TokenType.Other);
		TokenMap.put("}", TokenType.Other);
		TokenMap.put("[", TokenType.Other);
		TokenMap.put("]", TokenType.Other);
		TokenMap.put(".", TokenType.Other);
		
	}
	
	public static boolean isKey(String key) {
		for(Map.Entry<String, TokenType>item : TokenMap.entrySet()) {
			if(item.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}
	
	public static TokenType getType(String key) {
		return TokenMap.get(key);
	}

	public static String getKeyword(TokenType type) {
		for(Map.Entry<String, TokenType>item : TokenMap.entrySet()) {
			if(item.getValue() == type) {
				return item.getKey();
			}
		}
		return null;
	}
}
