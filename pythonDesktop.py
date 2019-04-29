from tkinter import *

window = Tk()
window.title("Janela Desktop")

Label(window,text="Hello World",font="none 12 bold").grid(row=1,column=0,sticky=W)

window.mainloop()