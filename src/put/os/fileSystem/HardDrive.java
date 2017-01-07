package put.os.fileSystem;

import java.nio.charset.Charset;
import java.util.*;

public class HardDrive 	// reprezentacja przestrzeni dyskowej 
{						
	public static byte[] drive;
	public static int blockSize = 8;
	public static int driveMaxBlockCount = 1;
	public static boolean[] vector; // tablica reprezentuj¹ca wektor bitowy
	public static INode[] iNodeTable = new INode[10];
	public static List<CatalogPosition> catalog = new ArrayList<CatalogPosition>(); //tablica z list¹ istniej¹cych plików
	
	public HardDrive(int _memorySize)
	{
		// tworzê tablicê reprezentuj¹c¹ dysk o wielkoœci podanej przez u¿ytkownika
		drive = new byte[_memorySize];
		//dla ka¿dego elementu dysku wpisujemy pusty znak
		for (int i = 0; i < drive.length; i++)
		{									
			drive[i] = -1;
		}
		for (int i = 0; i < iNodeTable.length; i++)
		{									
			iNodeTable[i] = null;
		}

		driveMaxBlockCount = _memorySize / blockSize;
		vector = new boolean[driveMaxBlockCount];
	}
	
	public int GetMaxBlockCount()
	{
		return driveMaxBlockCount;
	}
	
	public boolean CheckIfFileExists(String _fileName)
	{
		boolean result;
		result = false;
		
		for (CatalogPosition position : catalog)
		{
			if (position.GetFileName().equals(_fileName))
			{
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public int GetFreeSpaceOnDriveInBlocks()
	{
		int result = 0;
		for (boolean block : vector)
		{
			if (block == false) 
			{
				result++;
			}
		}
		
		return result;
	}
	
	public int GetBlockSize()
	{
		return blockSize;
	}
	
	public String GetFilesListInString()
	{
		String result = "";
		
		if (catalog.size() <=0)
		{
			result = "Brak plikow.";
		}
		else
		{
			int i = 0;
			for(CatalogPosition position : catalog)
			{
				i++;
				result += position.GetFileName() + "\t";
				if (i % 5 == 0) result +="\n";
			}
		}
		
		return result;
	}
	
	public AllocateMemory.memoryAllocateState CreateNewFile(String _fileName, String _fileContent)
	{
		return AllocateMemory.AllocateMemoryForFile(_fileName, _fileContent);
	}
	
	public AllocateMemory.memoryAllocateState DeleteFileFromMemory(String _fileName)
	{
		return AllocateMemory.DeleteFileFromMemory(_fileName);
	}
	
	public String PrintFileFromMemory(String _fileName)
	{
		return AllocateMemory.ReadFile(_fileName);
	}
	
	public static String PrintDiscContent(int printMode)
	{
		String result = null;
		
		if (printMode == 1)
		{
			result = "";
			int blockCount = 0;
			for (int i = 0; i < drive.length; i++)
			{
				if (i % blockSize == 0)
				{					
					result += "\n";
					result += blockCount + ":";
					blockCount++;
				}
				
				result += "\t" + drive[i];				
			}
		}
		else if (printMode == 2)
		{
			result = "";
			int blockCount = 0;
			for (int i = 0; i < drive.length; i++)
			{
				if (i % blockSize == 0)
				{					
					result += "\n";
					result += blockCount + ":";
					blockCount++;
				}
				if (drive[i] == -1)
				{
					result += "\t" + "";
				}
				else					
				{
					byte[] tempTable = new byte[1];
					tempTable[0] = drive[i];
					String temp = new String(tempTable, Charset.forName("UTF-8"));
					result += "\t" + temp;	
				}								
			}
		}
		else			
		{
			result = null;
		}
		
		return result;
	}
}
