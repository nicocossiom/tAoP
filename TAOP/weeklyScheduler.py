import sys
import fileinput

class Partido:
    def __init__ (self, equipo1, equipo2, hora_inicio, hora_fin): 
        self.equipo1 = equipo1
        self.equipo2 = equipo2
        self.hora_inicio = hora_inicio
        self.hora_fin = hora_fin
    
    def items(self): 
        return equipo1 +" "+  equipo2 +" "+  str(int(hora_inicio)) +" "+  str(int(hora_fin))
        
horario = dict({}) #<diapartido, lista_partidos>
horas = dict({}) #<diapartido, lista_horarios en un dia partido>

def printPartidos (lista: list): 
    for partido in lista: 
        print(partido.items())

def conflict_checker(horasp: list, horario:dict): 
    '''
    This function recieves a list of arrays whose elements define integer ranges,
    it sorts them, finds intersections between contiguous ranges. If found deletes entry 
    from list that coincides
    '''
    for item in list(zip(horasp.keys(),horasp.values())):
        i=1
        #sorts [[int_lower_1, int_upper_1],...] by ascending order in terms of int_lower_x, x being the index of any element of the tuple of lists 
        listaOrdenada = sorted(item[1], key = lambda lower:lower[0])
        # print(listaOrdenada)
        while(i < len(listaOrdenada)):
            if( listaOrdenada[i-1][1] > listaOrdenada[i][0] ): 
                horario[item[0]].pop(i)
                i = i+2 #we skip element as it already coincides with other match 
            i=i+1

        
        

# print("Meter datos de partidos, presione ctrl+d cuando termine: ") 
file = open("/mnt/c/Users/nicol/Documents/UPM/tAoP/TAOP/input", "r")
data = file.readlines()
nlines = data.count('\n')-1
counter,num = 0,0
# data = sys.stdin.read()   # Use Ctrl d to stop the input 
for line in data:
    counter = counter + 1
    spline = line.split()
    if line == "000000 0" or line == "\n": 
        conflict_checker(horas, horario) #for last date
        for i,(date,lista) in enumerate(horario.items()): 
            print(date[0] + str(len(lista)))
            printPartidos(lista)
        print("\n")
        sys.exit()
    elif (spline[0].isdigit()):
        if ( bool(horario) is not False or counter+num is nlines): 
            conflict_checker(horas, horario) #if not first element
        date = spline[0]
        num = spline[1]
        horario[date] = []
        horas[date] = []
    else:     
        equipo1 = spline[0]
        equipo2 = spline[1]
        hora_inicio = spline[2]
        hora_fin = spline[3]
        partido = Partido(equipo1, equipo2, hora_fin, hora_inicio)
        horario[date].append(partido)
        horas[date].append([int(hora_inicio), int(hora_fin)])
