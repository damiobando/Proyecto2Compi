/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

/**
 *
 * @author maria
 */

import Triangle.SyntacticAnalyzer.SourcePosition;

public class IntegerCase extends  Case{

  public IntegerCase (IntegerLiteral ilAST, SourcePosition thePosition) {
    super (thePosition);
    IC = ilAST;
  }
  public Object visit(Visitor v, Object o) {
    return v.visitIntegerCase(this, o);
  }

  public IntegerLiteral IC;
    
}