package put.os.fileSystem;

import java.util.*;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int memorySizeSetByUser = 0;
		Scanner reader = new Scanner(System.in); 
		System.out.println("Projekt zaliczeniowy systemy operacyjne");
		System.out.println("Podaj wielkoœæ dysku (w bajtach): \n");
		memorySizeSetByUser =  reader.nextInt();
		
		HardDrive drive = new HardDrive(memorySizeSetByUser);
		int applicationState = 1;
		do
		{
			System.out.println("Wybierz co chcesz zrobiæ:\n"
					+ "1: Wyswietl ilosc wolnego miejsca\n"
					+ "2: Wyswietl liste plikow\n"
					+ "3: Dodaj nowy plik\n"
					+ "4: Dopisz do pliku\n"
					+ "5: Usun plik\n"
					+ "6: Wyswietl plik\n"
					+ "0: koniec\n");
			int operatorChoice = reader.nextInt();
			reader.nextLine();
			switch(operatorChoice)
			{				
				case 1:
				{
					int tempFreeBlocks = drive.GetFreeSpaceOnDriveInBlocks();
					int tempBlockSize = drive.GetBlockSize();
					System.out.println("Na dysku pozostalo : " 
					+ tempFreeBlocks
					+ " wolnego miejsca na ktore sk³ada sie : "
					+ tempFreeBlocks / tempBlockSize
					+ " blokow.");
					break;
				}
				case 2:
				{
					System.out.println("Lista plikow : \n");
					System.out.println(drive.GetFilesListInString() + "\n");
					break;
				}
				case 3:
				{
					System.out.println("Podan nazwe pliku : \n");
					String tempFileName = reader.nextLine();
					System.out.println("Podan tresc pliku : \n");
					String tempFileContent = reader.nextLine();
					
					AllocateMemory.memoryAllocateState result = drive.CreateNewFile(tempFileName, tempFileContent); 
					
					System.out.println(result.toString());
					break;
				}
				case 4:
				{
					break;
				}
				case 5:
				{
					break;
				}
				case 6:
				{
					int applicationStateSecondLevel = 1;
					do
					{
						System.out.println("1 - Wyswietl plik");
						System.out.println("2 - Wyswietl strukturê pliku");
						System.out.println("Inny - Wyswietl plik");
						System.out.println("0 - Anuluj\n");
						int operatorChoiceCase6 = reader.nextInt();
						reader.nextLine(); 
						switch(operatorChoiceCase6)
						{
							case 1:
							{
								System.out.println("Podaj nazwe pliku : \n");
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
									System.out.println("Nie ma takiego pliku\n");
								}
								
								break;
							}
							case 2:
							{
								System.out.println("Podaj nazwe pliku : \n");
								break;
							}
							case 0:
							{
								applicationStateSecondLevel = 0;
								break;
							}
						}
					}
					while(applicationStateSecondLevel == 1);
					break;
				}
				case 0:
				{
					applicationState = 0;
					break;
				}
				default:
				{
					System.out.println("Nie rozpoznano polecenia");
					break;
				}
			}
		}
		while(applicationState == 1);
	}
}
