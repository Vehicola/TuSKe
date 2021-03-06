package me.tuke.sktuke.register;

import javax.annotation.Nullable;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public abstract class SimpleType<T>{
	
	private String variableName;
	private String name;
	private String pattern;
	private Class<T> clz;
	public SimpleType(Class<T> clz, String name){
		this(clz, name, name, ".+");
	}
	public SimpleType(Class<T> clz, String name, String pattern){
		this(clz, name, pattern, ".+");
	}
	public SimpleType(Class<T> clz, String name, String pattern, String variableName){
		this.clz = clz;
		this.name = name;
		this.pattern = pattern;
		this.variableName = variableName;
	}
	
	public abstract String toString(T arg0, int arg1);
	
	public abstract String toVariableNameString(T arg0);
	
	public T parse(String arg0, ParseContext arg1){
		return null;
	}
	public boolean canParse(ParseContext pc){
		return true;
	}
	
	public void register(){
		Classes.registerClass(new ClassInfo<T>(clz, name.toLowerCase().replaceAll("\\s+", "")).user(pattern).name(name).parser(new Parser<T>(){

			@Override
			public String getVariableNamePattern() {
				return variableName;
			}

			@Override
			public boolean canParse(ParseContext context) {
				return SimpleType.this.canParse(context);
			}
			@Override
			@Nullable
			public T parse(String arg0, ParseContext arg1) {
				return SimpleType.this.parse(arg0, arg1);
			}

			@Override
			public String toString(T arg0, int arg1) {
				return SimpleType.this.toString(arg0, arg1);
			}

			@Override
			public String toVariableNameString(T arg0) {
				return SimpleType.this.toVariableNameString(arg0);
			}}));
		
		
	}

}
