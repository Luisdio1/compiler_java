package types;

import ast.Operator;
import java.util.Set;
import org.objectweb.asm.Type;

/**
 * Helper class for type manipulations.
 */
public class TypeUtils {

	public static final Type STRING_TYPE = Type.getType(String.class);
	public static final Type INT_TYPE = Type.getType(int.class);
	public static final Type CHAR_TYPE = Type.getType(char.class);
	public static final Type BOOLEAN_TYPE = Type.getType(boolean.class);

	private TypeUtils() {
	}

	public static Type maxType(Type type1, Type type2) {
		if (type1.equals(STRING_TYPE)) {
			return type1;
		} else if (type2.equals(STRING_TYPE)) {
			return type2;
		} else if (type1.equals(Type.CHAR_TYPE)) {
			return type1;
		} else if (type2.equals(Type.CHAR_TYPE)) {
			return type2;
		} else if (type1.equals(Type.INT_TYPE)) {
			return type1;
		} else if (type2.equals(Type.INT_TYPE)) {
			return type2;
		} else if (type1.equals(Type.BOOLEAN_TYPE)) {
			return type1;
		} else if (type2.equals(Type.BOOLEAN_TYPE)) {
			return type2;
		} else {
			return type1;
		}
	}

	public static Type minType(Type type1, Type type2) {
		if (type1.equals(Type.BOOLEAN_TYPE)) {
			return type1;
		} else if (type2.equals(Type.BOOLEAN_TYPE)) {
			return type2;
		} else if (type1.equals(Type.INT_TYPE)) {
			return type1;
		} else if (type2.equals(Type.INT_TYPE)) {
			return type2;
		} else if (type1.equals(Type.CHAR_TYPE)) {
			return type1;
		} else if (type2.equals(Type.CHAR_TYPE)) {
			return type2;
		} else if (type1.equals(STRING_TYPE)) {
			return type1;
		} else if (type2.equals(STRING_TYPE)) {
			return type2;
		} else {
			return type1;
		}
	}

	public static boolean isLargerOrEqualType(Type type1, Type type2) {
		return type1.getSort() >= type2.getSort();
	}

	public static boolean isAssignable(Type target, Type source) {
		return isLargerOrEqualType(target, source);
	}

	public static Type maxType(Set<Type> types) {
		Type max = null;
		for (Type t : types) {
			if (max == null) {
				max = t;
			}
			max = maxType(max, t);
		}
		return max;
	}

	public static Type minType(Set<Type> types) {
		Type min = null;
		for (Type t : types) {
			if (min == null) {
				min = t;
			}
			min = minType(min, t);
		}
		return min;
	}

	public static boolean isUnaryComparible(Operator op, Type type) {
		switch (op) {
		case PLUS:
			return isNumber(type);
		case MINUS:
			return isNumber(type);
		case NOT:
			return type.equals(Type.BOOLEAN_TYPE);
		default:
			return false;
		}
	}

	public static boolean isNumber(Type type) {
		return type.equals(Type.INT_TYPE);
	}

	public static boolean isNumber(Set<Type> types) {
		for (Type t : types) {
			if (t.equals(Type.INT_TYPE)) {
				return true;
			}
		}
		return false;
	}

	public static Type applyUnary(Operator op, Type type) throws TypeException {
		if (!op.isUnary()) {
			throw new TypeException("Operator " + op + " is not unary");
		}
		if (!TypeUtils.isUnaryComparible(op, type)) {
			throw new TypeException("Type " + type + " is not unary comparible");
		}
		return type;
	}

	public static Type applyBinary(Operator op, Type t1, Type t2) throws TypeException {
		if (op.isRelational()) {
			if (TypeUtils.areComparable(t1, t2)) {
				return Type.BOOLEAN_TYPE;
			} else {
				throw new TypeException("Expressions are not comparable");
			}
		} else if (op.equals(Operator.PLUS)) {
			return maxType(t1, t2);
		} else if (op.equals(Operator.MINUS) || op.equals(Operator.DIV) || op.equals(Operator.TIMES)) {
			if (t1.equals(TypeUtils.STRING_TYPE) || t2.equals(TypeUtils.STRING_TYPE)) {
				throw new TypeException("Expressions cannot be handled as numbers");
			}
			if (t1.equals(Type.BOOLEAN_TYPE) || t2.equals(Type.BOOLEAN_TYPE)) {
				throw new TypeException("Expressions cannot be handled as numbers");
			}
			return maxType(t1, t2);
		} else {
			throw new TypeException("Operator " + op + " not supported");
		}
	}

	public static boolean areComparable(Type type1, Type type2) {
		if (type1.equals(Type.BOOLEAN_TYPE)) {
			return type2.equals(Type.BOOLEAN_TYPE);
		} else if (type1.equals(Type.INT_TYPE)) {
			return type2.equals(Type.INT_TYPE);
		} else if (type1.equals(Type.CHAR_TYPE)) {
			return type2.equals(Type.CHAR_TYPE);
		} else { // string
			return type2.equals(TypeUtils.STRING_TYPE);
		}
	}

}
