package fr.umlv.ir3.corba.generator;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Olivier Boitel
 *
 * this class represent all the common declarations used to generate applet
 * and proxy squeletons
 */
public class GeneratorInterface {
	
	// liste des methodes de l'interface IDL
	private Class javaInterface;
	// liste des numeros d'instructions correspondant aux methodes
	private int [] instructionsNumber;
	
	private String classPrefix;
	
	private byte applet_CLA;
	
	/**
	 * Constructor, this constructor must be bulid ton construct object
	 * @param javaInterface list of the method interface from java interface
	 * @param AppletID appletID for the javacard ie A00000000201, it must include PackageID (here A000000002)
	 * @param PackageID packageID for the javacard A0000000002
	 * @param appletCLA CLA for the javacard (ie 0x86)
	 *
	 * @param prefix ?
	 */
	//TODO : lbarbisan - A quoi sert l'argument prefix 
	public GeneratorInterface(Class javaInterface, String AppletID, String PackageID,  byte appletCLA, String prefix)
	{
		this.javaInterface = javaInterface;
		this.instructionsNumber = new int [javaInterface.getMethods().length];
		generateInstructionNumbers();
		
		this.classPrefix = prefix;
		this.applet_CLA = appletCLA; 
	}
	
	/**
	 * this method generate all number for the instructions used in the applet
	 * those number are used to call methods in the applet 
	 * 
	 */
	private void generateInstructionNumbers(){
		int increment = 10;
		this.instructionsNumber[0] = increment;
		
		for (int i = 1; i < this.javaInterface.getMethods().length; i++) {
			increment += 10;
			this.instructionsNumber[i] = increment; 
		}
	}
	
	/**
	 * this method gives all the necessary declarations to use and 
	 * call methods in teh applet 
	 * @return the array of all declarations
	 */
	public String[] getDeclaredInstruction()
	{
		int nbMethods = javaInterface.getMethods().length;
		
		String[] declarations = new String [nbMethods];
		
		for(int i=0; i < nbMethods; i++)
		{
			Method m = javaInterface.getMethods()[i];
			
			declarations[i] ="final static byte "
				+ m.getName().toUpperCase()+" = (byte)0x"+this.instructionsNumber[i];  
		}
		
		return declarations;
	}
	
	public String[] getInstructionNames()
	{
		int nbMethods = javaInterface.getMethods().length;
		
		String[] declarations = new String [nbMethods];
		
		for(int i=0; i < nbMethods; i++)
		{
			Method m = javaInterface.getMethods()[i];
			
			declarations[i] = m.getName();  
		}
		
		return declarations;
	}
	
	public String getClassPrefix() {
		return classPrefix;
	}
	
	public void setClassPrefix(String classPrefix) {
		this.classPrefix = classPrefix;
	}
	
	/**
	 * @return Returns the javaInterface.
	 */
	public Class getJavaInterface() {
		return javaInterface;
	}

	/**
	 * @return Returns the applet_CLA.
	 */
	public byte getApplet_CLA() {
		return applet_CLA;
	}
	
}