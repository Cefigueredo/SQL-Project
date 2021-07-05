f = open("C:/Users/Carlos/Desktop/Development/C-08/Proyecto/Iteracion 3/ciudadanos.csv", "r")
f1 = open("C:/Users/Carlos/Desktop/Development/C-08/Proyecto/Iteracion 3/ciudadanosw.csv", "w")
a = f.readline()

while a != "":
    b = a[0:len(a)-2]+"\n"
    f1.write(b)
    a = f.readline()

f.close()
f1.close()
