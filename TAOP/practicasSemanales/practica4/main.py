import sys

casos = sys.stdin.read()
casos = casos.split("\n\n")
for caso in casos:
    if caso == "0 0\n":
        print("\n\n")
        exit()
    primera = caso.replace("\n", " ", 1).split(" ", 2) #separa los primeros numeros del resto
    A, B = int(primera[0]), int(primera[1])
    linea = primera[2].split("\n")#cogemos de la primera a la última fila
    obs = {}
    for i in range(A):
        obs[i] = linea[i].split(" ")

    j, path, indexpath = 0, [], []
    while j < B:
        i, col = 0, []
        while i < A :
            col.append(int(obs[i][j]))
            i += 1
        smallest = min(col)
        indexpath.append(col.index(smallest))
        path.append(smallest)
        j += 1

    speed = {}
    time = 0
    
    suma = 0     
    for elem in path: suma+=elem*2
    print(suma)    
    print(path)
    while len(speed) != len(path):
        big = path.index(max(path))
        if big not in speed:
            speed[big] = "F"
            time += path[big]
            path[big] = 0
            if big+1 != len(path) and big+1 not in speed:
                speed[big + 1] = "H"
                time += path[big + 1] * 2
                path[big + 1] = 0
            if big-1 != -1 and big-1 not in speed:
                time += path[big - 1] * 2
                speed[big - 1] = "H"
                path[big - 1] = 0

    print(time)
    indexstr: str = ""
    for elem in indexpath: indexstr += f"\t{elem}"
    speedstr = ""
    for i in range(len(speed)): speedstr += f"\t{speed[i]}"
    print(indexstr,"\n",speedstr, "\n")