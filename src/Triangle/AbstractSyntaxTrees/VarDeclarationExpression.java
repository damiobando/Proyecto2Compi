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
public class VarDeclarationExpression extends Declaration {

  public VarDeclarationExpression (Identifier iAST, Expression eAST,
                         SourcePosition thePosition) {
    super (thePosition);
    I = iAST;
    E = eAST;
  }

  public Object visit(Visitor v, Object o) {
    return v.visitVarDeclarationExpression(this, o);
  }

  public Identifier I;
  public Expression E;
  public TypeDenoter T;
}