package fr.umlv.ir3.corba.calculator;


/**
* fr/umlv/ir3/corba/calculator/AppletCalculatorPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from calculator.idl
* mercredi 8 f�vrier 2006 10 h 39 CET
*/

public abstract class AppletCalculatorPOA extends org.omg.PortableServer.Servant
 implements fr.umlv.ir3.corba.calculator.AppletCalculatorOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("result", new java.lang.Integer (0));
    _methods.put ("addNumber", new java.lang.Integer (1));
    _methods.put ("clear", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // calculator/AppletCalculator/result
       {
         try {
           char _operator = in.read_char ();
           short $result = (short)0;
           $result = this.result (_operator);
           out = $rh.createReply();
           out.write_short ($result);
         } catch (fr.umlv.ir3.corba.calculator.InvalidOperator $ex) {
           out = $rh.createExceptionReply ();
           fr.umlv.ir3.corba.calculator.InvalidOperatorHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // calculator/AppletCalculator/addNumber
       {
         try {
           short number = in.read_short ();
           this.addNumber (number);
           out = $rh.createReply();
         } catch (fr.umlv.ir3.corba.calculator.StackOverFlow $ex) {
           out = $rh.createExceptionReply ();
           fr.umlv.ir3.corba.calculator.StackOverFlowHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // calculator/AppletCalculator/clear
       {
         this.clear ();
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:calculator/AppletCalculator:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public AppletCalculator _this() 
  {
    return AppletCalculatorHelper.narrow(
    super._this_object());
  }

  public AppletCalculator _this(org.omg.CORBA.ORB orb) 
  {
    return AppletCalculatorHelper.narrow(
    super._this_object(orb));
  }


} // class AppletCalculatorPOA
