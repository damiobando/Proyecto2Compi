/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author raque
 */
public class Select extends Command{
    
  public Expression E;
  public Case Ca;
  public Command Co;
  
  public Select (Expression eAST, Case caAST, Command coAST,
                    SourcePosition thePosition) {
    super (thePosition);
    E = eAST;
    Ca = caAST;
    Co = coAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitSelect(this, o);
  }

}
