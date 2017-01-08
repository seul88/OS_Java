package put.os.fileSystem;

import virtual.device.MainMemory;

import java.util.Scanner;

public class Filesystem {

    private static HardDrive drive = new HardDrive(MainMemory.size);

    public static void run() {
        Scanner reader = new Scanner(System.in);

        int applicationState = 1;
        do
        {
            System.out.println("Choose action to perform : \n"
                    + "1: Show free space on disc\n"
                    + "2: Show files list\n"
                    + "3: Create new file\n"
                    + "4: Add content to file\n"
                    + "5: Detele file\n"
                    + "6: Show file\n"
                    + "7: Show disc content\n"
                    + "8: Show inode of file\n"
                    + "0: Exit disc operation\n");
            int operatorChoice = reader.nextInt();
            reader.nextLine();
            switch(operatorChoice)
            {
                case 1:
                {
                    int tempFreeBlocks = drive.GetFreeSpaceOnDriveInBlocks();
                    int tempBlockSize = drive.GetBlockSize();
                    System.out.println("Free space on disc : "
                            + tempFreeBlocks * tempBlockSize
                            + " divided in : "
                            + tempFreeBlocks
                            + " blocks.");
                    break;
                }
                case 2:
                {
                    System.out.println("List of files on disk : \n");
                    System.out.println(drive.GetFilesListInString() + "\n");
                    break;
                }
                case 3:
                {
                    System.out.println("Enter file name : \n");
                    String tempFileName = reader.nextLine();
                    boolean tempResult = drive.CheckIfFileExists(tempFileName);
                    if (!tempResult)
                    {
                    	System.out.println("Enter content of file : \n");
                        String tempFileContent = reader.nextLine();
                        AllocateMemory.memoryAllocateState result = drive.CreateNewFile(tempFileName, tempFileContent);
                        System.out.println(result.toString());
                        break;	
                    }
                    else                    	
                    {
                    	System.out.println("File already exists\n");
                    }
                }
                case 4:
                {
                    System.out.println("Enter file name : \n");
                    String tempFileName = reader.nextLine();
                    boolean tempResult = drive.CheckIfFileExists(tempFileName);
                    if (tempResult)
                    {
                    	System.out.println("Enter content of file : \n");
                        String tempFileContent = reader.nextLine();
                        
                    }
                    else                    	
                    {
                    	System.out.println("File not found\n");
                    }
                    break;
                }
                case 5:
                {
                    System.out.println("Enter file name : \n");
                    String tempFileName = reader.nextLine();
                    boolean tempResult = drive.CheckIfFileExists(tempFileName);
                    if (tempResult)
                    {
                    	 AllocateMemory.memoryAllocateState result = drive.DeleteFileFromMemory(tempFileName);
                    	 System.out.println(result + "\n");
                    }
                    else
                    {
                        System.out.println("File not found\n");
                    }

                    break;
                }
                case 6:
                {
                    int applicationStateSecondLevel = 1;
                    do
                    {
                        System.out.println("1 - Show file content");
                        System.out.println("2 - Show file representation in blocks");
                        System.out.println("0 - Cancel\n");
                        int operatorChoiceCase6 = reader.nextInt();
                        reader.nextLine();
                        switch(operatorChoiceCase6)
                        {
                            case 1:
                            {
                                System.out.println("Enter file name : \n");
                                String tempFileName = reader.nextLine();
                                boolean tempResult = drive.CheckIfFileExists(tempFileName);
                                if (tempResult)
                                {
                                    String tempFileContent = drive.PrintFileFromMemory(tempFileName);
                                    if (tempFileContent != null)
                                    {
                                        System.out.println(tempFileContent + "\n");
                                    }
                                }
                                else
                                {
                                    System.out.println("File not found\n");
                                }

                                break;
                            }
                            case 2:
                            {
                                System.out.println("Enter file name : \n");
                                break;
                            }
                            case 0:
                            {
                                applicationStateSecondLevel = 0;
                                break;
                            }
                            default :
                            {
                            	System.out.println("Unnown command\n");
                            	break;
                            }
                        }
                    }
                    while(applicationStateSecondLevel == 1);
                    break;
                }
                case 7:
                {
                    int applicationStateSecondLevel = 1;
                    do
                    {
                        System.out.println("1 - Disc content as numbers");
                        System.out.println("2 - Disc content as chars");
                        System.out.println("0 - Cancel\n");
                        int operatorChoiceCase7 = reader.nextInt();
                        reader.nextLine();
                        switch(operatorChoiceCase7)
                        {
                            case 1:
                            {
                            	String result = drive.PrintDiscContent(operatorChoiceCase7);
                            	System.out.println(result);
                            	System.out.println("\n");
                                break;
                            }
                            case 2:
                            {
                            	String result = drive.PrintDiscContent(operatorChoiceCase7);
                            	System.out.println(result);
                            	System.out.println("\n");
                                break;
                            }
                            case 0:
                            {
                                applicationStateSecondLevel = 0;
                                break;
                            }
                            default :
                            {
                            	System.out.println("Unnown command \n");
                            	break;
                            }
                        }
                    }
                    while(applicationStateSecondLevel == 1);
                    break;
                }
                case 8:
                {
                    System.out.println("Enter file name : \n");
                    String tempFileName = reader.nextLine();
                    boolean tempResult = drive.CheckIfFileExists(tempFileName);
                    if (tempResult)
                    {
                    	INode tempINode = drive.GetInodeForFile(tempFileName);                    	
                    	if (tempINode != null)
                    	{
                    		System.out.println("Size of file : " + tempINode.GetFileSize() + "\n");
                    		System.out.println("Number of blocks : " + tempINode.GetBlockCounter() + "\n");
                    		System.out.println("Number of first data block : " + tempINode.GetDirectBlock1() + "\n");
                    		System.out.println("Number of second data block : " + tempINode.GetDirectBlock2() + "\n");
                    		if (tempINode.GetBlockCounter() > 2)
                    		{
                    			System.out.println("Number of index block : " + tempINode.GetFileIndexBlock() + "\n");                    			
                    		}                    	                    		
                    	}
                    }
                    else
                    {
                        System.out.println("File not found\n");
                    }
                    break;
                }
                case 0:
                {
                    applicationState = 0;
                    break;
                }
                default:
                {
                    System.out.println("Unnown command");
                    break;
                }
            }
        }
        while(applicationState == 1);

    }

}
