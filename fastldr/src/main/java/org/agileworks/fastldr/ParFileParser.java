package org.agileworks.fastldr;

import static org.agileworks.fastldr.Utils.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParFileParser {

	public static String[] parse(InputStream inputStream) throws FastLdrException {
		List<String> files = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("CONTROL")) {
					files.add(getSubstring(line));
				}
			}
		} catch (FileNotFoundException e) {
			throw new FastLdrException(e);
		} catch (IOException e) {
			throw new FastLdrException(e);
		}
		return files.toArray(new String[files.size()]);
	}

}
