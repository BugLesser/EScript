package cc.decat.EScript.Lexter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import cc.decat.EScript.Token.Token;
import cc.decat.EScript.Token.TokenManager;
import cc.decat.EScript.Token.TokenType;

public final class Lexter {
	private FileInputStream fin = null;
	private byte[] source;
	private int length = 0;
	private int index = 0;
	private int column = 0;
	private int row = 0;

	public Lexter(String filename) {
		try {
			fin = new FileInputStream(filename);
			length = fin.available();
			source = new byte[length];
			fin.read(source);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
			}
		}
	}

	public boolean check() {
		return index >= 0 && index < length;
	}

	byte peek(int i) {
		if (index + i >= 0 && index + i < length) {
			return source[index + i];
		}
		return '\0';
	}

	private char peek() {
		if (index >= 0 && index < length) {
			return (char) source[index];
		}
		return '\0';
	}

	private static boolean isAlpha(char b) {
		return ((b <= 'z') && (b >= 'a')) || ((b <= 'Z') && (b >= 'A')) || (b == '_');
	}

	private static boolean isNumber(char b) {
		return (b >= '0') && (b <= '9');
	}

	private static boolean isBlank(char b) {
		return b == ' ' || b == '\t';
	}

	private static boolean isNewLine(char b) {
		return b == '\n' || b == '\r';
	}

	private char next_() {
		if (!check()) {
			return '\0';
		}
		column++;
		return (char) source[index++];
	}

	public ArrayList<Token> ScanAll() {
		char b;
		TokenType type = null;
		ArrayList<Token> TokenList = new ArrayList<Token>();
		while (check()) {
			String temp = "";
			b = peek();
			if (isBlank(b)) {
				while (isBlank(peek())) {
					next_();
				}
				continue;
			} else if (isNewLine(b)) {
				column = 0;
				row++;
				next_();
				continue;
			} else if (b == '/') {
				if (peek(1) == '/') {
					// 单行注释
					while (!isNewLine(next_())) {
					}
					continue;
				} else if (peek(1) == '*') {
					/* 多行注释 */
					next_();// /
					next_();// *
					while (!(next_() == '*' && peek(1) == '/')) {
					}
					continue;
				}
				temp += next_();
				type = TokenManager.getType(temp);
			} else if (isNumber(b)) {
				while (isNumber(peek())) {
					temp += next_();
				}
				type = TokenType.Number;
			} else if (b == '"') {
				// 字符串
				next_();
				while (peek() != '"') {
					temp += next_();
				}
				next_();
				type = TokenType.String;
			} else if (isAlpha(b)) {
				temp += next_();
				while (isAlpha(peek()) || isNumber(peek())) {
					temp += next_();
				}
				if (TokenManager.isKey(temp)) {
					type = TokenManager.getType(temp);
				} else {
					type = TokenType.Symbol;
				}
			} else if (b == '(' || b == ')' || b == '[' || b == ']' || b == '{' || b == '}' || b == ';' || b == ','
					|| b == '.') {
				temp += next_();
				type = TokenManager.getType(temp);
			} else if (b == '+' || b == '-' || b == '*' || b == '/' || b == '%' || b == '=' || b == '>' || b == '<' || b == '&'
					|| b == '|' || b == '!'/* other */) {
				temp += next_();
				if (TokenManager.isKey(temp + peek())) {
					temp += next_();
				}
				type = TokenManager.getType(temp);
			} else {
				next_();
				continue;
			}
			TokenList.add(new Token(type, temp, row, column));
		}
		return TokenList;
	}
}
