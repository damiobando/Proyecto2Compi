/*
 * @(#)Parser.java                        2.1 2003/10/07
 *
 * Copyright (C) 1999, 2003 D.A. Watt and D.F. Brown
 * Dept. of Computing Science, University of Glasgow, Glasgow G12 8QQ Scotland
 * and School of Computer and Math Sciences, The Robert Gordon University,
 * St. Andrew Street, Aberdeen AB25 1HG, Scotland.
 * All rights reserved.
 *
 * This software is provided free for educational use only. It may
 * not be used for commercial purposes without the prior written permission
 * of the authors.
 */
package Triangle.SyntacticAnalyzer;

import Triangle.AbstractSyntaxTrees.AST;
import Triangle.ErrorReporter;
import Triangle.AbstractSyntaxTrees.ActualParameter;
import Triangle.AbstractSyntaxTrees.ActualParameterSequence;
import Triangle.AbstractSyntaxTrees.ArrayAggregate;
import Triangle.AbstractSyntaxTrees.ArrayExpression;
import Triangle.AbstractSyntaxTrees.ArrayTypeDenoter;
import Triangle.AbstractSyntaxTrees.AssignCommand;
import Triangle.AbstractSyntaxTrees.BinaryExpression;
import Triangle.AbstractSyntaxTrees.Body;
import Triangle.AbstractSyntaxTrees.CallCommand;
import Triangle.AbstractSyntaxTrees.CallExpression;
import Triangle.AbstractSyntaxTrees.Case;
import Triangle.AbstractSyntaxTrees.CharacterCase;
import Triangle.AbstractSyntaxTrees.CharacterExpression;
import Triangle.AbstractSyntaxTrees.CharacterLiteral;
import Triangle.AbstractSyntaxTrees.Command;
import Triangle.AbstractSyntaxTrees.CommandBody;
import Triangle.AbstractSyntaxTrees.ConstActualParameter;
import Triangle.AbstractSyntaxTrees.ConstDeclaration;
import Triangle.AbstractSyntaxTrees.ConstFormalParameter;
import Triangle.AbstractSyntaxTrees.Declaration;
import Triangle.AbstractSyntaxTrees.DotVname;
import Triangle.AbstractSyntaxTrees.EmptyActualParameterSequence;
import Triangle.AbstractSyntaxTrees.EmptyCommand;
import Triangle.AbstractSyntaxTrees.EmptyFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.Expression;
import Triangle.AbstractSyntaxTrees.FieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.ForDo;
import Triangle.AbstractSyntaxTrees.ForIn;
import Triangle.AbstractSyntaxTrees.ForUntil;
import Triangle.AbstractSyntaxTrees.ForWhile;
import Triangle.AbstractSyntaxTrees.FormalParameter;
import Triangle.AbstractSyntaxTrees.FormalParameterSequence;
import Triangle.AbstractSyntaxTrees.FuncActualParameter;
import Triangle.AbstractSyntaxTrees.FuncDeclaration;
import Triangle.AbstractSyntaxTrees.FuncFormalParameter;
import Triangle.AbstractSyntaxTrees.Identifier;
import Triangle.AbstractSyntaxTrees.IfCommand;
import Triangle.AbstractSyntaxTrees.IfExpression;
import Triangle.AbstractSyntaxTrees.IntegerCase;
import Triangle.AbstractSyntaxTrees.IntegerExpression;
import Triangle.AbstractSyntaxTrees.IntegerLiteral;
import Triangle.AbstractSyntaxTrees.LetCommand;
import Triangle.AbstractSyntaxTrees.LetExpression;
import Triangle.AbstractSyntaxTrees.LongIdentifier;
import Triangle.AbstractSyntaxTrees.LiteralCaseCommand;
import Triangle.AbstractSyntaxTrees.MultipleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleArrayAggregate;
import Triangle.AbstractSyntaxTrees.MultipleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.MultipleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.MultipleRecordAggregate;
import Triangle.AbstractSyntaxTrees.Operator;
import Triangle.AbstractSyntaxTrees.PackCommandBody;
import Triangle.AbstractSyntaxTrees.PackageDeclaration;
import Triangle.AbstractSyntaxTrees.Private;
import Triangle.AbstractSyntaxTrees.ProcActualParameter;
import Triangle.AbstractSyntaxTrees.ProcDeclaration;
import Triangle.AbstractSyntaxTrees.ProcFormalParameter;
import Triangle.AbstractSyntaxTrees.Program;
import Triangle.AbstractSyntaxTrees.Rec;
import Triangle.AbstractSyntaxTrees.RecordAggregate;
import Triangle.AbstractSyntaxTrees.RecordExpression;
import Triangle.AbstractSyntaxTrees.RecordTypeDenoter;
import Triangle.AbstractSyntaxTrees.RepeatDoUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatDoWhileCommand;
import Triangle.AbstractSyntaxTrees.RepeatTimesCommand;
import Triangle.AbstractSyntaxTrees.RepeatUntilCommand;
import Triangle.AbstractSyntaxTrees.RepeatWhileCommand;
import Triangle.AbstractSyntaxTrees.Select;
import Triangle.AbstractSyntaxTrees.SequentialCase;
import Triangle.AbstractSyntaxTrees.SequentialCaseLiteral;
import Triangle.AbstractSyntaxTrees.SequentialCaseRange;
import Triangle.AbstractSyntaxTrees.SequentialCommand;
import Triangle.AbstractSyntaxTrees.SequentialDeclaration;
import Triangle.AbstractSyntaxTrees.SequentialProcFunc;
import Triangle.AbstractSyntaxTrees.SimpleTypeDenoter;
import Triangle.AbstractSyntaxTrees.SimpleVname;
import Triangle.AbstractSyntaxTrees.SingleActualParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleArrayAggregate;
import Triangle.AbstractSyntaxTrees.SingleCaseLiteral;
import Triangle.AbstractSyntaxTrees.SingleFieldTypeDenoter;
import Triangle.AbstractSyntaxTrees.SingleFormalParameterSequence;
import Triangle.AbstractSyntaxTrees.SingleRecordAggregate;
import Triangle.AbstractSyntaxTrees.SkipCommand;
import Triangle.AbstractSyntaxTrees.SubscriptVname;
import Triangle.AbstractSyntaxTrees.TypeDeclaration;
import Triangle.AbstractSyntaxTrees.TypeDenoter;
import Triangle.AbstractSyntaxTrees.UnaryExpression;
import Triangle.AbstractSyntaxTrees.VarActualParameter;
import Triangle.AbstractSyntaxTrees.VarDeclaration;
import Triangle.AbstractSyntaxTrees.VarDeclarationExpression;
import Triangle.AbstractSyntaxTrees.VarFormalParameter;
import Triangle.AbstractSyntaxTrees.Vname;
import Triangle.AbstractSyntaxTrees.VnameExpression;
import Triangle.AbstractSyntaxTrees.ComplexLongIdentifier;
import Triangle.AbstractSyntaxTrees.IdForExpression;
import Triangle.AbstractSyntaxTrees.IdentifierVarname;
import Triangle.AbstractSyntaxTrees.PackageIdenDecla;
import Triangle.AbstractSyntaxTrees.SequentialPackage;
import Triangle.AbstractSyntaxTrees.SimpleLongIdentifier;
import Triangle.AbstractSyntaxTrees.SimpleVarname;

