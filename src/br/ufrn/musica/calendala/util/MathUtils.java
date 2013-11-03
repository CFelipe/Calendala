package br.ufrn.musica.calendala.util;

public class MathUtils {

	/** Transforms a given slice coordinate into real coordinates */
	public static int circularize(int givenSlice, int limit) {
		int realSlice = givenSlice % limit;
		if(realSlice < 0) realSlice += limit;
		return realSlice;
	}
}
