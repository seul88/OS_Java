package put.os.fileSystem;

public class INode 
{
	private int indexInINodeTable;
	private int fileSize;
	private int blockCounter;
	//bloki bezpośrednie
	private byte directBlock1;
	private byte directBlock2;
	//blok pośredni 1 poziomu
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
	
	public void SetDirectBlock2(byte _directBlock2)
	{
		directBlock2 = _directBlock2;
	}

	public int GetFileIndexBlock()
	{
		return numberOfFirstLevelIndirectBlock;
	}
	
	public void SetFileIndexBlock(int _numberOfFirstLevelIndirectBlock)
	{
		numberOfFirstLevelIndirectBlock = _numberOfFirstLevelIndirectBlock;
	}
	
	public void ResizeFileSize(int sizeToAdd)
	{
		fileSize = fileSize + sizeToAdd;
	}
}
