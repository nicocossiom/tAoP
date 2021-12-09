from typing import List

b = [98, 9, 33, 16, 64, 98, 58, 61, 84, 49]
a = [10, 100, 101, 100]
c= [10, 300, 10, 400, 500, 20]

def maxSubsequentSum(lista: List[int]):
    size = len(lista)
    if size == 0: return
    if size == 1: return lista[0]
    maxsum: List[int] = [None] * size
    maxsum[0] = lista[0]
    result2 = []
    if lista[0] > lista[1]:
        maxsum[1] = lista[0]
        result2.append("F")
        result2.append("H")
    else:
        maxsum[1] = lista[1]
        result2.append("H")
        result2.append("F")
    result1 = ["F"]
    result3 = []
    for index in range(2, size):
        a = maxsum[index - 1]
        b = maxsum[index - 2] + lista[index]
        if a > b:
            result3 = result1.copy()
            maxsum[index] = a
            result3.append("H")
            result3.append("F")
        else:
            result3 = result2.copy()
            maxsum[index] = b
            result3.append("H")
        result1 = result2.copy()
        result2 = result3.copy()

    peorSuma = 0
    for elem in lista: peorSuma += elem*2
    return peorSuma-maxsum[size-1] , maxsum, result2

print(maxSubsequentSum(c))
