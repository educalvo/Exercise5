package nl.ru.ai.calvodeboer.exercise5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Scanner;

public class Exercise5 {

	public static void main(String[] args) {
		//testCrypt();
		//otpCrypt();
		crackCode();
	}
	private static void crackCode() {
		Random generator;
		for (int i = 0; i < 10000; i++) {
			generator = new Random(i);
			if (testRandomSeed(generator.nextInt())) {
				System.out.println("Seed is: " + i);
				//i = 10001;
			}
		}
		
	}
	private static boolean testRandomSeed(int random) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("outputSecret.txt"));
			InputStreamReader reader = new InputStreamReader(new FileInputStream("secret.txt"));
			int c;
			while ((c = reader.read())>= 0) {
				writer.write(crypt((char)c, random, false));
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
		return true;
	}
	private static void otpCrypt() {
		Scanner scanner = new Scanner(System.in);
		boolean crypt = false;
		String input, output;
		System.out.println("Do you want to encrypt or decrypt? (e/d)");
		if (scanner.next() == "e") {
			crypt = true;
		}
		System.out.println("Enter input filename:");
		input = scanner.next();
		System.out.println("Enter output filename:");
		output = scanner.next();
		scanner.close();
		fileToFileCrypt(input, output, crypt);
		System.out.println("Done!");
	}
	private static void fileToFileCrypt(String input, String output, boolean crypt) {
		assert input != "" | output != "" : "Input or output are blank strings";
		Random generator = new Random(4711);
		try {
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(output));
			InputStreamReader reader = new InputStreamReader(new FileInputStream(input));
			int c;
			while ((c = reader.read())>= 0) {
				writer.write(crypt((char)c, generator.nextInt(96), crypt));
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
		
	}
	private static void testCrypt() {
		int random = 42;
		for (int i = 32; i <= 127; i++) {
			System.out.println((char)i + " " + crypt((char)i, random, true) + " " + crypt(crypt((char)i, random, true), random, false));
		}
	}
	/**
	 * When encrypt is true, the function will encrypt a character. When encrypt is false, the function will decrypt a character.
	 * @param input
	 * @param random
	 * @param encrypt
	 * @return Encrypted/decrypted character
	 */
	private static char crypt(char input, int random, boolean encrypt) {
		assert input >= 0;
		if (input < 32 | input > 127) {
			return (char)input;
		} else {
			if (encrypt) {
				return (char)((input - 32 + random + 96)%96 + 32);
			} else {
				return (char)((input - 32 - random + 96)%96 + 32);
			}
		}
	}

}
