package put.os.fileSystem;

import java.util.*;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int memorySizeSetByUser = 0;
		Scanner reader = new Scanner(System.in); 
		System.out.println("Projekt zaliczeniowy systemy operacyjne");
		System.out.println("Podaj wielkoœæ dysku (w znakach): \n");
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
			switch(reader.nextInt())
			{
				case 1:
				{
					int tempFreeBlocks = drive.GetFreeSpaceOnDriveInBlocks();
					int tempBlockSize = drive.GetBlockSize();
					System.out.println("Na dysku pozostalo :" 
					+ tempFreeBlocks * tempBlockSize
					+ "wolnego miejsca na ktere sk³ada sie :"
					+ tempFreeBlocks
					+ "blokow.");
					break;
				}
				case 2:
				{
					System.out.println("Lista plikow : \n");
					System.out.println(drive.GetFilesListInString());
					break;
				}
				case 3:
				{
					System.out.println("Podan nazwe pliku : \n");
					String tempFileName = reader.nextLine();
					System.out.println("Podan tresc pliku : \n");
					String tempFileContent = reader.nextLine();
					
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
						System.out.println("0 - Anuluj");
						switch(reader.nextInt())
						{
							case 1:
							{
								break;
							}
							case 2:
							{
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
