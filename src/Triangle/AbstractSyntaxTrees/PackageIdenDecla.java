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
public class PackageIdenDecla extends PackageDeclaration {

    public PackageIdenDecla (Identifier iAST, Declaration dAST, SourcePosition thePosition) {
        super (thePosition);
        I = iAST;
        D = dAST;
    }
    
    @Override
    public Object visit(Visitor v, Object o) {
        return v.visitpackageIdenDecla(this, o);
    }
    
    public Identifier I;
    public Declaration D;
    
}
