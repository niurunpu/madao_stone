package ast;
import java.util.List;
import stone.StoneException;
import stone.NativeFunction;

public class Arguments extends Postfix
{
    public Arguments(List<ASTree> c) { super(c); }
    public int size() { return numChildren(); }
    public Object eval_func(Environment callerEnv, Object value) // defined function
    {
    	if (!(value instanceof Function))
    		throw new StoneException("bad function", this);
    	Function func = (Function)value;
    	ParameterList params = func.parameters();
    	if (size() != params.size())
    		throw new StoneException("bad number argument", this);
    	Environment newEnv = func.makeEnv();
    	int num = 0;
    	for (ASTree t : this)
    		params.eval(newEnv, num++, t.eval(callerEnv));
    	return func.body().eval(newEnv);    				
    } 
    public Object eval(Environment callerEnv, Object value) // raw function
    {
    	if (!(value instanceof NativeFunction))
    		return eval_func(callerEnv, value);
    	NativeFunction func = (NativeFunction)value;
    	int nparams = func.numOfParameters();
    	if (size() != nparams)
    		throw new StoneException("bad number of arguments", this);
    	Object[] args = new Object[nparams];
    	int num = 0;
    	for (ASTree t : this)
    	{
    		ASTree ae = (ASTree)t;
    		args[num++] = ae.eval(callerEnv);
    	}
    	return func.invoke(args, this);
    }    
}
