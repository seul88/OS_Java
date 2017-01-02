package put.os.fileSystem;

public class INode 
{
	private int indexInINodeTable;
	private int fileSize;
	private int blockCounter;
	//bloki bezpoœrednie
	private byte value1;
	private byte value2;
	//blok poœredni 1 poziomu
	int numberOfFirstLevelIndirectBlock;
	
	public INode(int _indexInINodeTable, byte _value1, byte _value2, int _fileSize, int _blockCounter, int _numberOfFirstLevelIndirectBlock)
	{
		indexInINodeTable = _indexInINodeTable;
		fileSize = _fileSize;
		blockCounter = _blockCounter;
		value1 = _value1;
		value2 = _value2;
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
	
	public byte GetValue1()
	{
		return value1;
	}
	
	public byte GetValue2()
	{
		return value2;
	}

	public int GetFileIndexBlock()
	{
		return numberOfFirstLevelIndirectBlock;
	}
}
