import sys
import fileinput

class Partido:
    def __init__ (self, equipo1, equipo2, hora_inicio, hora_fin): 
        self.equipo1 = equipo1
        self.equipo2 = equipo2
        self.hora_inicio = hora_inicio
        self.hora_fin = hora_fin
    
    def items(self): 
        return equipo1 +" "+  equipo2 +" "+  hora_inicio +" "+  hora_fin
        
horario = dict({}) #<diapartido, lista_partidos>
horas = dict({}) #<diapartido, lista_horarios en un dia partido>
num_matches = 0

def printPartidos (lista: list): 
    for partido in lista: 
        print(partido.items())

def conflict_checker(horasp: list): 
    '''
    This function recieves a list of arrays whose elements define integer ranges,
    it sorts them, finds intersections between contiguous ranges. If found deletes entry 
    from list that coincides
    '''
    i=1
    result = []
    horasp.sort()
    while(i < len(horasp)):
        if( horasp[i-1][1] > horasp[i][0] ): 
            horasp.pop(i+1)
            i = i+2 #we skip element as it already coincides with other match 
        i=i+1
    


        
        

# print("Meter datos de partidos, presione ctrl+d cuando termine: ") 
file = open("input", "r")
data = file.readlines()
# data = sys.stdin.read()   # Use Ctrl d to stop the input 
for line in data:
    spline = line.split()
    if line == "000000 0" or line == "\n": 
        for i,(date,lista) in enumerate(horario.items()): 
            print(date[0] + str(len(lista)))
            printPartidos(lista)
        sys.exit()
    elif (spline[0].isdigit()):
        if bool(horario) is not False: 
            conflict_checker(horario[date]) #if not first element
        date = spline[0]
        horario[date] = listaPartidos = []
        horas[date] = hora_array = []
    else:     
        equipo1 = spline[0]
        equipo2 = spline[1]
        hora_inicio = spline[2]
        hora_fin = spline[3]
        partido = Partido(equipo1, equipo2, hora_fin, hora_inicio)
        listaPartidos.append(partido)
        hora_array.append([int(hora_inicio), int(hora_fin)])




    #cosassss