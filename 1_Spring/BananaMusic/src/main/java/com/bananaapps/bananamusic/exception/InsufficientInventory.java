package com.bananaapps.bananamusic.exception;

public class InsufficientInventory extends Exception{
	private static final long serialVersionUID = 1L;

	public InsufficientInventory(String message)  {
      super(message);
   }
}
