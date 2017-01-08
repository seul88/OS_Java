package put.os.fileSystem;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.runtime.NumberToString;
import sun.net.TelnetProtocolException;

public class AllocateMemory 
{
	public static enum memoryAllocateState
	{
		
		successfulyAllocatedMemory, //  pomyœlnie przydzielono pamiêæ do pliku
		fileAlreadyExist,			//	plik ju¿ istnieje(nie mo¿na utworzyæ!)
		notEnoughFreeMemory,		//	brak wystarczaj¹cej iloœci wolnej pamiêci
		fileExceedsMaximumSize,		//  udanie zmieniono wielkoœæ pliku
		notEmptyINode,				//	brak wolnego iwêz³a
		noFileExist,				//	plik nie istnieje
		successfulyDeletedFile,		//	udanie usuniêto plik
		successfulyChangedFileSize,	//  udanie zmieniono wielkoœæ pliku
		Error;						// b³¹d
	}
	
	/**
	 * przypisywanie miejsca do pliku
	 * @param fileName nazwa
	 * @param fileSize wielkoœæ
	 * @return zwraca wartoœæ enum'a reprezentuj¹c¹ wynik funkcji
	 */
	public static memoryAllocateState AllocateMemoryForFile(String fileName, String fileContent)
	{
		memoryAllocateState result = memoryAllocateState.Error;
		byte[] fileContentInBytes = fileContent.getBytes(Charset.forName("UTF-8"));
		int fileSize = fileContentInBytes.length;
		if (fileSize >  (HardDrive.blockSize * HardDrive.blockSize))
		{
			return memoryAllocateState.fileExceedsMaximumSize;
		}
		boolean isEnoughtSpace = false;
		int howManyBlocksIsNeaded = 0;		
		howManyBlocksIsNeaded = (int) Math.ceil((float)(fileContentInBytes.length) / HardDrive.blockSize);
		if (howManyBlocksIsNeaded > 2)
		{
			isEnoughtSpace = CheckIfDriveHasEnoughFreeMemoryForFile(howManyBlocksIsNeaded + 1);	
		}
		else			
		{
			isEnoughtSpace = CheckIfDriveHasEnoughFreeMemoryForFile(howManyBlocksIsNeaded);	
		}
		
		if (isEnoughtSpace)
		{
			int tempINodeNumber = GetFirstEmptyINode();
			int tempCounter = 0;
			if (tempINodeNumber == -1)
			{
				return memoryAllocateState.notEmptyINode;
			}			
			int numberOfIndexBlock = -1;
			if (howManyBlocksIsNeaded > 2)
			{
				numberOfIndexBlock = GetFirstFreeBlockNumber();
				HardDrive.vector[numberOfIndexBlock] = true;
			}

			List<Integer> listOfDataBlocks = new ArrayList<Integer>();
			for (int i = 0; i < howManyBlocksIsNeaded; i++)
			{
				int tempBlockIndex = GetFirstFreeBlockNumber();
				HardDrive.vector[tempBlockIndex] = true;
				listOfDataBlocks.add(tempBlockIndex);
				for(int j = 0; j < HardDrive.blockSize; j ++)
				{
					if (tempCounter >= fileContentInBytes.length)
					{
						HardDrive.drive[(tempBlockIndex * HardDrive.blockSize) + j] = -1;
					}
					else
					{
						HardDrive.drive[(tempBlockIndex * HardDrive.blockSize) + j] = fileContentInBytes[tempCounter];	
					}				
					tempCounter++;
				}
			}
			byte variable1 = -1;
			byte variable2 = -1;
			for(int i = 0; i < listOfDataBlocks.size(); i++)
			{
				if (i == 0)
				{
					variable1 = listOfDataBlocks.get(i).byteValue();
				}
				else if (i == 1)						
				{
					variable2 = listOfDataBlocks.get(i).byteValue();
				}
				else
				{
					HardDrive.drive[(HardDrive.blockSize * numberOfIndexBlock) + i - 2] = listOfDataBlocks.get(i).byteValue();	
				}					
			}

			INode tempINode = new INode(tempINodeNumber, variable1, variable2, fileSize, howManyBlocksIsNeaded, numberOfIndexBlock);
			HardDrive.iNodeTable[tempINodeNumber] = tempINode;
			HardDrive.catalog.add(new CatalogPosition(fileName, tempINodeNumber));
			result = memoryAllocateState.successfulyAllocatedMemory;
		}
		else
		{
			result = memoryAllocateState.notEnoughFreeMemory;
		}
		
		return result;
	}
	
