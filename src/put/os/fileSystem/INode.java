package put.os.fileSystem;

public class INode 
{
	private int indexInINodeTable;
	private int fileSize;
	private int blockCounter;
	//bloki bezpoœrednie
	private byte directBlock1;
	private byte directBlock2;
	//blok poœredni 1 poziomu
	int numberOfFirstLevelIndirectBlock;
	
	public INode(int _indexInINodeTable, byte _value1, byte _value2, int _fileSize, int _blockCounter, int _numberOfFirstLevelIndirectBlock)
	{
		indexInINodeTable = _indexInINodeTable;
		fileSize = _fileSize;
		blockCounter = _blockCounter;
		directBlock1 = _value1;
		directBlock2 = _value2;
		numberOfFirstLevelIndirectBlock = _numberOfFirstLevelIndirectBlock;
	}
	
	public int GetIndexInINodeTable()
	{
		return indexInINodeTable;
	}
	
	public int GetBlockCounter()
	{
		return blockCounter;
	}
	
	public int GetFileSize()
	{
		return fileSize;
	}
	
	public byte GetDirectBlock1()
	{
		return directBlock1;
	}
	
	public byte GetDirectBlock2()
	{
		return directBlock2;
	}

	public int GetFileIndexBlock()
	{
		return numberOfFirstLevelIndirectBlock;
	}
}
