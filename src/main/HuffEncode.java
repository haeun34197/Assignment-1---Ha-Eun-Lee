package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

	public static void main(String[] args) throws Exception {
		String input_file_name = "data/decoded.txt";
		String output_file_name = "data/encoded.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		int[] symbol_counts = new int[256];
		int num_symbols = 0;
		
		// Read in each symbol (i.e. byte) of input file and 
		// update appropriate count value in symbol_counts
		// Should end up with total number of symbols 
		// (i.e., length of file) as num_symbols
		int b_input = fis.read();
		
		while(b_input != -1) { //reads a byte of data from the input stream fis for each byte 
			num_symbols++; //update number of symbols count
			symbol_counts[b_input]++; //update the symbol count for that symbol
			b_input = fis.read(); //read the next byte
		}
		
		// Close input file
		fis.close();
		
		// Create array of symbol values
		int[] symbols = new int[256];
		for (int index = 0; index <= 255; index++) symbols[index] = index;
				
		// Create encoder using symbols and their associated counts from file.
		HuffmanEncoder encoder = new HuffmanEncoder(symbols, symbol_counts);
		
		// Open output stream.
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// Write out code lengths for each symbol as 8 bit value to output file.
		for(int index = 0; index <= 255; index++) bit_sink.write(encoder.getCode(index).length(), 8);
		
		// Write out total number of symbols as 32 bit value.
		bit_sink.write(num_symbols, 32);
		
		// Reopen input file.
		fis = new FileInputStream(input_file_name);
		
		// Go through input file, read each symbol (i.e. byte),
		// look up code using encoder.getCode() and write code
        // out to output file.
		
		while (fis.available() > 0) {
			b_input = fis.read();
			bit_sink.write(encoder.getCode(b_input));
		}

		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
	}
}

