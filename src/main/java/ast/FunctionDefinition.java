package ast;

import java.util.List;

public class FunctionDefinition extends Definition {
    
    private Type type;
    private String identifier;
    private List<Definition> parameterList;
    private List<Definition> definitionList;
    private List<Statement> statementList;
    
    public FunctionDefinition( Type type, String identifier, List<Definition> parameterList, List<Definition> definitionList, List<Statement> statementList) {
        this.identifier = identifier;
        this.parameterList = parameterList;
        this.type = type;
        this.statementList = statementList;
        this.definitionList = definitionList;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifer) {
        this.identifier = identifer;
    }
    
    public List<Definition> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Definition> parameterList) {
        this.parameterList = parameterList;
    }
    
    public List<Statement> getStatementList() {
        return statementList;
    }

    public void setStatementList(List<Statement> statementList) {
        this.statementList = statementList;
    }

    public List<Definition> getDefinitionList() {
        return definitionList;
    }

    public void setDefinitionList(List<Definition> definitionList) {
        this.definitionList = definitionList;
    }
    
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
