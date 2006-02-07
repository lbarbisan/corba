package fr.umlv.ir3.corba.calculator;


/**
* fr/umlv/ir3/corba/calculator/StackOverFlowHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from calculator.idl
* mardi 7 f�vrier 2006 13 h 39 CET
*/

abstract public class StackOverFlowHelper
{
  private static String  _id = "IDL:calculator/StackOverFlow:1.0";

  public static void insert (org.omg.CORBA.Any a, fr.umlv.ir3.corba.calculator.StackOverFlow that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static fr.umlv.ir3.corba.calculator.StackOverFlow extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [1];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_string_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "message",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_exception_tc (fr.umlv.ir3.corba.calculator.StackOverFlowHelper.id (), "StackOverFlow", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static fr.umlv.ir3.corba.calculator.StackOverFlow read (org.omg.CORBA.portable.InputStream istream)
  {
    fr.umlv.ir3.corba.calculator.StackOverFlow value = new fr.umlv.ir3.corba.calculator.StackOverFlow ();
    // read and discard the repository ID
    istream.read_string ();
    value.message = istream.read_string ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, fr.umlv.ir3.corba.calculator.StackOverFlow value)
  {
    // write the repository ID
    ostream.write_string (id ());
    ostream.write_string (value.message);
  }

}