package absyn;

public class CallExp extends Exp {

    public String func;
    public ExpList args;

    public CallExp(int pos, String func, ExpList args) {
        this.pos = pos;
        this.func = func;
        this.args = args;
    }

    @Override
    public void accept(AbsynVisitor visitor, int level, boolean isAddr, int scope) {
        visitor.visit( this, level, false, scope);

    }
    
}
