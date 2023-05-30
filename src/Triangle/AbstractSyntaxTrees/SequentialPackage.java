/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Triangle.AbstractSyntaxTrees;

import Triangle.SyntacticAnalyzer.SourcePosition;

/**
 *
 * @author antho
 */
public class SequentialPackage extends PackageDeclaration {
  
    public SequentialPackage(PackageDeclaration pAST, PackageDeclaration pAST2, SourcePosition packagePos) {
        super (packagePos);
        P1 = pAST;
        P2 = pAST2;
    }  

  public PackageDeclaration P1, P2;  

    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitSequentialPackage(this, o);
    }
    
}