public class Parser {

    private Scanner lexicalAnalyser;
    private ErrorReporter errorReporter;
    private Token currentToken;
    private SourcePosition previousTokenPosition;
    private HTMLgenerator HTMLgenerator; // se agrega la clase encargado de generar los archivos HTML - Raquel

    public Parser(Scanner lexer, ErrorReporter reporter) {
        lexicalAnalyser = lexer;
        HTMLgenerator = new HTMLgenerator(lexer.getSourceFile().filename);// se le env?a el archivo con el c?digo - Raquel
        errorReporter = reporter;
        previousTokenPosition = new SourcePosition();
    }

    // accept checks whether the current token matches tokenExpected.
    // If so, fetches the next token.
    // If not, reports a syntactic error.
    void accept(int tokenExpected) throws SyntaxError {
        if (currentToken.kind == tokenExpected) {
            previousTokenPosition = currentToken.position;
            currentToken = lexicalAnalyser.scan();
        } else {
            syntacticError("\"%\" expected here", Token.spell(tokenExpected));
        }
    }

    void acceptIt() {
        previousTokenPosition = currentToken.position;
        currentToken = lexicalAnalyser.scan();
    }

    // start records the position of the start of a phrase.
    // This is defined to be the position of the first
    // character of the first token of the phrase.
    void start(SourcePosition position) {
        position.start = currentToken.position.start;
    }

    // finish records the position of the end of a phrase.
    // This is defined to be the position of the last
    // character of the last token of the phrase.
    void finish(SourcePosition position) {
        position.finish = previousTokenPosition.finish;
    }

