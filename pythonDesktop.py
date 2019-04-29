from tkinter import *
from tkinter import messagebox
from tkinter import ttk

def onClickButton():
    numPri= inputText.get()
    numSeg= inputText2.get()

    messagebox.showinfo("Resultado",str(int(numPri) + int(numSeg)))

window = Tk()
window.title("Janela Desktop")

Label(window,text="Primeiro numero",font="none 12 bold").pack()

inputText = Entry(window,width=20,bg="white")
inputText.pack()

Label(window,text="Segundo numero",font="none 12 bold").pack()

inputText2 = Entry(window,width=20,bg="white")
inputText2.pack()

Button(window,text="Somar",width=6,command=onClickButton).pack()

output = Text(window,width=75,height=6,wrap=WORD,bg="white")

window.mainloop()