	/**
	 * Funkcja dopisuj¹ca do pliku nowe dane
	 * @param _fileName - nazwa pliku do jakiego zostanie dopisana zawartoœæ
	 * @param _newContent - zawartoœæ do dopisania do pliku
	 * @return zwraca wynik dopisywania do pliku w postaci jednej z wartoœci enuma
	 */
	public static memoryAllocateState AddContentToFile(String _fileName, String _newContent, INode inode)
	{
		memoryAllocateState result = memoryAllocateState.Error;
		if (inode == null)
		{
			return result;
		}
		String tempContent = ReadFile(_fileName) + _newContent; 
		byte[] fileContentInBytes = tempContent.getBytes(Charset.forName("UTF-8"));
		int fileSize = fileContentInBytes.length;
		if (fileSize >  (HardDrive.blockSize * HardDrive.blockSize))
		{
			return memoryAllocateState.fileExceedsMaximumSize;
		}
		fileContentInBytes = _newContent.getBytes(Charset.forName("UTF-8"));
		boolean isEnoughtSpace = false;
		int howManyBlocksIsNeaded = 0;		
		howManyBlocksIsNeaded = (int) Math.ceil((float)(fileContentInBytes.length - CalculateHowManyDataCanAddToExistingFileBlocks(inode)) / HardDrive.blockSize);
		if (howManyBlocksIsNeaded == 0)
		{
			isEnoughtSpace = true;
		}
		else if (howManyBlocksIsNeaded > 1)
		{
			isEnoughtSpace = CheckIfDriveHasEnoughFreeMemoryForFile(howManyBlocksIsNeaded + 1);	
		}
		else			
		{
			isEnoughtSpace = CheckIfDriveHasEnoughFreeMemoryForFile(howManyBlocksIsNeaded);	
		}
		if (isEnoughtSpace)
		{
			
		}
		else
		{
			
		}
		
		return result;
	}
	
