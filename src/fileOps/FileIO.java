package fileOps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import lists.NamedList;
import mainClasses.Pair;

public class FileIO {
	
	public static void saveToFile(String filePathString, NamedList<Pair> list){
		File file = new File(filePathString);
		FileOutputStream fout;
		ObjectOutputStream oos;
		try {
			fout = new FileOutputStream(file);
			oos = new ObjectOutputStream(fout);
			for(Pair tpl:list){
				oos.writeObject(tpl);	
			}
			oos.close();
			System.out.println("Done");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
