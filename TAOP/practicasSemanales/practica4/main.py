
casos = open("data").read()
casos = casos.split("\n\n")
for caso in casos:
    if caso == "0 0\n":
        exit()

    A = int(caso[0])
    B = int(caso[2])
    linea = caso.split("\n")[1:4]
    obs ={}
    for i in range(0, A):
        obs[i] = linea[i].split(" ")
    j = 0
    path = []
    indexpath = []
    while j < B:
        i = 0
        col = []
        while i < A :
            col.append(int(obs[i][j]))
            i += 1
        smallest = min(col)
        indexpath.append(col.index(smallest))
        path.append(smallest)
        j += 1

    print(path)
    print(indexpath)
    speed = {}
    while len(speed) != len(path):
        big = path.index(max(path))
        if big not in speed:
            speed[big] = "F"
            path[big] = 0
            if big != len(path) - 1 or big == 0 :
                if big+1 not in speed:
                    speed[big + 1] = "H"
                    path[big + 1] = 0
                if big - 1 not in speed:
                    path[big - 1] = 0
                    speed[big - 1] = "H"
    print(speed)