	private static int CalculateHowManyDataCanAddToExistingFileBlocks(INode inode)
	{
		int result = 0;
		
		if (inode.GetDirectBlock1() > 0)
		{
			byte[] tempBlock = ReadBlock(inode.GetDirectBlock1());
			for(byte b : tempBlock)
			{
				if (b == -1)
				{
					result++;
				}
			}
		}
		
		if (inode.GetDirectBlock2() > 0)
		{
			byte[] tempBlock = ReadBlock(inode.GetDirectBlock2());
			for(byte b : tempBlock)
			{
				if (b == -1)
				{
					result++;
				}
			}
		}
		if (inode.GetFileIndexBlock() > 0)
		{
			byte[] tempIndexBlock = ReadBlock(inode.GetFileIndexBlock());
			for(byte b : tempIndexBlock)
			{
				if (b == -1)
				{
					result = result + HardDrive.blockSize;
				}
				else
				{
					byte[] tempBlock = ReadBlock(b);
					for(byte variable : tempBlock)
					{
						if (variable == -1)
						{
							result++;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * funkcja usuwaj¹ca plik z pamiêci
	 * @param fileName nazwa
	 * @return
	 */
	public static memoryAllocateState DeleteFileFromMemory(String _fileName)
	{
		memoryAllocateState result = memoryAllocateState.Error;
		
		CatalogPosition position = GetCatalogPositionObject(_fileName);
		
		if (position == null)
		{
			return memoryAllocateState.Error;
		}
		else			
		{
			INode iNodeOfFileToDelete = HardDrive.iNodeTable[position.GetIndexOfINode()];
			if (iNodeOfFileToDelete.GetDirectBlock1() >= 0)
			{
				boolean clearBlockResult = ClearBlock(iNodeOfFileToDelete.GetDirectBlock1());
				if (!clearBlockResult)
				{
					return memoryAllocateState.Error;
				}
			}
			if (iNodeOfFileToDelete.GetDirectBlock2() >= 0)
			{
				boolean clearBlockResult = ClearBlock(iNodeOfFileToDelete.GetDirectBlock2());
				if (!clearBlockResult)
				{
					return memoryAllocateState.Error;
				}
			}
			if (iNodeOfFileToDelete.GetBlockCounter() > 2)
			{				
				int tempIndexBlockNumber = iNodeOfFileToDelete.GetFileIndexBlock();
				byte[] tempIndexBlock = ReadBlock(tempIndexBlockNumber);
				for (int i = 0; i < tempIndexBlock.length; i++)
				{
					if (tempIndexBlock[i] != -1)
					{
						boolean clearBlockResult = ClearBlock(tempIndexBlock[i]);
						if (!clearBlockResult)
						{
							return memoryAllocateState.Error;
						}
						HardDrive.drive[(tempIndexBlockNumber * HardDrive.blockSize) + i] = -1;	
					}
				}
				
				HardDrive.vector[iNodeOfFileToDelete.GetFileIndexBlock()] = false;
			}
			
			HardDrive.iNodeTable[position.GetIndexOfINode()] = null;
			HardDrive.catalog.remove(position);
			result = memoryAllocateState.successfulyDeletedFile;
		}
		
		return result;
	}
	
	private static boolean ClearBlock(int blockNumber)
	{
		boolean result = false;
		for (int i = 0; i < HardDrive.blockSize; i++)
		{
			HardDrive.drive[(blockNumber * HardDrive.blockSize) + i] = -1;
		}	
		HardDrive.vector[blockNumber] = false;
		result = true;
		return result;
	}
	
	public static String ReadFile(String _fileName)
	{
		String result = null;
		
		CatalogPosition position = GetCatalogPositionObject(_fileName);
		
		if (position == null)
		{
			return null;
		}
		else
		{
			byte[] fileInBytes = new byte[HardDrive.blockSize * HardDrive.blockSize];
			INode iNodeOfFileToRead = HardDrive.iNodeTable[position.GetIndexOfINode()];
			int tempCounter = 0;
			if (iNodeOfFileToRead.GetDirectBlock1() >= 0)
			{
				byte[] tempIndexBlock = ReadBlock(iNodeOfFileToRead.GetDirectBlock1());
				for (int i = 0; i < tempIndexBlock.length; i++)
				{
					if (tempIndexBlock[i] != -1)
					{
						fileInBytes[tempCounter] = tempIndexBlock[i];
						tempCounter++;	
					}					
				}	
			}
			if (iNodeOfFileToRead.GetDirectBlock2() >= 0)
			{
				byte[] tempIndexBlock = ReadBlock(iNodeOfFileToRead.GetDirectBlock2());
				for (int i = 0; i < tempIndexBlock.length; i++)
				{
					if (tempIndexBlock[i] != -1)
					{
						fileInBytes[tempCounter] = tempIndexBlock[i];
						tempCounter++;	
					}					
				}	
			}
			int tempIndexBlockNumber = iNodeOfFileToRead.GetFileIndexBlock();
			if (tempIndexBlockNumber >= 0)
			{
				byte[] tempIndexBlock = ReadBlock(tempIndexBlockNumber);
				for (int i = 0; i < tempIndexBlock.length; i++)
				{
					int byteAsInteger = tempIndexBlock[i];
					if (byteAsInteger > 0)
					{
						byte[] tempDataBlock = ReadBlock(byteAsInteger);					
						for (int j = 0; j < tempDataBlock.length; j++)
						{
							if (tempDataBlock[j] != -1)
							{
								fileInBytes[tempCounter] = tempDataBlock[j];
								tempCounter++;	
							}
						}						
					}					
				}	
			}
			result = new String(fileInBytes, Charset.forName("UTF-8"));
		}
		return result;
	}
	
	private static CatalogPosition GetCatalogPositionObject(String _fileName)
	{
		CatalogPosition result = null;
		
		for (CatalogPosition position : HardDrive.catalog)
		{
			if (position.GetFileName().equals(_fileName))
			{
				result = position;
			}
		}
		
		return result;
	}
	
	/**
	 * funkcja definiuj¹ca wielkoœæ pamiêci i wielkoœæ bloku indeksowego
	 * @param memorySize wielkoœæ pamiêci
	 * @param blockSize wielkoœæ bloku
	 * @return
	 */
	public static boolean DefineMemorySize(int memorySize, int blockSize)
	{
		boolean result = false;
		return result;
	}
	
	/**
	 * funkcja zmieniaj¹ca wielkoœæ pliku
	 * @param fileName
	 * @param newFileSize
	 * @return
	 */
	public static memoryAllocateState ResizeFile(String _FileName, int newFileSize)
	{
		memoryAllocateState result = memoryAllocateState.Error;
		return result;
	}
	
	public static boolean CheckIfDriveHasEnoughFreeMemoryForFile(int howManyBLocksIsNeaded)
	{
		boolean result = false;

		for(int i = 0; i < HardDrive.vector.length; i++)
		{
			if (HardDrive.vector[i] == false)
			{
				howManyBLocksIsNeaded--;
				if (howManyBLocksIsNeaded == 0)
				{
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static byte[] ReadBlock(int blockNumber)
	{
		byte[] result = null;
		if (blockNumber <= HardDrive.driveMaxBlockCount)
		{ 
			result = new byte[HardDrive.blockSize];
			int counter = 0;
			for (int i = blockNumber * HardDrive.blockSize; i < (blockNumber * HardDrive.blockSize) + HardDrive.blockSize; i++)
			{
				result[counter] = HardDrive.drive[i];
				counter++;
			}
		}
		
		return result;
	}
	
	private static int GetFirstEmptyINode()
	{
		int result = -1;
		
		for(int i = 0; i < HardDrive. iNodeTable.length; i++)
		{
			if(HardDrive.iNodeTable[i] == null) 
			{
				result = i;
				break;
			}
		}
		
		return result;
	}
	
	private static int GetFirstFreeBlockNumber()
	{
		int result = -1;
		for(int i = 0; i < HardDrive.vector.length; i++)
		{
			if (HardDrive.vector[i] == false) 
			{
				result = i;
				break;
			}
		}
		
		return result;
	}
}
