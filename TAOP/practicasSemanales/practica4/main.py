import sys
from typing import List


def navLinkedMaxSubSum(i, lista, result):
    # print(i, lista, result)
    if i == 0:
        result[0] = "F"
        result[1] = "H"
        return result
    elif i == 1:
        result[0] = "H"
        result[1] = "F"
        return result
    prev, origin = lista[i]
    # print(prev, origin)
    if origin == i or i == len(lista):
        # print(f"El {origin} es F")
        result[origin] = "F"
    # print(f"Me voy a {prev}")
    return navLinkedMaxSubSum(prev, lista, result)


def maxSubsequentSum(lista: List[int]):
    size = len(lista)
    if size == 0: return
    if size == 1: return lista[0]
    maxsum: List[int] = [None] * size
    maxsum[0] = lista[0]
    linkedMaxSubSum = [None] * size
    if lista[0] > lista[1]:
        maxsum[1] = lista[0]

    else:
        maxsum[1] = lista[1]

    for index in range(2, size):
        a = maxsum[index - 1]
        b = maxsum[index - 2] + lista[index]
        if a > b:
            maxsum[index] = a
            linkedMaxSubSum[index] = index - 1, -1
        else:
            maxsum[index] = b
            linkedMaxSubSum[index] = index - 2, index
    peorSuma = sum(lista) * 2
    result = navLinkedMaxSubSum(size - 1, linkedMaxSubSum, ["H"] * size)
    return peorSuma - maxsum[size - 1], result


def solution():
    casos = sys.stdin.read()
    casos = casos.split("\n\n")
    for caso in casos:
        if caso == "0 0\n":
            print("\n\n")
            exit()
        primera = caso.replace("\n", " ", 1).split(" ", 2)  # separa los primeros numeros del resto
        A, B = int(primera[0]), int(primera[1])
        linea = primera[2].split("\n")  # cogemos de la primera a la última fila
        obs = {}
        for i in range(A):
            obs[i] = linea[i].split(" ")

        j, path, indexpath = 0, [], []
        while j < B:
            i, col = 0, []
            while i < A:
                col.append(int(obs[i][j]))
                i += 1
            smallest = min(col)
            indexpath.append(col.index(smallest))
            path.append(smallest)
            j += 1
        sumTotal, speeds = maxSubsequentSum(path)
        endString = str(sumTotal) + "\n"
        for index in indexpath: endString += f"\t{index}"
        endString += "\n"
        for speed in speeds: endString += f"\t{speed}"
        print(endString, "\n")


solution()