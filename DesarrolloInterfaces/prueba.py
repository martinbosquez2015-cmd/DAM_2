import sys 
from PySide6.QtWidgets import QApplication, QLabel

app = QApplication(sys.argv)
ventana = QLabel("PySide6 funciona")
ventana.resize(300,100)
ventana.show()
sys.exit(app.exec())