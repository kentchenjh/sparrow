package com.sparrow.kit;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


public class AsmKit {

	private static final Map<Method, String[]> METHOD_NAMES_POOL = new ConcurrentHashMap<>(64);
	
	private static boolean sameType(Type[] types, Class<?>[] classes) {
        if (types.length != classes.length) return false;
        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(classes[i]).equals(types[i])) return false;
        }
        return true;
    }
	
	public static String[] getParamNamesByMethod(Method method) {
		
		if(METHOD_NAMES_POOL.containsKey(method)) return METHOD_NAMES_POOL.get(method);
		
		String clzName = method.getDeclaringClass().getName();
		String[] paramNames = new String[method.getParameterTypes().length];
		
		ClassReader cr;
		try {
			cr = new ClassReader(clzName);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		cr.accept(new ClassVisitor(Opcodes.ASM5) {

			@Override
			public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
				final Type[] args = Type.getArgumentTypes(desc);
                // The method name is the same and the number of parameters is the same
                if (!name.equals(method.getName()) || !sameType(args, method.getParameterTypes())) {
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
                MethodVisitor v = super.visitMethod(access, name, desc, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM5, v) {
                    @Override
                    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                        int i = index - 1;
                        // if it is a static method, the first is the parameter
                        // if it's not a static method, the first one is "this" and then the parameter of the method
                        if (Modifier.isStatic(method.getModifiers())) {
                            i = index;
                        }
                        if (i >= 0 && i < paramNames.length) {
                            paramNames[i] = name;
                        }
                        super.visitLocalVariable(name, desc, signature, start, end, index);
                    }
                };
			}
			
		}, 0);
		METHOD_NAMES_POOL.put(method, paramNames);
		return paramNames;
	}
	
}
