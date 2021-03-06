/*
 * Created on 22 oct. 2005 by roussel
 */
package fr.umlv.ir3.corba.calculator.client;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import com.sun.corba.se.impl.logging.UtilSystemException;

import opencard.cflex.service.CFlex32CardService;
import opencard.cflex.util.Util;
import opencard.core.service.CardRequest;
import opencard.core.service.CardServiceException;
import opencard.core.service.SmartCard;
import opencard.core.terminal.CardTerminal;
import opencard.core.terminal.CardTerminalException;
import opencard.core.terminal.CardTerminalRegistry;
import opencard.core.terminal.ResponseAPDU;
import opencard.core.util.HexString;
import opencard.core.util.OpenCardPropertyLoadingException;
import opencard.opt.terminal.ISOCommandAPDU;

/**
 * Client for javacard calculator applett
 * @author lbarbisan
 *
 */
public class RPNClient {


	//  This applet is designed to respond to the following
	//  class of instructions.
	final static byte CalculatorApplet_CLA = (byte)0x86;

	//  Instruction set for CalculatorApplet
	final static byte PUSH = (byte)0x10;
	final static byte POP = (byte) 0x20;
	final static byte RESULT = (byte)0x30;
	final static byte CLEAR = (byte) 0x40;
	

 /**
 * Main methode 
 * @param args args pass in the commande line
 * @throws CardTerminalException CardTerminalException
 * @throws OpenCardPropertyLoadingException OpenCardPropertyLoadingException
 * @throws CardServiceException CardServiceException
 * @throws ClassNotFoundException ClassNotFoundException
 * @throws UnsupportedEncodingException UnsupportedEncodingException
 */
public static void main(String[] args) throws CardTerminalException, OpenCardPropertyLoadingException, CardServiceException, ClassNotFoundException, UnsupportedEncodingException {

	//Try to starrt service
    if (SmartCard.isStarted() == false) {
      SmartCard.start();
    }
    
	//List terminal on the computer
	CardTerminalRegistry ctr = CardTerminalRegistry.getRegistry();
    
	CardTerminal terminal=null;
	SmartCard sm = null;
	CardRequest cr = null;
	
	for (Enumeration terminals = ctr.getCardTerminals();terminals.hasMoreElements();) {
      terminal = (CardTerminal) terminals.nextElement(); 
      int slots = printTerminalInfos(terminal);
      for (int j = 0; j < slots; j++) {
        printSlotInfos(terminal, j);
      }
    }

	if(terminal==null)
	{
		throw new NullPointerException("Couldn't not retrieve a card reader");
	}
	
	//Wait for insert card
    if(terminal.isCardPresent(0)==false)
    {
    	System.out.println("Re-insert/Insert your card ...");
    	cr = new CardRequest(CardRequest.NEWCARD, terminal, null);	
    }
    else
    {
    	cr = new CardRequest(CardRequest.ANYCARD, terminal, null);
    }
    sm = SmartCard.waitForCard(cr);
    
    if(sm==null)
    {
    	throw new NullPointerException("Error when waiting for card to become ready");
    }

	//Test application
    CFlex32CardService javacard = (CFlex32CardService) sm.getCardService(
        CFlex32CardService.class, true);
    javacard.selectApplication(HexString.parseHexString("A00000000201"));
    
    try {
		javacard.allocateChannel();

		ResponseAPDU res;
		//TODO : faire un client plus poussé
		byte[] number;
		number = Util.ShortToBytePair((short)2) ;
		res = javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,PUSH,(byte)0,(byte)0,number,2));
		number = Util.ShortToBytePair((short)4) ;
		res = javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,PUSH,(byte)0,(byte)0,number,2));
		number = Util.ShortToBytePair((short)6) ;
		res = javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,PUSH,(byte)0,(byte)0,number,2));
		number = Util.ShortToBytePair((short)1) ;
		res = javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,PUSH,(byte)0,(byte)0,number,2));
		
		number = new byte[2];
		number[0] = (byte)('+');
		res = javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,RESULT,(byte)0,(byte)0,number,1));
		number[0] = (byte)('+');
		res = javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,RESULT,(byte)0,(byte)0,number,1));
		number[0] = (byte)('+');
		number[1] = 0;
		res = javacard.sendAPDU(new ISOCommandAPDU(CalculatorApplet_CLA,RESULT,(byte)0,(byte)0,number,2));
      	
		System.out.println("Result : " + Util.BytePairToShort(res.getBuffer()[0], res.getBuffer()[1]));
		
    } finally {
      javacard.releaseChannel();
    }
  }

  private static int printTerminalInfos(CardTerminal terminal) {
    // First of all print all information stored about this reader
    System.err.println("Address: " + terminal.getAddress() + "\n" + "Name:    "
        + terminal.getName() + "\n" + "Type:    " + terminal.getType() + "\n"
        + "Slots:   " + terminal.getSlots() + "\n");
    return terminal.getSlots();
  }
  
  private static void printSlotInfos(CardTerminal terminal, int aSlotID) {
    try {
      // First print the ID of the slot
      System.err.println("Info for slot ID: " + aSlotID);
      if (terminal.isCardPresent(aSlotID)) {
        System.err.println("card present: yes");
        // If there is a card in the slot print the ATR the OCF got form this
        // card
        System.err.println("ATR: "
            + HexString.hexify(terminal.getCardID(aSlotID).getATR()));
        // As we do not have a driver for this card we cannot interpret this ATR
      } else
        System.err.println("card present: no");
    } catch (CardTerminalException e) {
      e.printStackTrace();
    }
  }

}
