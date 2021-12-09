from typing import List
from collections import defaultdict

def navLinkedMaxSubSum(i, lista, result):
        # print(i, lista, result)
        if i == 0 :
            result[0] = "F"
            result[1] = "H"
            return result
        elif i==1:
            result[0] = "H"
            result[1] = "F"
            return result
        prev, origin = lista[i]
        # print(prev, origin)
        if origin == i or i==len(lista):
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
            linkedMaxSubSum[index] = index-1, -1
        else:
            maxsum[index] = b
            linkedMaxSubSum[index] = index-2, index
    peorSuma= sum(lista)*2
    result = navLinkedMaxSubSum(size-1, linkedMaxSubSum, ["H"]*size)
    print(peorSuma-maxsum[size - 1], linkedMaxSubSum, result)


b = [98, 9, 33, 16, 64, 98, 58, 61, 84, 49]
a = [10, 100, 101, 100]
c= [10, 300, 10, 400, 500, 20]
d = [10,20,300,10,40,50,60]
print(maxSubsequentSum(a))
print(maxSubsequentSum(b))
print(maxSubsequentSum(c))
print(maxSubsequentSum(d))