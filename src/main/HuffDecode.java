package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws Exception {
		String input_file_name = "data/compressed.dat";
		String output_file_name = "data/decoded.txt";
		
		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();
		int[] counter_s = new int[256];
		// Read in huffman code lengths from input file and associate them with each symbol as a 
		// SymbolWithCodeLength object and add to the list symbols_with_length.
		for(int index = 0; index <= 255; index++) symbols_with_length.add(new SymbolWithCodeLength(index, bit_source.next(8)));
		
		// Then sort they symbol
		symbols_with_length.sort(null);

		// Now construct the canonical huffman tree
		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(symbols_with_length);

		// Read in the next 32 bits from the input file  the number of symbols
		int num_symbols = bit_source.next(32);
			
		try {
			// Open up output file.
			FileOutputStream fos = new FileOutputStream(output_file_name);
			
			for (int index = 0; index < num_symbols; index++) {
				// Decode next symbol using hufftree and write out to file
				int d_n_symbol = huff_tree.decode(bit_source);
				fos.write(d_n_symbol);
				// For answering question on part 3
				counter_s[d_n_symbol]++;
			}
			
			// Flush output and close files.
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}


