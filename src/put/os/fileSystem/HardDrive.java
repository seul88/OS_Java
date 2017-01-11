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
			result = "No files found.";
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
	
	public AllocateMemory.memoryAllocateState AddContentToFile(String _fileName, String newContent)
	{
		return AllocateMemory.AddContentToFile(_fileName, newContent, GetInodeForFile(_fileName));
	}
	
	public String PrintFileBlocks(String _fileName)
	{
		String result = null;
		
		INode tempINode = GetInodeForFile(_fileName);
		if (tempINode != null)
		{
			result = "";
			int tempNumber = tempINode.GetDirectBlock1();			
			result += tempNumber + " : ";
			byte[] tempBlock = AllocateMemory.ReadBlock(tempNumber);
			for (byte b : tempBlock)
			{
				result += "\t" + b;
			}
			result += "\n";
			tempNumber = tempINode.GetDirectBlock2();
			if (tempNumber != -1)
			{
				result += tempNumber + " : ";
				byte[] tempBlock2 = AllocateMemory.ReadBlock(tempNumber);
				for (byte b : tempBlock2)
				{
					result += "\t" + b;
				}
				result += "\n";
			}
			tempNumber = tempINode.GetFileIndexBlock();
			if (tempNumber != -1)
			{
				byte[] tempBlock3 = AllocateMemory.ReadBlock(tempNumber);
				result += tempNumber + " : ";
				for (byte b : tempBlock3)
				{
					result += "\t" + b;					
				}
				result += "\n";
				for (byte b : tempBlock3)
				{
					if (b != -1)
					{
						result += b + " : ";
						byte[] tempBlock4 = AllocateMemory.ReadBlock(b);
						for (byte c : tempBlock4)
						{
							result += "\t" + c;		
						}
						result += "\n";
					}
				}
			}
    		System.out.println("Size of file : " + tempINode.GetFileSize() + "\n");
    		System.out.println("Number of blocks : " + tempINode.GetBlockCounter() + "\n");
    		System.out.println("Number of first data block : " + tempINode.GetDirectBlock1() + "\n");
    		System.out.println("Number of second data block : " + tempINode.GetDirectBlock2() + "\n");
		}				
		
		return result;
	}
	
	public INode GetInodeForFile(String _fileName)
	{
		INode result = null;
				
		for (CatalogPosition position : catalog)
		{
			if (position.GetFileName().equals(_fileName))
			{				
				result = iNodeTable[position.GetIndexOfINode()];
				break;
			}
		}				
		
		return result;
	}
	
	public String PrintDiscContent(int printMode)
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
