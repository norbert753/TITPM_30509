#include<iostream>
#include<string>

using namespace std;

inline int sprawdzanie(char znak)
{
	
	if(znak >= 'a' && znak <= 'z') return 0;
	
	if(znak >= 'A' && znak <= 'Z') return 1;
	
	return 2;
}

void szyfruj(int key, string &tab)
{		
	
	if(!(key >= -26 && key <= 26)) return;
	
	int pom;
	char a, z;
	
	for(int i = 0; i < tab.size(); i++)
	{
		pom = sprawdzanie(tab[i]);
		
		if(pom < 2)
		{
			if(pom == 0) 
				a = 'a', z = 'z';
			else
				a = 'A', z = 'Z';
	
			if(key >= 0)
					
				if(tab[i] + key <= z)
					tab[i] += key;
				else
					tab[i] = tab[i] + key - 26; 
			else
				if(tab[i] + key >= a)
					tab[i] += key;
				else
					tab[i] = tab[i] + key + 26;
		}
	}
}

int main()
{
	string tab; 
	
	int key;
	
	cout<<"Podaj zdanie ktore chesz zaszyfrowac: ";
	getline(cin, tab);
	
	cout<<"Podaj klucz przedzial:[-26..26]: ";
	cin>>key;
	
	szyfruj(key,tab); 
	
	cout<<"zaszyfrowana wiadomosc: "<<tab<<endl;
	
	szyfruj(-key,tab);
	
	cout<<"rozszyfrowana wiadomosc: "<<tab<<endl;

	return 0;
}
