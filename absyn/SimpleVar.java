package absyn;

public class SimpleVar extends Var {

    public String name;

    public SimpleVar(int pos, String name) {

        this.pos = pos;
        this.name = name;   
        
    }

    @Override
    public void accept(AbsynVisitor visitor, int level, boolean isAddr, int scope) {
        visitor.visit( this, level, false, scope);

    }
    
}
