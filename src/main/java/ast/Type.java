package ast;

public enum Type {
    INT("int"),
    CHAR("char"),
    NOTHING("nothing");

    private Object type;

    private Type(Object type) {
        this.type = type;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
    
}
