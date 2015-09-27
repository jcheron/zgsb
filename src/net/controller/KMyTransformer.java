package net.controller;

import net.ko.controller.KTransformer;

public class KMyTransformer extends KTransformer {
	public static String remplaceEspaces(String value){
		if(value!=null)
			return value.replace(" ", "_");
		return value;
	}
}
