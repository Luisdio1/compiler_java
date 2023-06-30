package ast;

import java.util.List;

public class ArrayType {
    
    private Type type;
    private List<Integer> size;
    private boolean brack;
    
    public ArrayType(Type type, List<Integer> size, boolean brack) {
        this.type = type;
        this.size = size;
        this.brack = brack;
    }
    
    public Type getType() {
        return type;
    }

    public List<Integer> getSize() {
        return size;
    }

    public boolean getBrack() {
        return brack;
    }

    public String getArrayType() {
        String arrayType = this.type.toString();
        if (this.brack) {
            arrayType += "[]";
        }
        for (int i = 0; i < size.size(); i++) {
            arrayType += "[" + size.get(i) + "]";
        }
        return arrayType;

    }
    
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
