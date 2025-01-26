# jpa_rest_2
**Implementačná úloha 1.**  
Na obrázku je zobrazený diagram databázy s údajmi o učiteľoch a predmetoch, ktoré učia.

![image](https://github.com/user-attachments/assets/c1bf0b02-f501-4aff-bb94-d0d0bbe6845e)

V zip-súbore máte pripravený maven-projekt dbapp2t pre vývoj aplikácie pracujúcej s touto databázou.  
Projekt obsahuje v balíku dbapp tri zdrojové súbory:
- Ucitel.java: Dátová trieda pre prácu s učiteľmi. Obsahuje zatiaľ len deklarácie dátových členov (a tiež metódy hashCode, equals, a toString)
- Predmet.java: Dátová trieda pre prácu s predmetmi. Obsahuje zatiaľ len deklarácie dátových členov (a tiež metódy hashCode, equals, a toString)
- DbApp.java: Obsahuje deklarácie troch metód pre prácu s uvedenou databázou.  
  **Set<Ucitel> vyucujuci(EntityManager em, String kodPredmetu)**  
    &emsp;Vyhľadá v DB všetkých vyučujúcich daného predmetu.  
  **int pocetPredmetov(EntityManager em, String meno)**  
    &emsp;Zistí počet predmetov, ktoré učí vyučujúci s daným menom.  
  **boolean pridajVyucujuceho(EntityManager em, String kodPredmetu, String meno)**  
    &emsp;Pridá predmetu ďalšieho vyučujúceho.

Vašou úlohou je implementácia týchto metód podľa špecifikácie.  
Presnú špecifikáciu týchto metód nájdete v komentáry v hlavičke každej z nich.  

**Pokyny k implementácii.**
- Aby ste mohli metódy pracujúce s databázou implementovať je nutné najprv dokončiť implementáciu tried Ucitel a Predmet. Existujúci kód stačí ho doplniť o potrebné JPA anotácie, tak aby databázové tabuľly vygenerované z entitných tried mali presne takou štruktúrou ako je uvedená v diagrame hore, pričom:
  - kľúč ID tabuľky UCITEL bude autogenerovaný
    - a stĺpec MENO bude unikátny
  - kľúč KOD tabuľky PREDMET nie je autogenerovaný.
  - Názov prepojovacej tabuľky (UCITEL_PREDMET) aj názvy jej stĺpcov musia byť tiež také ako je uvedené v diagrame.
- Žiadne ďalše modifikácie entitných tried nie sú potrebné (názvy a typy dátových členov nemeniť!)
- Odporúčam postupovať tak, že najprv dokončíte implementáciu entitných tried a z nich si necháte vygenerovať tabuľky a potom implementujete metódy.

Dôležité je aby vygenerované tabuľky mali presne tú istú štruktúru ako je uvedená v diagrame hore. (Názvy tabuliek, stĺpcov a ich typy musia byť zachované, na poradí stĺpcov nezáleží).  
  
Odovzdajte balík dbapp zozipovaný ako súbor dbapp.zip

**Implementačná úloha 2.**  
V adresári restapp nájdete pripravený maven projekt pre vývoj webovej aplikácie s REST servisom.
Projekt obsahuje:
1. súbor pom.xml so všetkými závislosťami potrebnými pre vývoj servisu. Nie je ho potrebné dopĺňať.
2. balík rest. V tomto balíku budete vytvárať všetky triedy.

**Špecifikácia servisu**  
Ide o servis z druhej teoretickej otázky, ktorý funguje ako prekladový slovník z angličtiny do viacerých jazykov má resoursy s URI:
- **slovnik** koreňový zdroj
- **slovnik/{word}**, je zdroj obsahujúci preklady slova word do rôznych jazykov.
- **slovnik/{word}/{lang}**, reprezentuje preklad slova word do jazyka lang.

Pre prístup k nim metódy:
- GET pre koreňové URI slovnik - vráti počet slov v slovníku (t.j. anglických slov pre ktoré existuje v slovníku preklad aspoň do jedného jazyka)
- GET pre URI slovnik/{word} - vráti reťazec obsahujúci mená jazykov, do ktorých existuje preklad slova word. Ak pre slovo neexistuje žiadny preklad, vráti reťazec "Nezname slovo".
- DELETE pre URI slovnik/{word}- odstráni zo slovníka slovo word so všetkými prekladmi.
- GET pre URI slovnik/{word}/{lang} - vráti preklad slova word do jazyka lang.  
&emsp;&emsp;Ak slovník nemá preklad slova word do jazyka lang, vráti reťazec "Preklad neexistuje".  
&emsp;&emsp;Ak je slovník vôbec nepozná slovo word (nemá žiadne jeho preklady), vráti reťazec "Nezname slovo".  
- PUT pre URI slovnik/{word}/{lang} - modifikuje preklad slova word do jazyka lang.
  - Ak preklad daného slova do danáho jazyka ešte v slovníku nie je, pridá ho tam.
  - Ak preklad daného slova do danáho jazyka už v slovníku je, nahradí ho novým prekladom.
- DELETE pre URI slovnik/{lang}/{word}- odstráni preklad slova word do jazyka lang. (ostatné preklady slova word ponechá)

**Pokyny pre implementáciu**  
Servis imlementujte tak, že všetky údaje sú udržiavané len v dátových štruktúrach v pamäti servisu.
Vytvorte si pre to vhodné dátové štruktúry a použite anotáciu @javax.inject.Singleton.
Servis inicializujte tak, že po spustení (deploymente) bude slovník prázdny (neobsahuje preklad žiadneho slova do žiadneho jazyka).  
  
Odovzdajte balík rest zozipovaný ako súbor rest.zip
