package put.os.fileSystem;

import java.util.ArrayList;
import java.util.List;

public class AllocateMemory 
{
	public static enum memoryAllocateState
	{
		
		successfulyAllocatedMemory, //  pomy�lnie przydzielono pami�� do pliku
		fileAlreadyExist,			//	plik ju� istnieje(nie mo�na utworzy�!)
		notEnoughFreeMemory,		//	brak wystarczaj�cej ilo�ci wolnej pami�ci
		fileExceedsMaximumSize,		//  udanie zmieniono wielko�� pliku
		notEmptyINode,				//	brak wolnego iw�z�a
		noFileExist,				//	plik nie istnieje
		successfulyDeletedFile,		//	udanie usuni�to plik
		successfulyChangedFileSize,	//  udanie zmieniono wielko�� pliku
		Error;						// b��d
	}
	
	/**
	 * przypisywanie miejsca do pliku
	 * @param fileName nazwa
	 * @param fileSize wielko��
	 * @return zwraca warto�� enum'a reprezentuj�c� wynik funkcji
	 */
	public static memoryAllocateState AllocateMemoryForFile(String fileName, char[] fileContent)
	{
		memoryAllocateState result = memoryAllocateState.Error;	
		int fileSize = fileContent.length;
		boolean indexBlockIsNeaded = false;
		if (fileSize > 2)
		{
			indexBlockIsNeaded = true;
		}
		else if (fileSize > 2 + (HardDrive.blockSize * HardDrive.blockSize))
		{
			return memoryAllocateState.fileExceedsMaximumSize;
		}
		boolean isEnoughtSpace = false;
		int howManyBlocksIsNeaded = 0;
		if (indexBlockIsNeaded)
		{
			howManyBlocksIsNeaded = (fileContent.length - 2) / HardDrive.blockSize;
			isEnoughtSpace = CheckIfDriveHasEnoughFreeMemoryForFile(howManyBlocksIsNeaded);	
		}
		else
		{
			isEnoughtSpace = true;	
		}
		
		if (isEnoughtSpace)
		{
			int tempINodeNumber = GetFirstEmptyINode();
			if (tempINodeNumber == -1)
			{
				return memoryAllocateState.notEmptyINode;
			}			
			int numberOfIndexBlock = GetFirstFreeBlockNumber();
			HardDrive.vector[numberOfIndexBlock] = true;
			List<Integer> listOfDataBlocks = new ArrayList<Integer>();
			for (int i = 0; i < howManyBlocksIsNeaded; i++)
			{
				int tempBlockIndex = GetFirstFreeBlockNumber();
				HardDrive.vector[tempBlockIndex] = true;
				listOfDataBlocks.add(tempBlockIndex);
				for(int j = 0; j < HardDrive.blockSize; j ++)
				{
					HardDrive.drive[(tempBlockIndex * HardDrive.blockSize) + j] = fileContent[2 + (i * HardDrive.blockSize)];
				}
			}
			for(int i = 0; i < listOfDataBlocks.size(); i ++)
			{
				//HardDrive.drive[HardDrive.blockSize * numberOfIndexBlock + i] = (char)listOfDataBlocks.get(i);
			}
			
			INode tempINode = new INode(tempINodeNumber, fileContent[0], fileContent[1], fileSize, howManyBlocksIsNeaded, numberOfIndexBlock);
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
	 * funkcja usuwaj�ca plik z pami�ci
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
			if (iNodeOfFileToDelete.GetBlockCounter() > 0)
			{
				
				
				
				HardDrive.vector[iNodeOfFileToDelete.GetFileIndexBlock()] = false;
			}
			
			HardDrive.iNodeTable[position.GetIndexOfINode()] = null;
			HardDrive.catalog.remove(position);
		}
		
		return result;
	}
	
	private static CatalogPosition GetCatalogPositionObject(String _fileName)
	{
		CatalogPosition result = null;
		
		for (CatalogPosition position : HardDrive.catalog)
		{
			if (position.GetFileName() ==_fileName)
			{
				result = position;
			}
		}
		
		return result;
	}
	
	/**
	 * funkcja definiuj�ca wielko�� pami�ci i wielko�� bloku indeksowego
	 * @param memorySize wielko�� pami�ci
	 * @param blockSize wielko�� bloku
	 * @return
	 */
	public static boolean DefineMemorySize(int memorySize, int blockSize)
	{
		boolean result = false;
		return result;
	}
	
	/**
	 * funkcja zmieniaj�ca wielko�� pliku
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
			if (HardDrive.vector[i] == false) howManyBLocksIsNeaded--;
		}
		
		if (howManyBLocksIsNeaded == 0)
		{
			result = true;
		}
		else			
		{
			result = false;
		}
		
		return result;
	}
	
	public static char[] ReadBlock(int blockNumber)
	{
		char[] result = null;
		if (blockNumber <= HardDrive.driveMaxBlockCount)
		{ 
			result = new char[HardDrive.blockSize];
			int counter = 0;
			for (int i = blockNumber * HardDrive.blockSize; i < HardDrive.blockSize; i++)
			{
				result[counter] = HardDrive.drive[i];
				i++;
			}
		}
		
		return result;
	}
	
	private static int GetFirstEmptyINode()
	{
		int result = -1;
		
		for(int i = 0; i < HardDrive. iNodeTable.length; i++)
		{
			if(HardDrive.iNodeTable[i] == null) result = i;
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
