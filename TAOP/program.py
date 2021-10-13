import sys

class Partido:
    def __init__ (self, equipo1, equipo2, hora_inicio, hora_fin): 
        self.equipo1 = equipo1
        self.equipo2 = equipo2
        self.hora_inicio = hora_inicio
        self.hora_fin = hora_fin
    
    def toString(self): 
        return self.equipo1 +" "+ self.equipo2 +" "+ str(int(self.hora_inicio)) +" "+  str(int(self.hora_fin))
        

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
                del listaOrdenada[i+1]
                del horario[item[0]][i+1]
                i = i+2 #we skip element as it already coincides with other match 
            i=i+1

        

#file = open("/mnt/c/users/nicol/documents/upm/taop/taop/input", "r")
#data = file.readlines()
data = sys.stdin.readlines()
nlines = data.count('\n')-1
counter,num = 0,0
horario = dict({}) #<diapartido, lista_partidos>
horas = dict({}) #<diapartido, lista_horarios en un dia partido>
for line in data:
    counter = counter + 1
    spline = line.split()
    if line == "000000 0": 
        for matchday in list(zip(horario.keys(), horario.values())):
            print("\n" + matchday[0] + " "+ str(len(matchday[1])))
            for partido in matchday[1]:
                print(partido.toString())
        print(line)
        sys.exit()
    elif (spline[0].isdigit()):
        if ( bool(horario) is not False or counter+num is nlines): 
            conflict_checker(horas, horario) #runs for the last added date of matches
        date = spline[0]
        num = spline[1]
        horario[date] = []
        horas[date] = []
    else:     
        equipo1 = spline[0]
        equipo2 = spline[1]
        hora_inicio = spline[2]
        hora_fin = spline[3]
        partido = Partido(equipo1, equipo2, hora_inicio, hora_fin)
        horario[date].append(partido)
        horas[date].append([int(hora_inicio), int(hora_fin)])
