package de.gerrygames.the5zig.clientviaversion.asm;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;

import java.lang.reflect.Method;
import java.security.CodeSource;

public class ByteClassLoader {

	public static Class loadClass(byte[] b, String className) {
		//override classDefine (as it is protected) and define the class.
		Class clazz = null;
		try {
			ClassLoader loader = ClientViaVersion.class.getClassLoader().getParent();
			Class cls = loader.getClass().getSuperclass().getSuperclass();
			Method method = cls.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class, CodeSource.class);

			// protected method invocaton
			method.setAccessible(true);
			try {
				clazz = (Class) method.invoke(loader, className, b, 0, b.length, null);
			} finally {
				method.setAccessible(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return clazz;
	}

}
