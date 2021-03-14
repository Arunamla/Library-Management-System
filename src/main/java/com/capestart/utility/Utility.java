package com.capestart.utility;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utility {
	
	public static Byte[] convertToBytes(MultipartFile file) {

		try {
			Byte[] byteObjects = new Byte[file.getBytes().length];
			int i = 0;
			for (byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			return byteObjects;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
