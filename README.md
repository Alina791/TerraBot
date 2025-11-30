# Tema OOP TerraBot
# RÂNCĂ Alina Maria - 323CA

## Structura pechetelor
In implementarea programului am folosit abstractizarea si mostenirea pentru a
construi ierarhiile ecologice. Am creat pachete pentru a organiza clasele:

### 1. pachetul air
Contine toate subclasele clasei Air (tipuri de aer). Fiecare clasa primeste
AirInput pentru a stoca datele citite si implementeaza metode specifice pentru
calcularea calitatii si toxicitatii aerului. Toate clasele din pachet au metoda
addOxygen, care actualizeaza nivelul de oxigen, dar si caracteristice conexe
ale aerului in momentul interactiunii cu mediul (la scanarea unei plante).

### 2. pachetul soil
Contine toate subclasele clasei Soil (tipuri de sol). Fiecare clasa are un camp
specific tipului de sol, care este folosit in implementarea metodei pentru
calcularea calitatii solului.

### 3. pachetul animals
Contine toate subclasele clasei Animal (tipuri de animale, in functie de hrana
consumata). Fiecare primeste AnimalInput.

### 4. pachetul plants
Contine toate subclasele clasei Plant (tipuri de plante). Fiecare clasa are o
metoda ce returneaza cantitatea de oxigen produsa de planta in functie de
varsta sa. Fiecare primeste PlantInput.

In toate clasele din pachetele air, soil, animals si plants este calculata
probabilitatea ca entitatea respectiva sa afecteze drumul ales de robot. In
cazul claselor din air, probabilitatea este data de toxicitatea aerului.

### 5. pachetul entities
Contine toate clasele de baza (superclasele, inclusiv Entity), stabilind
interfata polimorfica prin metode abstracte precum calculateAirQuality() sau
calculateAttackProbability(). Toate clasele primesc inputul specific tipului.

* **'Clasa Air'** implementeaza metode, precum addOxygen, addHumidity si pentru
schimbarea conditiilor meteo, care sunt folosite de subclasele sale la
actualizarea in mod corespunzator a oxigenului, a umiditatii si a mediului.
Ulterior, este necesara recalcularea calitatii aerului. Clasa are o metoda care
returneaza un label pentru calitatea aerului in functie de valoarea sa si o
metoda care verifica daca vremea are o caracteristica specifica (apelata in
Simulation). 

* **'Clasa Plant'** are metode abstracte (implementate de subclase), pentru
posibilitatea plantei de a ataca robotul si pentru cantitatea de oxigen
produsa de fiecare specie de planta. Metoda getScanned spune daca planta a fost
sau nu scanata, iar metoda grow actualizeaza label ul pentru varsta plantei si
cresterea sa in functie de oxigenul primit de la mediu.

* **'Clasa Soil'** este asemanatoare cu Air, deoarece are metode similare
pentru schimabarea mediului, avand, de asemenea, o metoda pentru identificarea
unui label pentru calitatea solului.

* **'Clasa Animal'** implementeaza metoda abstracta pentru calcularea
probabilitatii de atac asupra robotului.

* **'Clasa Water'** are o metoda pentru a identifica daca entitatea a fost
scanata.

### 6. pachetul map
* **'Clasa Cell'** reprezinta o celula din harta si retine schimbarea apei.

* **'Clasa TerraMap'** creeaza un grid pentru a stoca parametrii corespunzatori
din input la coordonatele din harta indicate. Se creeaza un obiect nou pentru
fiecare entitate din input. In plus, exista metoda care aplica schimbarile
asupra mediului in urma scanarii unui obiect. Aceasta poate aduce schimbari
asupra aerului, in functie de plante (oxigen) sau de apa (umiditate) sau asupra
solului, in functie de umiditatea apei.

* **'Clasa Weather'** are o metoda ce are ca scop schimbarea calitatii aerului,
in functie de diferiti factori externi. Calitatea anterioara a aerului este
pastrata. Schimbarile aerului sunt applicate doar doua secunde dupa primirea
comenzii, iar apoi sunt recuperate valorile initiale.

### 7. pachetul simulation
Contine clasele Robot, Simulation si Scanner, responsabile de desfasurarea
unei simulari.

* **'Clasa Simulation'** proceseaza fiecare comanda intalnita in input si
produce schimbarile specifice. Exista erori specifice in cazul in care
simularea nu a inceput inca sau robotul este la incarcat. Simulation pune
mesajele fiecarei comenzi in nodurile array-ului. Aceasta are o metoda pentru
afisarea caracteristicior celulei curente (analizeaza ce entitati exista in
celula respectiva), dar si o metoda pentru afisarea hartii in mod simplificat. 

* **'Clasa Scanner'** se ocupa de scanarea unui anumit obiect, in functie de
parametrii primiti din input. Cand Scanner identifica ce tip de obiect trebuie
sa scaneze, scaneaza entitatea respectiva din celula curenta. Tipul de obiect
scanat aduce diferite schimbari asupra mediului.

* **'Clasa Robot'** este responsabila pentru traseul robotului pe harta. Metoda
moveRobot a clasei Robot implementeaza o logica de cautare a celui mai mic cost
de miscare. Acest cost este calculat ca o medie aritmetica a riscurilor din
celula tinta (toxicitate aer, risc de blocare sol, ..). Robotul analizeaza 
celulele intr-o ordine specifica, utilizand aceasta ordine ca regula de
tie-breaker in cazul scorurilor egale. Clasa Robot foloseste compozitia
(has-a relation) pentru a delega functia de scanare obiectului intern de tip
Scanner. Aceasta separa responsabilitatile de navigare si interactiune cu
mediul. Robot are metoda recharge, care adauga energie la bateria robotului.
Daca acesta se incarca, el nu mai poate realiza alte actiuni. 