    void syntacticError(String messageTemplate, String tokenQuoted) throws SyntaxError {
        SourcePosition pos = currentToken.position;
        errorReporter.reportError(messageTemplate, tokenQuoted, pos);
        throw (new SyntaxError());
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // PROGRAMS
    //
    ///////////////////////////////////////////////////////////////////////////////
    // Modificado por Anthony Jiménez
    // Program ::= Body
    public Program parseProgram() {

        Program programAST = null;

        previousTokenPosition.start = 0;
        previousTokenPosition.finish = 0;
        currentToken = lexicalAnalyser.scan();
        try {
            Body bodyAST = parseBody();
            programAST = new Program(bodyAST, previousTokenPosition);
            if (currentToken.kind != Token.EOT) {
                syntacticError("\"%\" not expected after end of program",
                        currentToken.spelling);
            }
        } catch (SyntaxError s) {
            return null;
        }
        return programAST;
    }

    // Body ::= Package-Declaration* Command
    Body parseBody() throws SyntaxError {
        SourcePosition bodyPos = new SourcePosition();
        Body bodyAST = null;
        start(bodyPos);
        //Se elimina el reconocimiento sint?ctico de paquetes
        /*if (currentToken.kind == Token.PACKAGE) {
            PackageDeclaration packAST = parsePackageDeclaration();
            Command cAST = parseCommand();
            finish(bodyPos);
            bodyAST = new PackCommandBody(packAST, cAST, previousTokenPosition);
        } else {
            Command cAST = parseCommand();
            finish(bodyPos);
            bodyAST = new CommandBody(cAST, bodyPos);
        }*/
        Command cAST = parseCommand();
        finish(bodyPos);
        bodyAST = new CommandBody(cAST, bodyPos);
        return bodyAST;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // LITERALS
    //
    ///////////////////////////////////////////////////////////////////////////////
    // parseIntegerLiteral parses an integer-literal, and constructs
    // a leaf AST to represent it.
    IntegerLiteral parseIntegerLiteral() throws SyntaxError {
        IntegerLiteral IL = null;

        if (currentToken.kind == Token.INTLITERAL) {
            previousTokenPosition = currentToken.position;
            String spelling = currentToken.spelling;
            IL = new IntegerLiteral(spelling, previousTokenPosition);
            currentToken = lexicalAnalyser.scan();
        } else {
            IL = null;
            syntacticError("integer literal expected here", "");
        }
        return IL;
    }

    // parseCharacterLiteral parses a character-literal, and constructs a leaf
    // AST to represent it.
    CharacterLiteral parseCharacterLiteral() throws SyntaxError {
        CharacterLiteral CL = null;

        if (currentToken.kind == Token.CHARLITERAL) {
            previousTokenPosition = currentToken.position;
            String spelling = currentToken.spelling;
            CL = new CharacterLiteral(spelling, previousTokenPosition);
            currentToken = lexicalAnalyser.scan();
        } else {
            CL = null;
            syntacticError("character literal expected here", "");
        }
        return CL;
    }

    // parseIdentifier parses an identifier, and constructs a leaf AST to
    // represent it.
    Identifier parseIdentifier() throws SyntaxError {
        Identifier I = null;
        if (currentToken.kind == Token.IDENTIFIER) {
            previousTokenPosition = currentToken.position; // a $ b
            String spelling = currentToken.spelling;
            I = new Identifier(spelling, previousTokenPosition);
            currentToken = lexicalAnalyser.scan();
        } else {
            I = null;
            syntacticError("identifier expected here", "");
        }
        return I;
    }

    // parseOperator parses an operator, and constructs a leaf AST to
    // represent it.
    Operator parseOperator() throws SyntaxError {
        Operator O = null;

        if (currentToken.kind == Token.OPERATOR) {
            previousTokenPosition = currentToken.position;
            String spelling = currentToken.spelling;
            O = new Operator(spelling, previousTokenPosition);
            currentToken = lexicalAnalyser.scan();
        } else {
            O = null;
            syntacticError("operator expected here", "");
        }
        return O;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // COMMANDS
    //
    ///////////////////////////////////////////////////////////////////////////////
    // parseCommand parses the command, and constructs an AST
    // to represent its phrase structure.
    Command parseCommand() throws SyntaxError {
        Command commandAST = null; // in case there's a syntactic error

        SourcePosition commandPos = new SourcePosition();

        start(commandPos);
        commandAST = parseSingleCommand();
        while (currentToken.kind == Token.SEMICOLON) {
            acceptIt();
            Command c2AST = parseSingleCommand();
            finish(commandPos);
            commandAST = new SequentialCommand(commandAST, c2AST, commandPos);
        }
        return commandAST;
    }

    /*
   * Autor: Alonso Garita
   * ElseIfSequence ::= '|' Expression then Command ElseIfSequence
   * | else Command
     */
    Command parseElseIfSequence() throws SyntaxError {
        Command elseIfSequence = null;

        SourcePosition elseIfPos = new SourcePosition();
        start(elseIfPos);

        if (currentToken.kind == Token.PIPE) { // ElseIfSequence ::= '|' Expression then Command ElseIfSequence
            acceptIt();
            Expression eAST = parseExpression(); // Expresion del if
            accept(Token.THEN);
            Command c1AST = parseCommand(); // Comando del then
            Command c2AST = parseElseIfSequence(); // Comandos del else...
            finish(elseIfPos);
            elseIfSequence = new IfCommand(eAST, c1AST, c2AST, elseIfPos);
        } else if (currentToken.kind == Token.ELSE) { // ElseIfSequence ::= 'else' Command
            acceptIt();
            elseIfSequence = parseCommand(); // Comando del else
            finish(elseIfPos);
        } else {
            syntacticError("Expected \"|\" or \"else\" here ", "");
        }

        return elseIfSequence;
    }

    Command parseSingleCommand() throws SyntaxError {
        Command commandAST = null; // in case there's a syntactic error

        SourcePosition commandPos = new SourcePosition();
        start(commandPos);

        switch (currentToken.kind) {

            case Token.IDENTIFIER: {
                /*** Código del Proyecto I que permitía LongIdentifiers para referirse a declaraciones dentro de paquetes
                LongIdentifier lAST = null;
                Identifier iAST2 = null;
                Identifier iAST1 = parseIdentifier();
                if (currentToken.kind == Token.DOLAR) { // a $ b
                    acceptIt();
                    iAST2 = parseIdentifier();
                    lAST = new ComplexLongIdentifier(iAST1, iAST2, commandPos);
                } else {
                    lAST = new SimpleLongIdentifier(iAST1, commandPos);
                }
                if (currentToken.kind == Token.LPAREN) {
                    acceptIt(); // Long-Identifier (Actual-Parameter-Sequence)
                    ActualParameterSequence apsAST = parseActualParameterSequence();
                    accept(Token.RPAREN);
                    finish(commandPos);
                    commandAST = new CallCommand(lAST, apsAST, commandPos);
                } else {
                    Vname vnameAST = null;
                    Vname vAST1 = null;
                    if (iAST2 == null) {
                        vAST1 = parseVarname(iAST1);
                        vnameAST = new SimpleVarname(vAST1, commandPos);
                    } else {
                        vAST1 = parseVarname(iAST2);
                        vnameAST = new IdentifierVarname(iAST1, vAST1, commandPos);
                    }
                    accept(Token.BECOMES);
                    Expression eAST = parseExpression();
                    finish(commandPos);
                    commandAST = new AssignCommand(vnameAST, eAST, commandPos);
                }
                ***/
                
                //Código recuperado para el Proyecto II de Triángulo Original que no soporta paquetes
                Identifier iAST = parseIdentifier();
                if (currentToken.kind == Token.LPAREN) {
                    acceptIt();
                    ActualParameterSequence apsAST = parseActualParameterSequence();
                    accept(Token.RPAREN);
                    finish(commandPos);
                    commandAST = new CallCommand(iAST, apsAST, commandPos);
                } else {
                    Vname vAST = parseVarname(iAST);
                    accept(Token.BECOMES);
                    Expression eAST = parseExpression();
                    finish(commandPos);
                    commandAST = new AssignCommand(vAST, eAST, commandPos);
                }
            }
            break;

            // Alonso: Eliminar la opcion de begin Command end
            // case Token.BEGIN:
            // acceptIt();
            // commandAST = parseCommand();
            // accept(Token.END);
            // break;
            case Token.LET: {
                // Alonso
                acceptIt();
                Declaration dAST = parseDeclaration();
                accept(Token.IN);
                Command cAST = parseCommand(); // Parsing de Command en lugar de SingleCommand
                accept(Token.END); // Espera terminar en END
                finish(commandPos);
                commandAST = new LetCommand(dAST, cAST, commandPos);
            }
            break;

            case Token.IF: // If...then-Pipe...then-Else Alonso
            // IfCommand ::= if Expression then Command ElseIfSequence end
            {
                acceptIt();
                Expression eAST = parseExpression(); // Expresion del tf
                accept(Token.THEN);
                Command c1AST = parseCommand(); // Comando del then
                Command c2AST = parseElseIfSequence(); // Comandos del else (ElseIfSequence)
                accept(Token.END);
                finish(commandPos);
                commandAST = new IfCommand(eAST, c1AST, c2AST, commandPos);
            }
            break;

            case Token.REPEAT: // Variantes de repeat - Alonso
            {
                acceptIt();
                commandAST = parseRepeat();
            }
            break;

            case Token.SKIP: // Skip Command "skip" - Alonso
            {
                acceptIt();
                finish(commandPos);
                commandAST = new SkipCommand(commandPos);
            }
            break;

            case Token.FOR: {
                acceptIt();

                Declaration ieAST = null;

                SourcePosition declarationPos = new SourcePosition();
                start(declarationPos);

                Identifier iAST = parseIdentifier();
                if (currentToken.kind == Token.BECOMES) {
                    accept(Token.BECOMES);
                    Expression eAST = parseExpression();
                    finish(declarationPos);
                    ieAST = new IdForExpression(iAST, eAST, declarationPos);

                    accept(Token.TWODOTS);
                    Expression e1AST = parseExpression();
                    if (currentToken.kind == Token.DO) {
                        acceptIt();
                        Command cAST = parseCommand();
                        accept(Token.END);
                        finish(commandPos); // "for" Identifier ":=" Expression ".." Expression "do" Command "end"
                        commandAST = new ForDo(ieAST, e1AST, cAST, commandPos); // ForDo Parsing - Raquel
                    } else if (currentToken.kind == Token.WHILE) {
                        acceptIt();
                        Expression e2AST = parseExpression();
                        accept(Token.DO);
                        Command cAST = parseCommand();
                        accept(Token.END);
                        finish(commandPos); // "for" Identifier ":=" Expression ".." Expression "while" Expression "do"
                        // Command "end"
                        commandAST = new ForWhile(ieAST, e1AST, e2AST, cAST, commandPos); // ForWhile Parsing - Raquel
                    } else {
                        accept(Token.UNTIL);
                        Expression e2AST = parseExpression();
                        accept(Token.DO);
                        Command cAST = parseCommand();
                        accept(Token.END);
                        finish(commandPos); // "for" Identifier ":=" Expression ".." Expression "until" Expression "do"
                        // Command "end"
                        commandAST = new ForUntil(ieAST, e1AST, e2AST, cAST, commandPos); // ForUntil Parsing - Raquel
                    }

                } else {
                    accept(Token.IN);
                    Expression eAST = parseExpression();
                    finish(declarationPos);
                    ieAST = new IdForExpression(iAST, eAST, declarationPos);
                    accept(Token.DO);
                    Command cAST = parseCommand();
                    accept(Token.END);
                    finish(commandPos); // "for" Identifier "in" Expression "do" Command "end"
                    commandAST = new ForIn(ieAST, cAST, commandPos); // ForIn Parsing - Raquel
                }
            }
            break;

            case Token.SELECT: {
                acceptIt();
                Expression eAST = parseExpression();
                accept(Token.FROM);
                Case caAST = parseCases();
                Command coAST = null;
                if (currentToken.kind == Token.ELSE) {
                    accept(Token.ELSE);
                    coAST = parseCommand();
                }

                accept(Token.END);
                finish(commandPos); // "select" Expression "from" Cases ["else" Command] "end"
                commandAST = new Select(eAST, caAST, coAST, commandPos); // Select Parsing - Raquel
            }
            break;

            // Alonso: Eliminar la opcion del comando vacio.
            // case Token.SEMICOLON:
            // case Token.END:
            // case Token.ELSE:
            // case Token.IN:
            // case Token.EOT:
            // finish(commandPos);
            // commandAST = new EmptyCommand(commandPos);
            // break;
            default:
                syntacticError("\"%\" cannot start a command",
                        currentToken.spelling);
                break;
        }

        return commandAST;
    }

    // Alonso
    Command parseRepeat() throws SyntaxError { // Parse Repeats
        // Cuando entra aqui ya se leyo Token.REPEAT
        Command repeatCommand = null;

        SourcePosition repeatPos = new SourcePosition();
        start(repeatPos);

        switch (currentToken.kind) {

            case Token.WHILE: { // repeat while Expression do Command end
                acceptIt();
                Expression eAST = parseExpression();
                accept(Token.DO);
                Command cAST = parseCommand();
                accept(Token.END);
                finish(repeatPos);
                repeatCommand = new RepeatWhileCommand(eAST, cAST, repeatPos);
            }
            break;

            case Token.UNTIL: { // repeat until Expression do Command end
                acceptIt();
                Expression eAST = parseExpression();
                accept(Token.DO);
                Command cAST = parseCommand();
                accept(Token.END);
                finish(repeatPos);
                repeatCommand = new RepeatUntilCommand(eAST, cAST, repeatPos);
            }
            break;

            case Token.DO: { // repeat do Command...
                acceptIt();
                Command cAST = parseCommand();
                if (currentToken.kind == Token.WHILE) { // repeat do Command while Expression end
                    acceptIt();
                    Expression eAST = parseExpression();
                    accept(Token.END);
                    finish(repeatPos);
                    repeatCommand = new RepeatDoWhileCommand(cAST, eAST, repeatPos);
                } else if (currentToken.kind == Token.UNTIL) { // repeat do Command until Expression end
                    acceptIt();
                    Expression eAST = parseExpression();
                    accept(Token.END);
                    finish(repeatPos);
                    repeatCommand = new RepeatDoUntilCommand(cAST, eAST, repeatPos);
                } else {
                    syntacticError("These are expected instead of \"%\":\"while\" or \"until\".", currentToken.spelling);
                }
            }
            break;

            default: {
                try { // repeat Expression times do Command end
                    Expression eAST = parseExpression();
                    accept(Token.TIMES);
                    accept(Token.DO);
                    Command cAST = parseCommand();
                    accept(Token.END);
                    repeatCommand = new RepeatTimesCommand(eAST, cAST, repeatPos);
                } catch (SyntaxError s) {
                    syntacticError("Wrong repeat sintax near \"%\" at ", currentToken.spelling);
                }
            }
            break;

        }

        return repeatCommand;
    }
    ///////////////////////////////////////////////////////////////////////////////
    //
    // EXPRESSIONS
    //
    ///////////////////////////////////////////////////////////////////////////////

    Expression parseExpression() throws SyntaxError {
        Expression expressionAST = null; // in case there's a syntactic error

        SourcePosition expressionPos = new SourcePosition();

        start(expressionPos);

        switch (currentToken.kind) {

            case Token.LET: {
                acceptIt();
                Declaration dAST = parseDeclaration();
                accept(Token.IN);
                Expression eAST = parseExpression();
                finish(expressionPos);
                expressionAST = new LetExpression(dAST, eAST, expressionPos);
            }
            break;

            case Token.IF: {
                acceptIt();
                Expression e1AST = parseExpression();
                accept(Token.THEN);
                Expression e2AST = parseExpression();
                accept(Token.ELSE);
                Expression e3AST = parseExpression();
                finish(expressionPos);
                expressionAST = new IfExpression(e1AST, e2AST, e3AST, expressionPos);
            }
            break;

            default:
                expressionAST = parseSecondaryExpression();
                break;
        }
        return expressionAST;
    }

    Expression parseSecondaryExpression() throws SyntaxError {
        Expression expressionAST = null; // in case there's a syntactic error

        SourcePosition expressionPos = new SourcePosition();
        start(expressionPos);

        expressionAST = parsePrimaryExpression();
        while (currentToken.kind == Token.OPERATOR) {
            Operator opAST = parseOperator();
            Expression e2AST = parsePrimaryExpression();
            expressionAST = new BinaryExpression(expressionAST, opAST, e2AST,
                    expressionPos);
        }
        return expressionAST;
    }

    Expression parsePrimaryExpression() throws SyntaxError {
        Expression expressionAST = null; // in case there's a syntactic error

        SourcePosition expressionPos = new SourcePosition();
        start(expressionPos);

        switch (currentToken.kind) {

            case Token.INTLITERAL: {
                IntegerLiteral ilAST = parseIntegerLiteral();
                finish(expressionPos);
                expressionAST = new IntegerExpression(ilAST, expressionPos);
            }
            break;

            case Token.CHARLITERAL: {
                CharacterLiteral clAST = parseCharacterLiteral();
                finish(expressionPos);
                expressionAST = new CharacterExpression(clAST, expressionPos);
            }
            break;

            case Token.LBRACKET: {
                acceptIt();
                ArrayAggregate aaAST = parseArrayAggregate();
                accept(Token.RBRACKET);
                finish(expressionPos);
                expressionAST = new ArrayExpression(aaAST, expressionPos);
            }
            break;

            case Token.LCURLY: {
                acceptIt();
                RecordAggregate raAST = parseRecordAggregate();
                accept(Token.RCURLY);
                finish(expressionPos);
                expressionAST = new RecordExpression(raAST, expressionPos);
            }
            break;

            case Token.IDENTIFIER: {
                /*** C?digo del Proyecto I que permit?a LongIdentifiers para referirse a declaraciones dentro de paquetes
                LongIdentifier lAST = null;
                Identifier iAST2 = null;
                Identifier iAST1 = parseIdentifier();
                if (currentToken.kind == Token.DOLAR) { // a $ b
                    acceptIt();
                    iAST2 = parseIdentifier();
                    lAST = new ComplexLongIdentifier(iAST1, iAST2, expressionPos);
                } else {
                    lAST = new SimpleLongIdentifier(iAST1, expressionPos);
                }
                if (currentToken.kind == Token.LPAREN) {
                    acceptIt(); // Long-Identifier (Actual-Paremeter-Sequence)
                    ActualParameterSequence apsAST = parseActualParameterSequence();
                    accept(Token.RPAREN);
                    finish(expressionPos);
                    expressionAST = new CallExpression(lAST, apsAST, expressionPos);
                } else {
                    Vname vnameAST = null;
                    Vname vAST1 = null;
                    if (iAST2 == null) {
                        vAST1 = parseVarname(iAST1);
                        vnameAST = new SimpleVarname(vAST1, expressionPos);
                    } else {
                        vAST1 = parseVarname(iAST2);
                        vnameAST = new IdentifierVarname(iAST1, vAST1, expressionPos);
                    }
                    finish(expressionPos);
                    expressionAST = new VnameExpression(vnameAST, expressionPos);
                }
                ***/
                
                //C?digo recuperado para el Proyecto II de Tri?ngulo Original que no soporta paquetes
                Identifier iAST= parseIdentifier();
                if (currentToken.kind == Token.LPAREN) {
                    acceptIt();
                    ActualParameterSequence apsAST = parseActualParameterSequence();
                    accept(Token.RPAREN);
                    finish(expressionPos);
                    expressionAST = new CallExpression(iAST, apsAST, expressionPos);

                } else {
                    Vname vAST = parseVarname(iAST);
                    finish(expressionPos);
                    expressionAST = new VnameExpression(vAST, expressionPos);
                }
            }
            break;

            case Token.OPERATOR: {
                Operator opAST = parseOperator();
                Expression eAST = parsePrimaryExpression();
                finish(expressionPos);
                expressionAST = new UnaryExpression(opAST, eAST, expressionPos);
            }
            break;

            case Token.LPAREN:
                acceptIt();
                expressionAST = parseExpression();
                accept(Token.RPAREN);
                break;

            default:
                syntacticError("\"%\" cannot start an expression",
                        currentToken.spelling);
                break;

        }
        return expressionAST;
    }

    RecordAggregate parseRecordAggregate() throws SyntaxError {
        RecordAggregate aggregateAST = null; // in case there's a syntactic error

        SourcePosition aggregatePos = new SourcePosition();
        start(aggregatePos);

        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        Expression eAST = parseExpression();

        if (currentToken.kind == Token.COMMA) {
            acceptIt();
            RecordAggregate aAST = parseRecordAggregate();
            finish(aggregatePos);
            aggregateAST = new MultipleRecordAggregate(iAST, eAST, aAST, aggregatePos);
        } else {
            finish(aggregatePos);
            aggregateAST = new SingleRecordAggregate(iAST, eAST, aggregatePos);
        }
        return aggregateAST;
    }

    ArrayAggregate parseArrayAggregate() throws SyntaxError {
        ArrayAggregate aggregateAST = null; // in case there's a syntactic error

        SourcePosition aggregatePos = new SourcePosition();
        start(aggregatePos);

        Expression eAST = parseExpression();
        if (currentToken.kind == Token.COMMA) {
            acceptIt();
            ArrayAggregate aAST = parseArrayAggregate();
            finish(aggregatePos);
            aggregateAST = new MultipleArrayAggregate(eAST, aAST, aggregatePos);
        } else {
            finish(aggregatePos);
            aggregateAST = new SingleArrayAggregate(eAST, aggregatePos);
        }
        return aggregateAST;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // VALUE-OR-VARIABLE NAMES
    //
    ///////////////////////////////////////////////////////////////////////////////
    // Author Mari?ngeles
    // V-name ::= [Identifier "$" ] Var-name
    Vname parseVname() throws SyntaxError {
        /*Vname vnameAST = null; // in case there's a syntactic error
        SourcePosition vnamePos = new SourcePosition();
        start(vnamePos);
        Identifier iAST1 = parseIdentifier();
        if (currentToken.kind == Token.DOLAR) {
            acceptIt();
            Vname vAST1 = parseVarname(null);
            finish(vnamePos);
            vnameAST = new IdentifierVarname(iAST1, vAST1, vnamePos);
        } else {
            finish(vnamePos);
            Vname vAST1 = parseVarname(iAST1);
            vnameAST = new SimpleVarname(vAST1, vnamePos);
        }
        return vnameAST;*/
        
        //Recuperado de Triángulo original
        Vname vnameAST = null; // in case there's a syntactic error
        Identifier iAST = parseIdentifier();
        vnameAST = parseVarname(iAST);
        return vnameAST;
    }

    // Autor Mariangeles
    // Var-name ::= Identifier ("." Identifier | "[" Expression "]" ) *
    Vname parseVarname(Identifier identifierAST) throws SyntaxError {
        /*SourcePosition vnamePos = new SourcePosition();
        Identifier iAST1 = null;
        start(vnamePos);

        if (identifierast == null) {
            iAST1 = parseIdentifier();
        } else {
            iAST1 = identifierast;
            vnamePos = identifierast.position;
        }

        Vname vnameAST = new SimpleVname(iAST1, vnamePos);
        while (currentToken.kind == Token.DOT
                || currentToken.kind == Token.LBRACKET) {

            if (currentToken.kind == Token.DOT) {
                acceptIt();
                Identifier i2AST = parseIdentifier();
                vnameAST = new DotVname(vnameAST, i2AST, vnamePos);
            } else {
                acceptIt();
                Expression eAST = parseExpression();
                accept(Token.RBRACKET);
                finish(vnamePos);
                vnameAST = new SubscriptVname(vnameAST, eAST, vnamePos);
            }
        }
        return vnameAST;*/
        
        //Recuperado de Triángulo Original
        SourcePosition vnamePos = new SourcePosition();
        vnamePos = identifierAST.position;
        Vname vAST = new SimpleVname(identifierAST, vnamePos);

        while (currentToken.kind == Token.DOT ||
               currentToken.kind == Token.LBRACKET) {

          if (currentToken.kind == Token.DOT) {
            acceptIt();
            Identifier iAST = parseIdentifier();
            vAST = new DotVname(vAST, iAST, vnamePos);
          } else {
            acceptIt();
            Expression eAST = parseExpression();
            accept(Token.RBRACKET);
            finish(vnamePos);
            vAST = new SubscriptVname(vAST, eAST, vnamePos);
          }
        }
        return vAST;
    }
    ///////////////////////////////////////////////////////////////////////////////
    //
    // DECLARATIONS
    //
    ///////////////////////////////////////////////////////////////////////////////

    Declaration parseDeclaration() throws SyntaxError {
        Declaration declarationAST = null; // in case there's a syntactic error

        SourcePosition declarationPos = new SourcePosition();
        start(declarationPos);
        declarationAST = parseCompoundDeclaration();
        while (currentToken.kind == Token.SEMICOLON) {
            acceptIt();
            Declaration d2AST = parseCompoundDeclaration();
            finish(declarationPos);
            declarationAST = new SequentialDeclaration(declarationAST, d2AST,
                    declarationPos);
        }
        return declarationAST;
    }

    Declaration parseCompoundDeclaration() throws SyntaxError {
        Declaration declarationAST = null; // in case there's a syntactic error
        SourcePosition declarationPos = new SourcePosition();
        start(declarationPos);

        switch (currentToken.kind) {

            case Token.REC: {
                acceptIt();
                Declaration pfAST = parseProcFuncsDeclaration();
                accept(Token.END);
                finish(declarationPos);
                declarationAST = new Rec(pfAST, declarationPos);
            }
            break;

            case Token.PRIVATE: {
                acceptIt();
                Declaration d1AST = parseDeclaration();
                accept(Token.IN);
                Declaration d2AST = parseDeclaration();
                accept(Token.END);
                finish(declarationPos);
                declarationAST = new Private(d1AST, d2AST, declarationPos);
            }
            break;

            default:
                declarationAST = parseSingleDeclaration();
                break;

        }
        return declarationAST;

    }

    Declaration parseSingleDeclaration() throws SyntaxError {
        Declaration declarationAST = null; // in case there's a syntactic error

        SourcePosition declarationPos = new SourcePosition();
        start(declarationPos);

        switch (currentToken.kind) {

            case Token.CONST: {
                acceptIt();
                Identifier iAST = parseIdentifier();
                accept(Token.IS);
                Expression eAST = parseExpression();
                finish(declarationPos);
                declarationAST = new ConstDeclaration(iAST, eAST, declarationPos);
            }
            break;
            // "var" Identifier (":" TypeDenoter |":=" Expression)
            // Mari?ngeles
            case Token.VAR: {
                acceptIt();
                Identifier iAST = parseIdentifier();

                if (currentToken.kind == Token.COLON) {
                    acceptIt();
                    TypeDenoter tAST = parseTypeDenoter();
                    finish(declarationPos);
                    declarationAST = new VarDeclaration(iAST, tAST, declarationPos);
                } else {

                    accept(Token.BECOMES);
                    Expression eAST = parseExpression();
                    finish(declarationPos);
                    declarationAST = new VarDeclarationExpression(iAST, eAST, declarationPos);
                }
                break;
            }

            case Token.PROC: {
                // Alonso
                // proc Identifier ( Formal-Parameter-Sequence ) ~ SingleCommand ->
                // proc Identifier ( Formal-Parameter-Sequence ) ~ Command end

                acceptIt();
                Identifier iAST = parseIdentifier();
                accept(Token.LPAREN);
                FormalParameterSequence fpsAST = parseFormalParameterSequence();
                accept(Token.RPAREN);
                accept(Token.IS);
                Command cAST = parseCommand();
                accept(Token.END);
                finish(declarationPos);
                declarationAST = new ProcDeclaration(iAST, fpsAST, cAST, declarationPos);
            }
            break;

            case Token.FUNC: {
                acceptIt();
                Identifier iAST = parseIdentifier();
                accept(Token.LPAREN);
                FormalParameterSequence fpsAST = parseFormalParameterSequence();
                accept(Token.RPAREN);
                accept(Token.COLON);
                TypeDenoter tAST = parseTypeDenoter();
                accept(Token.IS);
                Expression eAST = parseExpression();
                finish(declarationPos);
                declarationAST = new FuncDeclaration(iAST, fpsAST, tAST, eAST,
                        declarationPos);
            }
            break;

            case Token.TYPE: {
                acceptIt();
                Identifier iAST = parseIdentifier();
                accept(Token.IS);
                TypeDenoter tAST = parseTypeDenoter();
                finish(declarationPos);
                declarationAST = new TypeDeclaration(iAST, tAST, declarationPos);
            }
            break;

            default:
                syntacticError("\"%\" cannot start a declaration", currentToken.spelling);
                break;

        }
        return declarationAST;

    }

    // Parse ProcFuncs - Alonso
    // Proc-Funcs ::= Proc-Func ( "|" Proc-Func )+ << En el texto
    // Proc-Funcs ::= Proc-Func "|" Proc-Func ( "|" Proc-Func )* << Lo que hace la
    // funcion
    Declaration parseProcFuncsDeclaration() throws SyntaxError {
        SequentialProcFunc procFuncsAST = null; // in case there's a syntactic error **

        SourcePosition procFuncsPos = new SourcePosition();
        start(procFuncsPos);

        Declaration procFunc1AST = parseProcFuncDeclaration();
        accept(Token.PIPE);
        Declaration procFunc2AST = parseProcFuncDeclaration();
        finish(procFuncsPos);
        procFuncsAST = new SequentialProcFunc(procFunc1AST, procFunc2AST, procFuncsPos); // Obliga a que hayan al menos 2

        while (currentToken.kind == Token.PIPE) {
            acceptIt();
            Declaration procFuncAST = parseProcFuncDeclaration();
            finish(procFuncsPos);
            procFuncsAST = new SequentialProcFunc(procFuncsAST, procFuncAST, procFuncsPos);
        }

        return procFuncsAST;
    }

    // Parse ProcFunc Declaration - Alonso
    // ProcFunc ::= proc Identifier ( Formal-Parameter-Sequence ) ~ Command end
    // | func Identifier ( Formal-Parameter-Sequence ) : Type-denoter ~ Expression
    Declaration parseProcFuncDeclaration() throws SyntaxError {
        Declaration procFuncAST = null;

        SourcePosition procFuncPos = new SourcePosition();

        start(procFuncPos);
        if (currentToken.kind == Token.PROC) {
            acceptIt();
            Identifier iAST = parseIdentifier();
            accept(Token.LPAREN);
            FormalParameterSequence fpsAST = parseFormalParameterSequence();
            accept(Token.RPAREN);
            accept(Token.IS);
            Command cAST = parseCommand();
            accept(Token.END);
            finish(procFuncPos);
            procFuncAST = new ProcDeclaration(iAST, fpsAST, cAST, procFuncPos);
        } else if (currentToken.kind == Token.FUNC) {
            acceptIt();
            Identifier iAST = parseIdentifier();
            accept(Token.LPAREN);
            FormalParameterSequence fpsAST = parseFormalParameterSequence();
            accept(Token.RPAREN);
            accept(Token.COLON);
            TypeDenoter tAST = parseTypeDenoter();
            accept(Token.IS);
            Expression eAST = parseExpression();
            finish(procFuncPos);
            procFuncAST = new FuncDeclaration(iAST, fpsAST, tAST, eAST, procFuncPos);
        } else {
            syntacticError("\"%\" cannot start a declaration",
                    currentToken.spelling);
        }
        return procFuncAST;
    }

    Declaration parseForDeclaration() throws SyntaxError {
        Declaration declarationAST = null; // in case there's a syntactic error

        SourcePosition declarationPos = new SourcePosition();
        start(declarationPos);

        Identifier iAST = parseIdentifier();
        accept(Token.BECOMES);

        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new VarDeclarationExpression(iAST, eAST, declarationPos);

        return declarationAST;
    }

    Declaration parseForInDeclaration() throws SyntaxError {
        Declaration declarationAST = null; // in case there's a syntactic error

        SourcePosition declarationPos = new SourcePosition();
        start(declarationPos);

        Identifier iAST = parseIdentifier();
        accept(Token.IN);

        Expression eAST = parseExpression();
        finish(declarationPos);
        declarationAST = new VarDeclarationExpression(iAST, eAST, declarationPos);

        return declarationAST;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // PARAMETERS
    //
    ///////////////////////////////////////////////////////////////////////////////
    FormalParameterSequence parseFormalParameterSequence() throws SyntaxError {
        FormalParameterSequence formalsAST;

        SourcePosition formalsPos = new SourcePosition();

        start(formalsPos);
        if (currentToken.kind == Token.RPAREN) {
            finish(formalsPos);
            formalsAST = new EmptyFormalParameterSequence(formalsPos);

        } else {
            formalsAST = parseProperFormalParameterSequence();
        }
        return formalsAST;
    }

    FormalParameterSequence parseProperFormalParameterSequence() throws SyntaxError {
        FormalParameterSequence formalsAST = null; // in case there's a syntactic error;

        SourcePosition formalsPos = new SourcePosition();
        start(formalsPos);
        FormalParameter fpAST = parseFormalParameter();
        if (currentToken.kind == Token.COMMA) {
            acceptIt();
            FormalParameterSequence fpsAST = parseProperFormalParameterSequence();
            finish(formalsPos);
            formalsAST = new MultipleFormalParameterSequence(fpAST, fpsAST,
                    formalsPos);

        } else {
            finish(formalsPos);
            formalsAST = new SingleFormalParameterSequence(fpAST, formalsPos);
        }
        return formalsAST;
    }

    FormalParameter parseFormalParameter() throws SyntaxError {
        FormalParameter formalAST = null; // in case there's a syntactic error;

        SourcePosition formalPos = new SourcePosition();
        start(formalPos);

        switch (currentToken.kind) {

            case Token.IDENTIFIER: {
                Identifier iAST = parseIdentifier();
                accept(Token.COLON);
                TypeDenoter tAST = parseTypeDenoter();
                finish(formalPos);
                formalAST = new ConstFormalParameter(iAST, tAST, formalPos);
            }
            break;

            case Token.VAR: {
                acceptIt();
                Identifier iAST = parseIdentifier();
                accept(Token.COLON);
                TypeDenoter tAST = parseTypeDenoter();
                finish(formalPos);
                formalAST = new VarFormalParameter(iAST, tAST, formalPos);
            }
            break;

            case Token.PROC: {
                acceptIt();
                Identifier iAST = parseIdentifier();
                accept(Token.LPAREN);
                FormalParameterSequence fpsAST = parseFormalParameterSequence();
                accept(Token.RPAREN);
                finish(formalPos);
                formalAST = new ProcFormalParameter(iAST, fpsAST, formalPos);
            }
            break;

            case Token.FUNC: {
                acceptIt();
                Identifier iAST = parseIdentifier();
                accept(Token.LPAREN);
                FormalParameterSequence fpsAST = parseFormalParameterSequence();
                accept(Token.RPAREN);
                accept(Token.COLON);
                TypeDenoter tAST = parseTypeDenoter();
                finish(formalPos);
                formalAST = new FuncFormalParameter(iAST, fpsAST, tAST, formalPos);
            }
            break;

            default:
                syntacticError("\"%\" cannot start a formal parameter",
                        currentToken.spelling);
                break;

        }
        return formalAST;
    }

    ActualParameterSequence parseActualParameterSequence() throws SyntaxError {
        ActualParameterSequence actualsAST;

        SourcePosition actualsPos = new SourcePosition();

        start(actualsPos);
        if (currentToken.kind == Token.RPAREN) {
            finish(actualsPos);
            actualsAST = new EmptyActualParameterSequence(actualsPos);

        } else {
            actualsAST = parseProperActualParameterSequence();
        }
        return actualsAST;
    }

    ActualParameterSequence parseProperActualParameterSequence() throws SyntaxError {
        ActualParameterSequence actualsAST = null; // in case there's a syntactic error

        SourcePosition actualsPos = new SourcePosition();

        start(actualsPos);
        ActualParameter apAST = parseActualParameter();
        if (currentToken.kind == Token.COMMA) {
            acceptIt();
            ActualParameterSequence apsAST = parseProperActualParameterSequence();
            finish(actualsPos);
            actualsAST = new MultipleActualParameterSequence(apAST, apsAST,
                    actualsPos);
        } else {
            finish(actualsPos);
            actualsAST = new SingleActualParameterSequence(apAST, actualsPos);
        }
        return actualsAST;
    }

    ActualParameter parseActualParameter() throws SyntaxError {
        ActualParameter actualAST = null; // in case there's a syntactic error

        SourcePosition actualPos = new SourcePosition();

        start(actualPos);

        switch (currentToken.kind) {

            case Token.IDENTIFIER:
            case Token.INTLITERAL:
            case Token.CHARLITERAL:
            case Token.OPERATOR:
            case Token.LET:
            case Token.IF:
            case Token.LPAREN:
            case Token.LBRACKET:
            case Token.LCURLY: {
                Expression eAST = parseExpression();
                finish(actualPos);
                actualAST = new ConstActualParameter(eAST, actualPos);
            }
            break;

            case Token.VAR: {
                acceptIt();
                Vname vAST = parseVname();
                finish(actualPos);
                actualAST = new VarActualParameter(vAST, actualPos);
            }
            break;

            case Token.PROC: {
                acceptIt();
                Identifier iAST = parseIdentifier();
                finish(actualPos);
                actualAST = new ProcActualParameter(iAST, actualPos);
            }
            break;

            case Token.FUNC: {
                acceptIt();
                Identifier iAST = parseIdentifier();
                finish(actualPos);
                actualAST = new FuncActualParameter(iAST, actualPos);
            }
            break;

            default:
                syntacticError("\"%\" cannot start an actual parameter",
                        currentToken.spelling);
                break;

        }
        return actualAST;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // TYPE-DENOTERS
    //
    ///////////////////////////////////////////////////////////////////////////////
    TypeDenoter parseTypeDenoter() throws SyntaxError {
        TypeDenoter typeAST = null; // in case there's a syntactic error
        SourcePosition typePos = new SourcePosition();

        start(typePos);

        switch (currentToken.kind) {

            case Token.IDENTIFIER: {
                //LongIdentifier iAST = parseLongIdentifier(); // Long-Identifier
                Identifier iAST = parseIdentifier(); // Identifier
                finish(typePos);
                typeAST = new SimpleTypeDenoter(iAST, typePos);
            }
            break;

            case Token.ARRAY: {
                acceptIt();
                IntegerLiteral ilAST = parseIntegerLiteral();
                accept(Token.OF);
                TypeDenoter tAST = parseTypeDenoter();
                finish(typePos);
                typeAST = new ArrayTypeDenoter(ilAST, tAST, typePos);
            }
            break;

            case Token.RECORD: {
                acceptIt();
                FieldTypeDenoter fAST = parseFieldTypeDenoter();
                accept(Token.END);
                finish(typePos);
                typeAST = new RecordTypeDenoter(fAST, typePos);
            }
            break;

            default:
                syntacticError("\"%\" cannot start a type denoter",
                        currentToken.spelling);
                break;

        }
        return typeAST;
    }

    FieldTypeDenoter parseFieldTypeDenoter() throws SyntaxError {
        FieldTypeDenoter fieldAST = null; // in case there's a syntactic error

        SourcePosition fieldPos = new SourcePosition();

        start(fieldPos);
        Identifier iAST = parseIdentifier();
        accept(Token.COLON);
        TypeDenoter tAST = parseTypeDenoter();
        if (currentToken.kind == Token.COMMA) {
            acceptIt();
            FieldTypeDenoter fAST = parseFieldTypeDenoter();
            finish(fieldPos);
            fieldAST = new MultipleFieldTypeDenoter(iAST, tAST, fAST, fieldPos);
        } else {
            finish(fieldPos);
            fieldAST = new SingleFieldTypeDenoter(iAST, tAST, fieldPos);
        }
        return fieldAST;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // CASE
    //
    ///////////////////////////////////////////////////////////////////////////////
    // Author Mariangeles
    // Cases ::= Case+
    Case parseCases() throws SyntaxError {

        Case caseAST = null; // in case there's a syntactic error **

        SourcePosition casePos = new SourcePosition();
        start(casePos);
        caseAST = parseCase();
        while (currentToken.kind == Token.WHEN) {
            // acceptIt();
            Case c2AST = parseCase();
            finish(casePos);
            caseAST = new SequentialCase(caseAST, c2AST, casePos);
        }
        return caseAST;
    }

    // Case ::= "when" Case-Literals "then" Command
    Case parseCase() throws SyntaxError {

        Case caseAST = null; // in case there's a syntactic error **

        SourcePosition casePos = new SourcePosition();
        start(casePos);

        accept(Token.WHEN);

        Case caseAST2 = parseCaseLiterals();

        accept(Token.THEN);
        
        Command command = parseCommand();

        caseAST = new LiteralCaseCommand(caseAST2, command, casePos);

        return caseAST;

    }

    // Case-Literals ::= Case-Range ("|" Case-Range)*
    Case parseCaseLiterals() throws SyntaxError {

        Case caseLSAST = null; // in case there's a syntactic error **

        SourcePosition casePos = new SourcePosition();
        start(casePos);

        caseLSAST = parseCaseRange();
        while (currentToken.kind == Token.PIPE) {
            acceptIt();
            Case cR2AST = parseCaseRange();
            finish(casePos);
            caseLSAST = new SequentialCaseRange(caseLSAST, cR2AST, casePos);
        }
        return caseLSAST;

    }

    // Case-Range ::= Case-Literal [".." Case-Literal]
    Case parseCaseRange() throws SyntaxError {

        Case caseRAST = null; // in case there's a syntactic error **

        SourcePosition casePos = new SourcePosition();
        start(casePos);

        Case c1AST = parseCaseLiteral();
        if (currentToken.kind == Token.TWODOTS) {
            acceptIt();
            Case c2AST = parseCaseLiteral();
            finish(casePos);
            caseRAST = new SequentialCaseLiteral(c1AST, c2AST, casePos);
        } else {
            finish(casePos);
            caseRAST = new SingleCaseLiteral(c1AST, casePos);
        }

        return caseRAST;
    }

    // Case-Literal ::= Integer-Literal | Character-Literal
    Case parseCaseLiteral() throws SyntaxError {
        Case caseLAST = null; // in case there's a syntactic error **

        SourcePosition casePos = new SourcePosition();
        start(casePos);

        switch (currentToken.kind) {

            case Token.INTLITERAL: {
                IntegerLiteral ilAST = parseIntegerLiteral();
                finish(casePos);
                caseLAST = new IntegerCase(ilAST, casePos);
            }
            break;

            case Token.CHARLITERAL: {
                CharacterLiteral clAST = parseCharacterLiteral();
                finish(casePos);
                caseLAST = new CharacterCase(clAST, casePos);
            }
            break;

            default:
                syntacticError("\"%\" cannot start a case literal",
                        currentToken.spelling);
                break;

        }
        return caseLAST;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // PACKAGES
    //
    ///////////////////////////////////////////////////////////////////////////////
    // Package-Declaration ::= "package" Package-Identifier "~"
    // Declaration "end"
    // Creado por Anthony JB
    PackageDeclaration parsePackageDeclaration() throws SyntaxError {
        PackageDeclaration packageAST = null;
        SourcePosition packPos = new SourcePosition();
        start(packPos);
        packageAST = parseSinglePackageDeclaration();
        while (currentToken.kind == Token.PACKAGE) {
            PackageDeclaration packageAST2 = parseSinglePackageDeclaration();
            finish(packPos);
            packageAST = new SequentialPackage(packageAST, packageAST2, packPos);
        }
        return packageAST;
    }

    PackageDeclaration parseSinglePackageDeclaration() throws SyntaxError {
        PackageDeclaration packageAST = null;
        SourcePosition packPos = new SourcePosition();
        start(packPos);
        accept(Token.PACKAGE);
        Identifier iAST = parseIdentifier();
        accept(Token.IS);
        Declaration declarationAST = parseDeclaration();
        accept(Token.END);
        finish(packPos);
        packageAST = new PackageIdenDecla(iAST, declarationAST, packPos);
        return packageAST;
    }

    ///////////////////////////////////////////////////////////////////////////////
    //
    // Long-Identifier
    //
    ///////////////////////////////////////////////////////////////////////////////
//Long-Identifier ::= [ Package-Identifier "$" ] Identifier a $ a , a
//Long-Identifier ::= Identifier ["$"  Identifier] a, a $ a
//Creado por Anthony JB
    //En desuso a partir del Proyecto II
    LongIdentifier parseLongIdentifier() throws SyntaxError {
        LongIdentifier longAST = null;
        SourcePosition longPos = new SourcePosition();
        start(longPos);

        Identifier iAST = parseIdentifier();
        if (currentToken.kind == Token.DOLAR) {
            acceptIt();
            Identifier iAST2 = parseIdentifier();
            finish(longPos);
            longAST = new ComplexLongIdentifier(iAST, iAST2, longPos);
        } else {
            finish(longPos);
            longAST = new SimpleLongIdentifier(iAST, longPos);
        }
        return longAST;
    }

}
