package cc.decat.EScript.Test;


import java.util.ArrayList;

import cc.decat.EScript.AST.ASTNode;
import cc.decat.EScript.Lexter.Lexter;
import cc.decat.EScript.MRunTime.MRunTime;
import cc.decat.EScript.Parser.Parser;
import cc.decat.EScript.Runtime.Runtime;
import cc.decat.EScript.Runtime.ZL;
import cc.decat.EScript.gen.CodeGen;

public class Tests {
	
	
	//支持  if else return function while var print +-*÷% && || continue break
	
	public static void main(String[] args) {
		
		String filename = "E:\\C\\test1.es";
		Lexter lex = new Lexter(filename);
		Parser parser = new Parser(lex);
		ASTNode root = parser.ParserAll();
		ArrayList<ZL> gen1 = Runtime.execRootNode(root);
		ArrayList<ZL> gen2 = CodeGen.gen(gen1);
		System.out.println();
		System.out.println();
		MRunTime.exec(gen2);
	}
}
