package com.scarabsoft.jrest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil {
	public static String streamToString(InputStream in) {
		final StringBuilder out = new StringBuilder();
		try {
			final BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null) {
				out.append(line);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}
}