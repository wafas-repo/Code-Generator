package absyn;

public class CompoundExp extends Exp {

    public VarDecList decs;
    public ExpList exps;

    public CompoundExp(int pos, VarDecList decs, ExpList exps) {
        this.pos = pos;
        this.decs = decs;
        this.exps = exps;
    }

    @Override
    public void accept(AbsynVisitor visitor, int level, boolean isAddr, int scope) {
        visitor.visit( this, level, false, scope );

    }
    
}
