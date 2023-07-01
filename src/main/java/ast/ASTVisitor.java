package ast;

/**
 * Abstract syntax tree visitor.
 */
public interface ASTVisitor {

    void visit(Program node) throws ASTVisitorException;

    void visit(SpacerStatement node) throws ASTVisitorException;

    void visit(StatementGroup node) throws ASTVisitorException;

    void visit(IdentifierExpression node) throws ASTVisitorException;

    void visit(IntegerLiteralExpression node) throws ASTVisitorException;

    void visit(CharLiteralExpression node) throws ASTVisitorException;

    void visit(StringLiteralExpression node) throws ASTVisitorException;

    void visit(BinaryExpression node) throws ASTVisitorException;

    void visit(UnaryExpression node) throws ASTVisitorException;

    void visit(BinaryCondition node) throws ASTVisitorException;

    void visit(UnaryCondition node) throws ASTVisitorException;

    void visit(ParenthesisExpression node) throws ASTVisitorException;

    void visit(ParenthesisCondition node) throws ASTVisitorException;

    void visit(WhileStatement node) throws ASTVisitorException;

    void visit(PutsStatement node) throws ASTVisitorException;

    void visit(FunctionDefinition node) throws ASTVisitorException;

    void visit(FunctionParameterDefinition node) throws ASTVisitorException;

    void visit(IfStatement node) throws ASTVisitorException;

    void visit(EmptyStatement node) throws ASTVisitorException;

    void visit(FunctionCallStatement node) throws ASTVisitorException;

    void visit(IfElseStatement node) throws ASTVisitorException;

    void visit(ReturnStatement node) throws ASTVisitorException;

    void visit(VariableDefinition node) throws ASTVisitorException;

    void visit(LValueExpression node) throws ASTVisitorException;

    void visit(HeaderDefinition node) throws ASTVisitorException;

    void visit(ArrayType node) throws ASTVisitorException;

    void visit(FunctionCallExpression node) throws ASTVisitorException;

}
