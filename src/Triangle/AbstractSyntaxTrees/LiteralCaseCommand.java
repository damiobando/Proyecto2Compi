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

public class LiteralCaseCommand extends Case {

  public LiteralCaseCommand (Case LitAST, Command cAST, SourcePosition thePosition) {
    super (thePosition);
    LC = LitAST;
    C = cAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitLiteralCaseCommand(this, o);
  }

  public Case LC;
  public Command C;
}

