import sys

class Partido:
    def __init__ (self, equipo1, equipo2, hora_inicio, hora_fin): 
        self.equipo1 = equipo1
        self.equipo2 = equipo2
        self.hora_inicio = int(hora_inicio)
        self.hora_fin = int(hora_fin)
    
    def toString(self): 
        return self.equipo1 +" "+ self.equipo2 +" "+ str(int(self.hora_inicio)) +" "+  str(int(self.hora_fin)) + "\n"

def listaPrinter(listaP: list):
    res = ""
    for partido in listaP:
        res = res + partido.toString()
    return res.rstrip()
def conflict_checker(horario:dict): 
    '''
    This function recieves a list of arrays whose elements define integer ranges,
    it sorts them, finds intersections between contiguous ranges. If found deletes entry 
    from list that coincides
    '''
    for date, listaP in horario.items():
        i=0
        #sorts [[int_lower_1, int_upper_1],...] by ascending order in terms of int_lower_x, x being the index of any element of the tuple of lists 
        listaOrdenada = sorted(listaP, key = lambda lower:lower.hora_fin)
        while(i < len(listaOrdenada)-1):
            if( listaOrdenada[i].hora_fin > listaOrdenada[i+1].hora_inicio ): 
                del listaOrdenada[i+1]
            else : i=i+1
        print( date + " " + str(len(listaOrdenada)) + "\n" + listaPrinter(listaOrdenada))
    print("000000 0")

        
        

# file = open("/mnt/c/users/nicol/documents/upm/taop/taop/input", "r")
# data = file.readlines()
data = sys.stdin.readlines()
horario = dict({}) #<diapartido, lista_partidos>
for line in data:
    spline = line.split()
    if line == "000000 0": 
        conflict_checker(horario)
        sys.exit()
    elif (spline[0].isdigit()):
        date = spline[0]
        num = spline[1]
        horario[date] = []
    else:     
        equipo1 = spline[0]
        equipo2 = spline[1]
        hora_inicio = spline[2]
        hora_fin = spline[3]
        partido = Partido(equipo1, equipo2, hora_inicio, hora_fin)
        horario[date].append(partido)        
